FROM maven:latest
ENV APP_HOME=/app/
COPY /pom.xml $APP_HOME
COPY /src $APP_HOME/src/
WORKDIR $APP_HOME
RUN mvn package -DskipTests
ENV JAR_FILE=target/bokhandel.jar
RUN mv ${JAR_FILE} /bokhandel.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bokhandel.jar"]