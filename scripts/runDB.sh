#!/bin/sh

docker run --hostname "psw_isa_db" --rm -it --name PSW_ISA_postgres -e POSTGRES_PASSWORD=admin --publish 6666:5432 postgres
