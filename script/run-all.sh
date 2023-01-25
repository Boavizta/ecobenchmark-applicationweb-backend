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
    -i script/inventory-$1 \
    script/site.yml \
    --skip-tags install \
    --extra-vars "$extra_vars service=$1 use_case=$2 output_directory=$PWD/results run_id=$run_id"
}

function run() {
  for i in {1..10}; do
    run_once $1 $2
  done
}

run go-pgx no-index

run jvm-kotlin-spring no-index

run jvm-java-quarkus no-index

run jvm-java-quarkus-reactive no-index

run native-java-quarkus no-index

run node-express-sequelize no-index

run php-symfony no-index

run rust-actix-sqlx no-index

