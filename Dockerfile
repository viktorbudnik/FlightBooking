FROM ubuntu:latest
LABEL authors="Viktor"
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]