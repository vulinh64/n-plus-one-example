# Build the Spring Boot application and a minimal Java runtime.
FROM maven:3.9.14-amazoncorretto-25-alpine AS build

WORKDIR /usr/src/project

ENV JAVA_VERSION=25
ENV APP_NAME=app.jar
ENV DEPS_FILE=deps.info
ENV COMMONS_NAME=spring-base-commons
ENV COMMONS_GROUP_ID=com.vulinh
ENV GITHUB_USER=vulinh64

COPY pom.xml ./

RUN echo "Reading ${COMMONS_NAME} version from pom.xml..." \
    && mvn help:evaluate -Dexpression="spring-base-commons.version" -q -DforceStdout 2>/dev/null \
        | sed -n '/^[0-9][0-9A-Za-z_.-]*$/ { p; q; }' > commons-version.txt \
    && test -s commons-version.txt

RUN COMMONS_VERSION="$(cat commons-version.txt)" \
    && wget -O "${COMMONS_NAME}.jar" \
        "https://github.com/${GITHUB_USER}/${COMMONS_NAME}/releases/download/${COMMONS_VERSION}/${COMMONS_NAME}-${COMMONS_VERSION}.jar" \
    && mvn install:install-file \
        -Dfile="${COMMONS_NAME}.jar" \
        -DgroupId="${COMMONS_GROUP_ID}" \
        -DartifactId="${COMMONS_NAME}" \
        -Dversion="${COMMONS_VERSION}" \
        -Dpackaging=jar

COPY src/ src/

RUN mvn clean package -DskipTests \
    && cp target/n-plus-one-example-*.jar "${APP_NAME}" \
    && mkdir extracted \
    && cd extracted \
    && jar xf "../${APP_NAME}"

RUN jdeps \
        --ignore-missing-deps \
        -q --recursive \
        --multi-release "${JAVA_VERSION}" \
        --print-module-deps \
        --class-path 'extracted/BOOT-INF/lib/*' \
        "${APP_NAME}" > "${DEPS_FILE}" \
    && jlink \
        --add-modules "$(cat "${DEPS_FILE}"),jdk.crypto.ec" \
        --strip-java-debug-attributes \
        --compress 2 \
        --no-header-files \
        --no-man-pages \
        --output /jre-minimalist

FROM alpine:3.23.3 AS final

ENV JAVA_HOME=/opt/java/jre-minimalist
ENV PATH=$JAVA_HOME/bin:$JAVA_HOME/lib:$PATH
ENV USER=springuser
ENV GROUP=springgroup
ENV APP_NAME=app.jar

COPY --from=build /jre-minimalist "${JAVA_HOME}"

RUN addgroup -S "${GROUP}" \
    && adduser -S "${USER}" -G "${GROUP}" \
    && mkdir -p /app \
    && chown -R "${USER}:${GROUP}" /app

COPY --from=build --chown=springuser:springgroup /usr/src/project/app.jar /app/app.jar

WORKDIR /app

USER ${USER}

EXPOSE 8080

ENTRYPOINT ["java", \
    "-XX:+UseCompactObjectHeaders", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:InitialRAMPercentage=50.0", \
    "-XX:MaxMetaspaceSize=512m", \
    "-jar", \
    "app.jar"]
