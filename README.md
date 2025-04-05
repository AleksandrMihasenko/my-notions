# My notions

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Learning Objectives](#learning-objectives)
- [Getting Started](#getting-started)
- [TODO and Ideas](#note-and-ideas)

## Overview
My notions is a full-stack application that enables users to write, publish, read articles. It should offer features as user registration, secure email validation, articles management (including creation, updating, sharing), versioning, translating. The application ensures security using JWT tokens and adheres to best practices in REST API design. The backend is built with Spring Boot 3 and Spring Security 6, while the frontend is developed using React.

## Features
- User Registration: Users can register for a new account.
- Email Validation: Accounts are activated using secure email validation codes.
- User Authentication: Existing users can log in to their accounts securely.
- Articles Management: Users can create, update, share, and archive their books.

## Technologies Used
List of technologies.

### Backend

- Spring Boot 3
- Spring Security 6
- JWT Token Authentication
- Spring Data JPA
- JSR-303 and Spring Validation
- OpenAPI and Swagger UI Documentation
- Docker
- GitHub Actions
- Keycloak

### Frontend (book-network-ui)

- React
- Typescript
- Component-Based Architecture using FSD approach
- Unit tests using Jest and React test library
- Screenshot test using Storybook and Loki

## Learning Objectives

By following this project, I would like to learn:

- Securing an application using JWT tokens with Spring Security
- Registering users and validating accounts via email
- Utilizing inheritance with Spring Data JPA
- Implementing the service layer and handling application exceptions
- Object validation using JSR-303 and Spring Validation
- Handling custom exceptions
- Implementing pagination and REST API best practices
- Using Spring Profiles for environment-specific configurations
- Documenting APIs using OpenAPI and Swagger UI
- Implementing business requirements and handling business exceptions
- Dockerizing the infrastructure
- CI/CD pipeline & deployment
- Usage of Redis, RabbitMQ/Kafka for async tasks
- Add monitoring with Grafana/Clickhouse

## Getting Started

To get started with the Book Social Network project, follow the setup instructions in the respective directories:

- [Backend Setup Instructions]
- [Frontend Setup Instructions]

## TODO and Ideas

- [x] Setup project with docker
- [x] Add database
- [x] Create simple CRUD
- [ ] Add pagination and filtration
- [ ] Implement search
- [ ] Implement event systems with message brokers
- [ ] Create simple UI for app using React
- [ ] Login functionality
- [ ] I18n for UI
- [ ] write backend tests
- [ ] write frontend tests
- [ ] setup CI/CD
