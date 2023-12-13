# Notice Management Project
This project is about providing APIs for notice using Spring Boot.

## Project Overview
### 1. Authentication 
- register user:
  * **payload**: username, password
  * **validation**:
    - username cannot be null or blank
    - password cannot be null
- login: has the same payload and validation as register
### 2. Notice Controller
##### Prerequisite: user have to log in
- get notice list having startDate before now and endDate after now
- create new notice
- update notice
  * **payload**:
    - notice request: title, content, startDate, endDate, registerationDate
    - attachements: list of files
  * **validation**:
    - title cannot be null
    - content cannot be null
- delete notice: soft delete
- add view for notice: the new view will be increased when user read notice

## Core Technologies
- Java JDK 8
- Spring Boot 2 ecosystem
  - Web
  - Security
  - JPA
  - Validation
- Maven 3
- PostgresQL
- Lombok
- JWT
- JUnit, Mockito

## Project Instructions
- Install PostgresQL 
- Update database config in ./resources/application.properties
- Run Notice Management Project
- Use Postman API from file **Exam.postman_collection.json** to test APIs

## Project Quality
- Having 13 test cases succeeding
- 
## Key problem solving strategies
- For storing attachments, we are storing these on server. Moreover, the current api for uploading attachment just support for attachment with max size 10MB. Therefore, in future, we can improve these by using thirparty to store these attachments like Amazon S3, Cloudinary, Google Cloud Storage,... 
- For authentication, our project use JWT token. However, because of the lack of encryption of JWT token, we can replace this to Oauth 2.0, intergrating Google or Facebook authentication
- For robusting API response, we can apply cache Spring Boot cache or Redis to inquiring APIs in future.


