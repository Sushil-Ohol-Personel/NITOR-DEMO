FROM openjdk:11.0.16-jdk
WORKDIR /app
COPY  /home/runner/work/nit-javahib/nit-javahib/target/*.jar app.jar
EXPOSE 8800
CMD ["java","-jar","-Dmultitenancy.poc.dataSources[0].url=jdbc:mysql://10.21.12.183:3306/test1","-Dmultitenancy.poc.dataSources[1].url=jdbc:mysql://10.21.12.183:3306/test2","app.jar"]
#CMD ["java","-jar","app.jar"]
