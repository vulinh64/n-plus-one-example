@echo off
SETLOCAL EnableDelayedExpansion

docker info >nul 2>&1
if errorlevel 1 (
    echo Error: Docker daemon is not running.
    echo Please start Docker Desktop or Docker service and run this script again.
    exit /b 1
)

SET PG_CONTAINER_NAME=postgresql
SET PG_VOLUME_NAME=postgresql-volume
SET POSTGRES_NAME=postgres
SET POSTGRES_TAG=18.3-alpine3.23
SET POSTGRES_IMAGE=%POSTGRES_NAME%:%POSTGRES_TAG%
SET PG_COMMAND=docker run -d --name !PG_CONTAINER_NAME! -e "POSTGRES_USER=postgres" -e "POSTGRES_PASSWORD=123456" -e "POSTGRES_DB=example" -p 5432:5432 -v !PG_VOLUME_NAME!:/var/lib/postgresql !POSTGRES_IMAGE!

echo Checking PostgreSQL container [!PG_CONTAINER_NAME!]...
docker container inspect !PG_CONTAINER_NAME! >nul 2>&1
if errorlevel 1 (
    echo Container [!PG_CONTAINER_NAME!] does not exist, creating with volume [!PG_VOLUME_NAME!]...
    !PG_COMMAND!
    if errorlevel 1 (
        echo Failed to create PostgreSQL container [!PG_CONTAINER_NAME!].
        exit /b 1
    )
) else (
    docker container inspect -f "{{.State.Running}}" !PG_CONTAINER_NAME! | findstr /C:"true" >nul
    if errorlevel 1 (
        echo Container [!PG_CONTAINER_NAME!] is stopped, restarting...
        docker start !PG_CONTAINER_NAME!
        if errorlevel 1 (
            echo Failed to restart PostgreSQL container [!PG_CONTAINER_NAME!].
            exit /b 1
        )
    ) else (
        echo Container [!PG_CONTAINER_NAME!] is already running.
    )
)

echo Waiting for PostgreSQL to accept connections...
SET PG_READY=0
FOR /L %%I IN (1,1,30) DO (
    docker exec !PG_CONTAINER_NAME! pg_isready -U postgres -d postgres >nul 2>&1
    if not errorlevel 1 (
        SET PG_READY=1
        goto :postgres_ready
    )
    timeout /t 1 /nobreak >nul
)

:postgres_ready
if "!PG_READY!"=="0" (
    echo PostgreSQL did not become ready within 30 seconds.
    exit /b 1
)

echo Checking database [example]...
SET DATABASE_EXISTS=
FOR /F "usebackq delims=" %%D IN (`docker exec !PG_CONTAINER_NAME! psql -U postgres -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='example'"`) DO (
    SET DATABASE_EXISTS=%%D
)

if not "!DATABASE_EXISTS!"=="1" (
    echo Database [example] does not exist, creating...
    docker exec !PG_CONTAINER_NAME! createdb -U postgres example
    if errorlevel 1 (
        echo Failed to create database [example].
        exit /b 1
    )
) else (
    echo Database [example] already exists.
)

echo Purging database [example]...
docker exec !PG_CONTAINER_NAME! psql -v ON_ERROR_STOP=1 -U postgres -d example -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"
if errorlevel 1 (
    echo Failed to purge database [example].
    exit /b 1
)

echo Installing project data-class dependency...
call .\create-data-classes.cmd
if errorlevel 1 (
    echo Failed to initialize project data classes.
    exit /b 1
)

echo Local development initialization completed successfully.
ENDLOCAL
