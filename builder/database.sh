
#!/bin/bash

if test "$#" -ne 1; then
  echo "Invalid number of arguments..."
  echo
  echo "Usage: ./builder/database.sh <use_case>"
  echo
  echo "Available tags:"
  echo "default (the use case to compare to)"
  echo
  exit 1
fi

export ORG=${ORG:-jdrouet}
export use_case=$1

echo "Building database for use case $use_case"

docker buildx build --push --tag "$ORG/eco-benchmark:database-$use_case" ./migrations

exit 0

