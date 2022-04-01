#!/bin/bash

docker buildx build --output type=local,dest=$PWD .
