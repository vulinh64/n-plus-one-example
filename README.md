# `N+1` Query Example

This Spring Boot project demonstrates common JPA and Hibernate association-loading strategies and their tradeoffs.

The included endpoints and integration tests compare:

- Lazy loading without a transaction, resulting in `LazyInitializationException`

- Transactional lazy loading and the N+1 query problem

- `@EntityGraph`

- JPQL `JOIN FETCH`

- Hibernate `@BatchSize`

- Explicit two-query loading

- Loading multiple lazy collections

- Fetching multiple collections with an entity graph

The project logs generated SQL through P6Spy and includes integration tests that verify the expected query counts using a PostgreSQL Testcontainer.

## Requirements

- JDK 25

- Docker daemon or Docker Desktop

## Run Local Debug

1. Start the Docker daemon or Docker Desktop.

2. Run the initialization script for your environment from the project root.

### Windows:

```bat
initialize-local-development.cmd
```

### Linux, macOS, or another Unix-like environment:

```sh
chmod +x ./initialize-local-development.sh
./initialize-local-development.sh
```

### The initialization script

- Creates or starts the shared `postgresql` Docker container using `postgres:18.3-alpine3.23`

- Creates the `example` database when it does not exist

- Purges and recreates the `example` database's `public` schema

- Installs the required `spring-base-commons` dependency into the local Maven repository

The script only resets the `example` database. Other databases in the shared PostgreSQL container are not modified.

After initialization, run `Application` from your IDE in debug mode.

## Run Integration Tests

Ensure Docker is running, then execute:

### Windows

```bat
mvnw.cmd test
```

### Linux, macOS, or another Unix-like environment:

```sh
./mvnw test
```

## Run With Docker Compose

Ensure Docker is running, then execute:

```shell
docker compose up --build --detach
```

The Compose stack builds the application image and starts an isolated PostgreSQL container on the internal Compose network. The application is available at `http://localhost:8080`.

Stop the stack with:

```sh
docker compose down
```
