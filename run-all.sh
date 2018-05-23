#!/usr/bin/env bash

deploy() {
    echo $1
}

curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar

deploy config-service
deploy discovery-service
deploy payment
deploy user
deploy videos
deploy gateway-service
deploy frontend