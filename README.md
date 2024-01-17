# FSE Application

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)

## Introduction

The FSE (Your Project Name) application is designed to (provide a brief description of your project).

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 21 or later
- Maven (for building and managing dependencies)
- Spring Boot
- MySQL running in a docker container 
- Refer to the config/DatabaseConfiguration.java to modify the connection properties accordingly

## Installation

1. Clone the repository:

   `git clone https://github.com/sridharc72/fse.git`
   `cd fse`

2. Build the project using maven

    `mvn clean install`

## Running the Application

Run the application in your favorite IDE, such as Netbeans or Visual Studio Code.

Alternatively, you can run the application by executing the following command

    java -jar target/fse-application.jar

The application will start, and you can access it at http://localhost:8080.

## API Endpoints

- Endpoint 1: /api/entity-relationships/by-source/{sourceEntityId}

Description: Retrieve relationships by source entity.
Method: GET
Parameters: sourceEntityId (Long), relationshipTypes (Optional Set<String>)

Examples: 
- http://localhost:8080/fse/api/entity-relationships/by-source/7
- http://localhost:8080/fse/api/entity-relationships/by-source/7?relationshipTypes=TRANSPORT
- http://localhost:8080/fse/api/entity-relationships/by-source/7?relationshipTypes=TRANSFER,MUTUAL_AID,TRANSPORT

- Endpoint 2: /api/entities

Description: Retrieve all entities