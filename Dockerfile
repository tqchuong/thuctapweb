FROM tomcat:10.1-jdk17

# Copy file .war vừa build vào thư mục deploy của Tomcat
COPY target/FoodMart-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/foodmart.war

EXPOSE 8080
