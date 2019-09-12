# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="andre957@libero.it"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

RUN mkdir -p /opt/app

COPY target/demo-0.0.1-SNAPSHOT.jar /opt/app
CMD  ["java","-jar","/opt/app/demo-0.0.1-SNAPSHOT.jar"]
