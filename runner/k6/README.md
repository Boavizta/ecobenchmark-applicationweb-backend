# K6 runner

You can runner k6 locally by starting the server and then running the following command

```bash
docker run --rm -it \
    --net host \
    -v $(pwd)/runner/k6:/config:ro \
    grafana/k6 run /config/scenario.js \
    --vus 10 --duration 1m \
    -e SERVER_HOST=localhost:8081
```

To start the server, there is a docker-compose file at the root of the repository

```bash
docker-compose up php-symfony
```
