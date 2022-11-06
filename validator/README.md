# Validator container

This container uses [k6](https://k6.io/) to test that the service follows the same rules than the other services.

You can embed this validator in the docker-compose for the service you're working on as follow

```yaml

  validator:
    image: grafana/k6
    build: ../../validator
    command:
      - run
      - "--iterations"
      - "1"
      - "--env"
      - "SERVER_HOST=service:8080"
      - "/config/scenario.js"
    volumes:
      - ../../validator:/config:ro
    depends_on:
      - service

  service:
    image: jdrouet/eco-benchmark:service-rust-actix-sqlx-default
    build: .
    depends_on:
      - database
    environment:
      - DATABASE_URL=postgres://postgres:mysecretpassword@database:5432/postgres
      - DATABASE_POOL_MAX=20
    ports:
      - 8080:8080
    restart: unless-stopped

  database:
    image: jdrouet/eco-benchmark:database-default
    build: ../../migrations
    environment:
      - POSTGRES_PASSWORD=mysecretpassword
    healthcheck:
      test: "/usr/bin/psql -U postgres postgres -c \"\\d\""
      interval: 3s
      timeout: 1s
      retries: 20
    restart: unless-stopped
```

And the you can run `docker-compose up -d --build service` to start the service freshly built and then run `docker-compose run validator` to make sure everything works as expected.
