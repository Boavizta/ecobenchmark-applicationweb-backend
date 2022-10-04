#!/usr/bin/env bash

php-fpm &
crond -L /proc/1/fd/1 -b -l 8 -c /etc/cron.d &
nginx -g "daemon off;"