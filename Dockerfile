FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk17
COPY --from=build /app/target/FoodMart-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/foodmart.war
EXPOSE 8080
