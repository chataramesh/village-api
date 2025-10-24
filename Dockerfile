#pull jdk
from openjdk:11-jdk-slim
#working directory
WORKDIR /app			
#move jar 
COPY target/*.jar app.jar
#expose port 
EXPOSE 9898
#run the jar
ENTRYPOINT ["java","-jar","app.jar"]
