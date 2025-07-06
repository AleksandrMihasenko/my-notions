# My notions

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Learning Objectives](#learning-objectives)
- [Getting Started](#getting-started)
- [TODO and Ideas](#note-and-ideas)

## Overview
My notions is a full-stack application that enables users to write, publish, read articles. It should offer features as user registration, secure email validation, articles management (including creation, updating, sharing), versioning, translating.
My personal project built to explore backend development, architecture patterns, and moder technologies in practice.
The application ensures security using JWT tokens and adheres to best practices in REST API design.
The backend is built with Spring Boot 3 and Spring Security 6, while the frontend is developed using React.

## Features
- User Registration: Users can register for a new account.
- Email Validation: Accounts are activated using secure email validation codes.
- User Authentication: Existing users can log in to their accounts securely.
- Role-based access control.
- Version history for notes.
- Full-text search.
- Multilingual UI and translation notes to different languages.
- Export to PDF.
- Notes Management: Users can create, update, share, and archive their notes.
- Tags Management: Users can create, update, share, and archive their tags.

## Technologies Used
List of technologies.

### Backend

- Java
- Spring Boot
- Spring Web / Validation
- Spring Security
- JWT Token Authentication
- Spring Data JPA
- OpenAPI and Swagger UI Documentation

### Frontend

- React
- Typescript
- Component-Based Architecture using FSD approach
- Unit tests using Jest and React test library
- Screenshot test using Storybook and Loki

### Dev and Infrastructure

- Docker + Docker Compose
- Testcontainers / JUnit
- MySQL
- Flyway (DB migrations)
- Redis
- Kafka / RabbitMQ
- Websocket
- CI / CD (GitHub Actions)

## Learning Objectives

By following this project, I would like to learn:

- Learn **security**, stateless auth, and role management, securing an application using JWT tokens with Spring Security
- Registering users and validating accounts via email
- Utilizing inheritance with Spring Data JPA
- Implementing the service layer and handling application exceptions
- Object validation using JSR-303 and Spring Validation
- Handling custom exceptions
- Implementing pagination and REST API best practices
- Using Spring Profiles for environment-specific configurations
- Documenting APIs using OpenAPI and Swagger UI
- Modularize backend (Clean Architecture), learn **separation of concerns** and scalable structure
- Create multi-module architecture, prepare for **microservices** or **modular monolith** design
- Learn **event-driven architecture** and message brokering, implement Kafka / RabbitMQ for domain events
- Understand **in-memory caching**, TTL, and performance optimization, add Redis for caching
- Set up WebSockets , build **real-time communication** features
- Create a versioning system for Notes, explore **event sourcing** and content history
- Dockerized the infrastructure
- Practice **DevOps basics** and automated testing, add CI/CD with GitHub Actions |
- Understand **observability** and performance tracking, add monitoring with Grafana/Clickhouse

## Getting Started

To get started with the Book Social Network project, follow the setup instructions in the respective directories:

- [Backend Setup Instructions]
- [Frontend Setup Instructions]

## TODO and Progress

### 🔄 In Progress
- [ ] Migrate frontend from Svelte to React
- [ ] Move business logic to a core module (Clean Architecture)
- [ ] Add DTOs, validation, and global error handler

### Done
- [x] Basic CRUD for Notes and Tags
- [x] Docker Compose: backend + frontend + MySQL
- [x] Initial backend architecture: controller-service-dao-repository
- [x] Basic UI: form to create notes, list rendering

### Future Tasks
#### Architecture
- [ ] Separate core / infra modules (Clean Architecture)
- [ ] Implement aggregates, value objects (DDD)
- [ ] Add domain events + event bus
- [ ] Implement RBAC (roles & access control)

#### Technologies
- [ ] Add Redis caching
- [ ] Integrate Kafka or RabbitMQ
- [ ] Add WebSocket for real-time updates
- [ ] CI/CD pipeline with GitHub Actions
- [ ] Add monitoring & metrics (Prometheus + Grafana)

#### Niches & Experiments
- [ ] AI-powered summary generation (OpenAI)
- [ ] Version history for notes
- [ ] Export to PDF
- [ ] Real-time collaboration between users
- [ ] Data visualization & reports
- [ ] Localization and theme support
- [ ] Payment & subscription support
