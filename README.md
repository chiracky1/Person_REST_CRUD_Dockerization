# Person REST CRUD Dockerization

This is a simple REST API for doing CRUD operations on a Person entity. 
This microservice covers a learning need. 
All layers of this microservice (DAO, Service, and Controller) are covered by unit tests. 
An end-to-end test (integration test) is also included. 

This microservice has been containerized via Docker

# Technologies

- Spring Boot 2.3.1
- Java 8  
- MySQL 5.6 Database (user: root, pwd: root)
- Mockito, Junit 5
- Docker (Dockerfile, docker-compose)

# Local install

docker compose -f docker-compose.yml up -d





