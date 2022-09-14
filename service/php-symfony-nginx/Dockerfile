FROM ubuntu:22.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update -yqq && apt-get install -yqq software-properties-common > /dev/null
RUN LC_ALL=C.UTF-8 add-apt-repository ppa:ondrej/php
RUN apt-get update -yqq > /dev/null && \
    apt-get install -yqq nginx git unzip curl \
    php8.1-cli php8.1-fpm php8.1-pgsql  \
    php8.1-mbstring php8.1-xml php8.1-curl > /dev/null

RUN curl -sSL https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

COPY docker/symfony.prod.ini /etc/php/8.1/fpm/conf.d/symfony.ini

RUN sed -i "s|pm = dynamic|pm = static|g" /etc/php/8.1/fpm/pool.d/www.conf
RUN sed -i "s|pm.max_children = 5|pm.max_children = 20|g" /etc/php/8.1/fpm/pool.d/www.conf

WORKDIR /symfony
ADD ./composer.json /symfony/
RUN mkdir -m 777 -p /symfony/var/cache/{dev,prod} /symfony/var/log
RUN COMPOSER_ALLOW_SUPERUSER=1 composer install --prefer-dist --no-dev --no-progress --no-scripts --no-interaction
ADD . /symfony
RUN COMPOSER_ALLOW_SUPERUSER=1 composer dump-autoload --no-dev --classmap-authoritative
RUN COMPOSER_ALLOW_SUPERUSER=1 composer dump-env prod

RUN php bin/console cache:warmup --env prod

EXPOSE 8080

CMD service php8.1-fpm start && \
    nginx -c /symfony/docker/nginx.conf