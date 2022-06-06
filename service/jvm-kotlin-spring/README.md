# How to run

## Build the image

```bash
docker build -t benchmark-kotlin-spring-jpa .
```

## Run the container

```bash
docker run --name=benchmark-kotlin-spring-jpa -d -p 8080:8080 \
  -e SPRING_DATASOURCE_URL='jdbc:mysql://172.17.0.1:3306/ecobenchmark' \
  -e SPRING_DATASOURCE_USERNAME='root' \
  -e SPRING_DATASOURCE_PASSWORD='mysqlpw' \
  benchmark-kotlin-spring-jpa
```
