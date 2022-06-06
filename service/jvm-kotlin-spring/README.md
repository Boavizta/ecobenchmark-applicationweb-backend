# How to run

## Build the image

```bash
docker build -t benchmark-kotlin-spring-jpa .
```

## Run the container

```bash
docker run --name=benchmark-kotlin-spring-jpa -d -p 8080:8080 \
  -e SPRING_DATASOURCE_URL='jdbc:postgresql://172.17.0.1:5432/postgres' \
  -e SPRING_DATASOURCE_USERNAME='postgres' \
  -e SPRING_DATASOURCE_PASSWORD='mysecretpassword' \
  benchmark-kotlin-spring-jpa
```
