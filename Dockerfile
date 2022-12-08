#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY src /testapp/src
COPY pom.xml /testapp/
RUN mvn -f /testapp/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM adorsys/java:17

ENV SERVER_PORT 8080
ENV JAVA_OPTS -Xmx512m
ENV JAVA_TOOL_OPTIONS -Xmx512m

WORKDIR /opt/testapp
USER 1001
COPY --from=build testapp/target/test-app-0.0.1-SNAPSHOT.jar /opt/testapp/test-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD exec $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/testapp/test-app-0.0.1-SNAPSHOT.jar
