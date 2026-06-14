#!/bin/sh

set -e

if ! docker info >/dev/null 2>&1; then
    echo "Error: Docker daemon is not running."
    echo "Please start Docker and run this script again."
    exit 1
fi

PG_CONTAINER_NAME="postgresql"
PG_VOLUME_NAME="postgresql-volume"
POSTGRES_NAME="postgres"
POSTGRES_TAG="18.3-alpine3.23"
POSTGRES_IMAGE="${POSTGRES_NAME}:${POSTGRES_TAG}"

echo "Checking PostgreSQL container [${PG_CONTAINER_NAME}]..."
if ! docker container inspect "${PG_CONTAINER_NAME}" >/dev/null 2>&1; then
    echo "Container [${PG_CONTAINER_NAME}] does not exist, creating with volume [${PG_VOLUME_NAME}]..."
    docker run -d \
        --name "${PG_CONTAINER_NAME}" \
        -e POSTGRES_USER=postgres \
        -e POSTGRES_PASSWORD=123456 \
        -e POSTGRES_DB=example \
        -p 5432:5432 \
        -v "${PG_VOLUME_NAME}:/var/lib/postgresql" \
        "${POSTGRES_IMAGE}"
elif [ "$(docker container inspect -f '{{.State.Running}}' "${PG_CONTAINER_NAME}")" != "true" ]; then
    echo "Container [${PG_CONTAINER_NAME}] is stopped, restarting..."
    docker start "${PG_CONTAINER_NAME}"
else
    echo "Container [${PG_CONTAINER_NAME}] is already running."
fi

echo "Waiting for PostgreSQL to accept connections..."
PG_READY=false
for _ in $(seq 1 30); do
    if docker exec "${PG_CONTAINER_NAME}" pg_isready -U postgres -d postgres >/dev/null 2>&1; then
        PG_READY=true
        break
    fi
    sleep 1
done

if [ "${PG_READY}" != "true" ]; then
    echo "PostgreSQL did not become ready within 30 seconds."
    exit 1
fi

echo "Checking database [example]..."
DATABASE_EXISTS=$(docker exec "${PG_CONTAINER_NAME}" \
    psql -U postgres -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='example'")

if [ "${DATABASE_EXISTS}" != "1" ]; then
    echo "Database [example] does not exist, creating..."
    docker exec "${PG_CONTAINER_NAME}" createdb -U postgres example
else
    echo "Database [example] already exists."
fi

echo "Purging database [example]..."
docker exec "${PG_CONTAINER_NAME}" \
    psql -v ON_ERROR_STOP=1 -U postgres -d example \
    -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

echo "Installing project data-class dependency..."
chmod +x ./create-data-classes.sh
./create-data-classes.sh

echo "Local development initialization completed successfully."
