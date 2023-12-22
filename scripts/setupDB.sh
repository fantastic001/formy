#!/bin/sh

psql --host localhost --port 6666 --user postgres --password -c 'create database "ISA"'  -c 'grant ALL on database "ISA" to "postgres"'
