FROM openjdk:8
MAINTAINER ccyprus (ccyprus2015@gmail.com)
ADD ./target/Person_REST_CRUD_CI.jar /personcrud/Person_REST_CRUD_CI.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/personcrud/Person_REST_CRUD_CI.jar"]

