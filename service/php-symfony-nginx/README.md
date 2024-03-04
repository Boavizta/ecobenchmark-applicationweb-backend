# How to run

## Build the image
 For Mac with M1, you need to add --platform linux/amd64

```bash
docker build -t benchmark-php-symfony-nginx .
```

## Run the container
### Get database IP addresses
```bash
docker inspect \
  -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' eco-benchmark-database
```

Be careful to replace the IP address of the database (172.17.0.2) with the previously obtained result.

```bash
docker run --name=benchmark-php-symfony-nginx -d -p 8080:8080 -e DATABASE_URL='postgresql://postgres:mysecretpassword@172.17.0.2:5432/postgres' benchmark-php-symfony-nginx 
```
