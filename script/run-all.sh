#!/bin/bash

set -xe

# run <service> <use_case>
function run_once() {
  run_id=$(date +%s)
  extra_vars="server_application_host=$SERVER_APPLICATION_HOST"
  extra_vars="$extra_vars server_application_user=$SERVER_APPLICATION_USER"
  extra_vars="$extra_vars server_database_host=$SERVER_DATABASE_HOST"
  extra_vars="$extra_vars server_database_user=$SERVER_DATABASE_USER"
  extra_vars="$extra_vars server_runner_host=$SERVER_RUNNER_HOST"
  extra_vars="$extra_vars server_runner_user=$SERVER_RUNNER_USER"
  ansible-playbook \
    -i script/hosts \
    -i script/inventory-$1-$2 \
    script/site.yml \
    --skip-tags install \
    --extra-vars "$extra_vars output_directory=$PWD/results run_id=$run_id"
}

function run() {
  for i in {1..10}; do
    run_once $1 $2
  done
}

# run go-pgx default

run jvm-kotlin-spring default

# run node-express-sequelize default

# run php-symfony default

# run rust-actix-sqlx default

