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

## Architecture Exploration