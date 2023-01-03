#!/bin/bash
# wait for the SQL Server to come up
sleep 20s
#run the setup script to create the DB and the schema in the DB
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "yourStrong**Password!" -i "/usr/src/app/setup.sql"

