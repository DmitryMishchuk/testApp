FROM adorsys/java:17

ENV SERVER_PORT 8080
ENV JAVA_OPTS -Xmx512m
ENV JAVA_TOOL_OPTIONS -Xmx512m

WORKDIR /opt/testapp

USER 1001

COPY ./target/test-app-0.0.1-SNAPSHOT.jar /opt/testapp/test-app-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD exec $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/testapp/test-app-0.0.1-SNAPSHOT.jar
