FROM mcr.microsoft.com/mssql/server
USER root
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY .. /usr/src/app/
RUN chmod +x /usr/src/app/import-data.sh
EXPOSE 1433
USER mssql
ENTRYPOINT ["/bin/bash", "./entrypoint.sh"]
