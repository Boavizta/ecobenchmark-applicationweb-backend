# How to run

## Build the image

```bash
docker build -t benchmark-php-symfony-apache2-dev .
```

## Run the container

```bash
docker run --name=benchmark-php-symfony-apache2-dev -d -p 8080:8080 -e DATABASE_URL='postgresql://postgres:mysecretpassword@172.17.0.2:5432/postgres' benchmark-php-symfony-apache2-dev
```
