# Eco benchmark

This repository is benchmarking different scenario to try to compare the energy consumption, for several languages.

The different scenario will be the following, for each language:

- default, optimised but not extremely
- without database index
- without proper sql queries

## Workflow

The complete workflow is based on docker images. Every image should be named as `<org>/service-<service_name>:<use_case>`.

Each image can be built using the following command.

```bash
# oRG=jdrouet is used by default but can be changed by exporting a different variable.
./builder/service.sh <name_of_your_service> <use_case>
```

## Starting the database

### Postgres

```bash
docker run -d \
  --name eco-benchmark-database \
  # to run the migrations when starting the database
  --volume $(pwd)/migrations:/docker-entrypoint-initdb:ro \
  --port 5432:5432 \
  -e POSTGRES_PASSWORD=mysecretpassword \
  # today, the 30th of april 2022, this is the latest release
  postgres:14.2-bullseye

export DATABASE_URL=postgresql://postgres:mysecretpassword@127.0.0.1:5432/postgres
```
