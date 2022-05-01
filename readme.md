# Eco benchmark

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
