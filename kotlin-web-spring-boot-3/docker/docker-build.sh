#!/bin/bash

# http://redsymbol.net/articles/unofficial-bash-strict-mode/
set -e
set -u

# https://www.shellcheck.net/
# Unavailable in POSIX sh
set -o pipefail
IFS=$'\n\t'

MODE=$1
JAR_FILE=$2
DOCKER_USERNAME=vndevteam
IMAGE_NAME=$DOCKER_USERNAME/kotlin-web-spring-boot-3
TIME=$(date +%s)

if [[ $MODE == 'cnb-jdk' ]]; then
	./gradlew bootBuildImage "--imageName=$IMAGE_NAME:$MODE"
elif [[ $MODE == 'cnb-jre' ]]; then
  ./gradlew bootBuildImage "--imageName=$IMAGE_NAME:$MODE" -Dbp.jvm.type=JRE
elif [[ $MODE == 'cnb-jlink' ]]; then
	./gradlew bootBuildImage "--imageName=$IMAGE_NAME:$MODE" -Dbp.jvm.jlink.enabled=true
elif [[ $MODE == 'jib-jre-alpine' ]]; then
	./gradlew jibDockerBuild "--image=$IMAGE_NAME:$MODE"
elif [[
	$MODE == 'dev' ||
	$MODE == 'dev-debug' ||
	$MODE == 'dev-jlink'
]]; then
	export DOCKER_BUILDKIT=1
	docker build -f "./docker/$MODE.Dockerfile" -t "$IMAGE_NAME:$MODE" --build-arg JAR_FILE=$JAR_FILE .
fi

echo "Done in $(($(date +%s)-TIME)) seconds"
