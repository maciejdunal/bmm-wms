FROM mcr.microsoft.com/mssql/server
USER root
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app/
RUN chmod +x /usr/src/app/import-data.sh
EXPOSE 1433
USER mssql
ENTRYPOINT ["/bin/bash", "./entrypoint.sh"]

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

FROM node:alpine
RUN npm install -g newman newman-reporter-htmlextra
WORKDIR /etc/newman
ENTRYPOINT ["newman"]

# Add dockerize tool -------------------
RUN apk add --no-cache openssl
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

CMD ["/sayhello"]