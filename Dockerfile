FROM mcr.microsoft.com/mssql/server

USER root

#RUN apt-get -y update

#RUN mkdir -p /usr/src/app
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app/
# Grant permissions for the import-data script to be executable
#RUN chmod +x apps/mssql/data/import-data.sh

#RUN ["ls", "/usr/src/app/ &>/usr/src/app/logg.log"]
#RUN ["chmod", "+x", "/usr/src/app/import-data.sh"]
#ENTRYPOINT "/usr/src/app/entrypoint.sh"
#ENTRYPOINT ["/usr/src/app/entrypoint.sh"]
RUN chmod +x /usr/src/app/log.txt
RUN chmod +x /usr/src/app/import-data.sh
#RUN chmod +x /usr/src/app/setup.sql
EXPOSE 1433

USER mssql
ENTRYPOINT ["/bin/bash", "./entrypoint.sh"]
#ENTRYPOINT /bin/bash ./entrypoint.sh