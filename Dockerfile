FROM openjdk:8
MAINTAINER ccyprus (ccyprus2015@gmail.com)
ADD ./target/Person_REST_CRUD_Dockerization.jar /app/Person_REST_CRUD_Dockerization.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/app/Person_REST_CRUD_Dockerization.jar"]

