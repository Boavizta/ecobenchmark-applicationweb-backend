# Eco benchmark

This repository is benchmarking different scenario to try to compare the energy consumption, for several languages.

The different scenario will be the following, for each language:

- default, optimised but not extremely
- different degraded use cases.

![Eco Benchmark diagram](eco-benchmark-boavizta.drawio.svg)

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


## Use Case Status

| Langage       | Reference (main) | No Index | ORM Loop | MySQL | GRPC | No Pagination | Aggregation on code side |
|---------------|------------------|----------|----------|-------|------|---------------|--------------------------|
| Go Lang       | X                | X        | X        | X     | X    | X             | X                        |
| Rust          | X                | X        | X        |       |      | X             | X                        |
| PHP (Symfony) | X                | X        | X        | X     | N/A  | X             | X                        |
| JVM/Kotlin    | X                | X        | X        | X     | X    | X             | X                        |
| Node.js       | X                | X        | X        | X     |      | X             | X                        |
| Ruby On Rails |                  |          |          |       |      |               |                          |

## Use Case Documentation

### Default

**Branch**  : main

**Description** : classic optimized implementation, but not too much.

### No Index

**Branch** : usecase-no-index

**Description** : oups, we forgot to put the indexes. For thoses who are laughing, you all have people running wordpress website around you. Check the db...

### ORM Loop

**Branch**  : usecase-orm-loop

**Description** : for the API path `GET /api/accounts/:account_id/lists/` we do a classic loop like developer usually do using a ORM or layered architecture.

Instead of one request, the get list become : 
 - Get the list from the db (paginated)
 - Loop on the list
   - get the tasks of the lists
   - fill the tasks in the list object
 - return full list with task.

### MySQL

**Branch**  : usecase-mysql

**Description** : replace postgresql by mysql

**Note** : DATABASE_URL should be like : `mysql://root:mysqlpw@tcp(localhost:3306)/ecobenchmark?parseTime=true`

`?parseTime=true` is mandatory.

### GRPC

**Branch**  : usecase-grpc

**Description** : use grpc instead of classic http/json.

Based on this service definition : https://gitlab.com/jeremie.drouet/eco-benchmark/-/blob/usecase-grpc/service/go-pgx/endpoints/endpoints.proto

### No Pagination

**Branch**  : usecase-no-pagination

**Description** : for the API path `GET /api/accounts/:account_id/lists/` remove pagination.

### Aggregation on code side

**Branch**  : usecase-aggregation-code-side

**Description** : for the API path `GET /api/stats` remove group by and aggregate on code side.

## Contributing

Everybody is more than welcome to contribute to this benchmark! Please check out the [Contributing guide](CONTRIBUTING.md) for guidelines about how to proceed.

You can also [join us](https://boavizta.org/en/contact) and become a member of the non-profit French association.

## License

The Eco benchmark is released under the [AGPL-3.0 license](LICENSE).