FROM openjdk:17-alpine
EXPOSE 8080
ADD target/bokhandel.jar bokhandel.jar
ENTRYPOINT ["java","-jar","/bokhandel.jar"]