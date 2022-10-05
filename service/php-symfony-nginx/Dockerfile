FROM php:8.1.11-fpm-buster

RUN apt-get update &&\
    apt-get install -y nginx wget libpq-dev &&\
    docker-php-ext-install pdo_pgsql opcache &&\
    curl https://getcomposer.org/installer -o composer-setup.php && \
    php composer-setup.php --install-dir=/usr/local/bin --filename=composer

COPY docker/www.conf /usr/local/etc/php-fpm.d/www.conf
RUN rm /usr/local/etc/php-fpm.d/docker.conf /usr/local/etc/php-fpm.d/zz-docker.conf && \
    mkdir -p /var/run/php && \
    chown www-data:www-data /var/run/php
COPY docker/php.ini /usr/local/etc/php/php.ini
COPY docker/default.conf /etc/nginx/sites-available/default

COPY . /var/www/project
WORKDIR /var/www/project
RUN composer install --prefer-dist --no-dev --no-progress --no-scripts --no-interaction && \
    composer dump-autoload --no-dev --classmap-authoritative && \
    composer dump-env prod && \
    php bin/console cache:warmup --env prod

COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
