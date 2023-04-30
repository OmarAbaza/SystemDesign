FROM maven:3.5-jdk-8 AS build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/app/target/systemdesign-0.0.1-SNAPSHOT.jar systemdesign.jar
ENTRYPOINT ["java", "-jar","systemdesign.jar"]
EXPOSE 8080