FROM php:8.1-apache-buster

RUN apt-get update && \
    apt-get install -y libpq-dev unzip && \
    rm -rf /var/cache/apt/archives /var/lib/apt/lists/* && \
    docker-php-ext-install pdo_pgsql && \
    a2enmod rewrite && \
    rm /etc/apache2/sites-available/000-default.conf && \
    rm /etc/apache2/sites-available/default-ssl.conf && \
    echo 'Listen 8080' > /etc/apache2/ports.conf && \
    curl https://getcomposer.org/installer -o composer-setup.php && \
    php composer-setup.php --install-dir=/usr/local/bin --filename=composer
COPY apache.conf /etc/apache2/sites-enabled/symfony.conf
COPY . /var/www/project
WORKDIR /var/www/project
RUN composer install
