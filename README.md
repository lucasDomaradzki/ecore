# Ecore

Ecore application

### Solution

The goal of this application is to consume the existing service/api that provides user and team information and be keep the role information for users.

The application reaches for existing service to check if user/team is still available/active and updates database to keep track of changes and have valid membership information.

Predefined DEVELOPER, PRODUCT_OWNER and TESTER roles are inserted in database upon application start up.

If a valid user/team is found in existing service it is inserted in database along with its corresponding membership relationship and primary role as DEVELOPER.

Role is only changed if intentionally defined by the service, as existing service keep no role information.

A user or team is never deleted/removed from database, unless the request for existing service returns no result for user/team that exists in database.

##### System requirements
- Java 11
- Spring boot 2.7.3
- Docker version 20.10.17
- Docker compose v2.6.0
- Mysql 8.0.18
- Maven 3.6.3

##### How to run the application

Run below commands to install/start application

- mvn clean install -DskipTests
- docker compose up -d

##### How to follow application logs

- docker compose logs -f ecore-api

##### How to access API Documentation (Swagger)

- Access http://localhost:9091/ecore/ on web browser

##### How to debug application

- Avaiable default debug port is: 9999 and application can be remotely debbuged

##### How to connect to Database

- Database is exposed through port: 3306
- User: ecore
- Password: ecore
- Database name: ecore

>> Database can be accessed through docker container through command:
> 
> docker exec -it `docker ps | grep ecore-mysql | awk '{print $1;}'` bash