# Stage 1: Build the JAR using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#pull jdk
FROM eclipse-temurin:17-jdk-jammy
#working directory
WORKDIR /app			
#move jar 
COPY --from=build /app/target/*.jar app.jar
#expose port 
EXPOSE 9898
#run the jar
ENTRYPOINT ["java","-jar","app.jar"]
