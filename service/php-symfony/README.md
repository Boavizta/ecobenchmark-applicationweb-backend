# How to run

## Build the image

```bash
docker build -t benchmark-php-symfony .
```

## Run the container

```bash
docker run --name=benchmark-php-symfony -d -p 8080:8080 -e DATABASE_URL='postgresql://postgres:mysecretpassword@172.17.0.3:5432/postgres' benchmark-php-symfony
```
