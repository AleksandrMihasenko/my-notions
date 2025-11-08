# My notions

<div style="border-left: 1px solid grey; padding: 0 8px">
Single source of truth for the repo: vision → architecture → how to run → roadmap → learning log.
</div>

## Table of Contents
- [Overview](#overview)
- [Product idea](#product-idea)
- [Technologies Used](#technologies-used)
- [Learning Objectives](#learning-objectives)
- [Getting Started](#getting-started)
- [TODO and Ideas](#note-and-ideas)

## Overview
Personal product to learn backend & systems thinking (security, data, real‑time), then grow into specialization (data / realtime), keeping 80% practice.

Stack: Java + Spring Boot (API), React + TS (UI), MySQL, Docker Compose. Redis (cache), Kafka/RabbitMQ (events), WebSocket (RT updates) — progressively added.

Run locally: docker compose up -d → backend on :8080, frontend on :5173 (adjust if changed). OpenAPI at /swagger-ui.html when backend is up.

## Product idea
A Notion‑like knowledge app: notes, tags, versions, full‑text search, translations, export, collaboration.

Engineering goals:
* Foundation in backend (Spring security, REST, SQL, migrations, testing).
* Build real‑time features (WebSocket), event‑driven thinking (Kafka/RabbitMQ), caching (Redis), observability.
* Explore specializations: (a) Data pipelines/analytics; (b) Real‑time systems.
* Practice architecture: clean layering, modularization, DDD‑вкусы, CI/CD.

Guiding rules: ship small, document decisions, measure.

## 2) Monorepo layout
```
my-notions/
├─ backend/            # Spring Boot app (REST API, security, data, tests)
├─ frontend/           # React + TS app (FSD-ish structure, Storybook/Loki)
├─ deploy/             # docker, nginx, compose, envs
├─ .github/workflows/  # CI (lint/test/build)
├─ Makefile            # convenience commands
└─ README.md           # this document
```
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
- [ ] JWT and hashing algorithms
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
- [ ] Implement authorization & authentication
- [ ] Different API approaches (REST, GraphQL, gRPC, websockets etc)
