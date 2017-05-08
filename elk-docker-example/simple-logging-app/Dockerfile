FROM openjdk:8-jre-alpine
WORKDIR /simpleLoggingApp
ADD target/simple-logging-app*-full.jar /simpleLoggingApp/app.jar
RUN mkdir /logs
CMD ["/bin/sh", "-c", "java -jar app.jar > /logs/app-logs.txt"]
