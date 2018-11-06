FROM openjdk:8-jdk-alpine
LABEL maintainer="dimascio@us.ibm.com"
COPY build/libs/ /usr/src/
WORKDIR /usr/src
EXPOSE 8080
CMD [ "java", "-jar", "examples-service-1.0.0.jar" ]
