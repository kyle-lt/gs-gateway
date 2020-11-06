#!/bin/bash

mvn clean package && cp target/gs-gateway-0.1.0.jar docker

docker-compose build gateway

exit 0
