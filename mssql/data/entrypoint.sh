#!/bin/bash
/opt/mssql/bin/sqlservr & /usr/src/app/import-data.sh
/usr/src/app/import-data.sh &>/usr/src/app/log

