# Formy

# How to run

## Linux

	cd scripts
	./runDB.sh 
	./setupDB.sh
	./runBackend.sh ../backend
	./runFrontend.sh ../frontend

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
