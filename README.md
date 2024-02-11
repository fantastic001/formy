# Formy

# How to run

## Linux

	cd scripts
	./runDB.sh 
	./setupDB.sh
	./runBackend.sh ../backend
	./runFrontend.sh ../frontend

API documentation is available at http://localhost:9000/swagger-ui.html

## Windwos


	cd scripts/windows
	./runDB.ps1

Login to your postgresql database (port 6666, localhost, user postgres and password is admin)
	create server with port 6666, localhost;
	create database "ISA"; 
	grant ALL on database "ISA" to "postgres";

In Powershell then type:

	./runBackend.ps1 -BackendPath ../../backend
	./runFrontend.ps1 -FrontendPath ../../frontend
./runBackendTest.ps1 -BackendPath ../../backend


# Running E2E tests 

go to e2e test environment

	cd e2e_tests
	virtualenv env 
	. env/bin/activate 
	pip install -r requirements.txt 

Run tests:

	python -m e2e