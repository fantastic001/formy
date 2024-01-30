#!/bin/sh

if [ $1 == "" ];then 
	echo "Usage: $0 backend_path"
	echo "backend_path - path to root where backend is located"
fi

BACKEND_DIR="$(readlink -f $1)"
SOURCE_DIR="$BACKEND_DIR/src/main/java"
RESOURCES_DIR="$BACKEND_DIR/src/main/resources"
TEST_DIR="$BACKEND_DIR/src/test"

APPLICATION_PROPERTIES="$RESOURCES_DIR/application-docker.properties"

if [ ! -f "$APPLICATION_PROPERTIES" ]; then
	echo "File $APPLICATION_PROPERTIES does not exist"
	exit 1
fi



function run_docker() {
	docker run \
		--hostname backend.psw \
		--link PSW_ISA_postgres \
		-it --rm \
		--name formy_backend \
		-v "$BACKEND_DIR/pom.xml":/usr/src/mymaven/pom.xml \
		-v "$SOURCE_DIR":/usr/src/mymaven/src/main/java \
		-v "$TEST_DIR":/usr/src/mymaven/src/test \
		-v "$APPLICATION_PROPERTIES":/usr/src/mymaven/src/main/resources/application.properties \
		-v "$BACKEND_DIR/.m2/":/root/.m2 \
		-w /usr/src/mymaven \
		--publish 9000:8080 \
		maven mvn $1
}

if [ $2 = "test" ]; then
	run_docker test
elif [ $2 = "package" ]; then
	run_docker package
elif [ $2 = "install" ]; then
	run_docker install
elif [ $2 = "clean" ]; then
	run_docker clean
elif [ $2 = "compile" ]; then
	run_docker compile
elif [ $2 = "spring-boot:run" ]; then
	run_docker spring-boot:run
elif [ $2 = "production" ]; then 
	APPLICATION_PROPERTIES="$RESOURCES_DIR/application-prod.properties"
	if [ ! -f "$APPLICATION_PROPERTIES" ]; then
		echo "File $APPLICATION_PROPERTIES does not exist"
		exit 1
	fi
	run_docker spring-boot:run
else
	run_docker spring-boot:run
fi
