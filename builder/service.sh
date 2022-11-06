#!/bin/bash

if test "$#" -ne 2; then
  echo "Invalid number of arguments..."
  echo
  echo "Usage: ./builder/service.sh <service> <use_case>"
  echo
  echo "Available services:"
  ls service
  echo
  echo "Available tags:"
  echo "default (the use case to compare to)"
  echo
  exit 1
fi

export ORG=${ORG:-jdrouet}
export service=$1
export use_case=$2

echo "Building service $service for use case $use_case"

docker buildx build --push --tag "$ORG/eco-benchmark:service-$service-$use_case" ./service/$service

exit 0

