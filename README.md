# my-notions

> **Learning playground** for backend engineering & specialization exploration

A Notion-like knowledge management app built to master backend fundamentals, then explore three specializations (Real-time, AI, Data Engineering) to discover what I enjoy most.

**Philosophy**: 80% practice, 20% theory. Ship small, iterate fast, document decisions.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Project Phases](#project-phases)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Development Progress](#development-progress)
- [Architecture Decisions](#architecture-decisions)
- [Learning Approach](#learning-approach)
- [Notes](#notes)
- [Contributing](#contributing)

---

## Overview

### What is this?

A full-stack Notion clone serving as my **primary learning vehicle** for:
1. **Backend Foundation** : Spring Boot, SQL, REST, Testing
2. **Specialization Exploration** : Real-time, AI, Data Engineering
3. **Portfolio Project**: Deployed production app for interviews

### Why this project?

- **Practice-first approach**: Learn by building, not by tutorials
- **Exploration tool**: Try different specializations hands-on
- **Portfolio piece**: Demonstrate full-stack + specialized skills
- **Safe playground**: Break things, experiment, learn from mistakes

### Core Features (MVP)

- 📝 Rich text notes with nested structure
- 🔐 User authentication (JWT)
- 👥 Workspace collaboration
- 🔍 Full-text search
- 🎨 Clean, responsive UI

### Exploration Features

- ⚡ **Real-time**: Collaborative editing, presence, WebSocket sync (Month 3)
- 🤖 **AI Integration**: Writing assistant, semantic search, RAG (Month 4)
- 📊 **Data Engineering**: Analytics, event tracking, ETL pipeline (Month 5)

---

## Project Phases

### Phase 1: MVP v0.1  ✅ CURRENT
**Goal**: Solid fullstack foundation, production-ready code

**Backend**:
- ✅ User registration & JWT authentication
- ✅ Workspace management
- ✅ Pages CRUD (create, read, update, delete)
- ✅ Spring Boot + PostgreSQL + Flyway
- ✅ Tests (Unit + Integration, >70% coverage)
- ✅ OpenAPI documentation

**Frontend**:
- ✅ React + TypeScript + Vite
- ✅ Feature-Sliced Design (FSD) architecture
- ✅ Auth flow (login/register)
- ✅ Workspace & Pages UI
- ✅ Tailwind CSS styling

**Deliverable**: Working full-stack app, locally runnable, well-tested

---

### Phase 2: MVP v0.2 - Real-time Exploration
**Goal**: Deep dive into real-time systems, understand if I like it

**Features**:
- Typing indicators (show who's typing)
- Online presence (who's viewing a page)
- Real-time page updates (WebSocket broadcast)
- Simple collaborative text editing
- Cursor position tracking

**Tech**:
- Spring WebSocket + STOMP
- WebSocket client (React)
- Redis pub/sub (basic)
- Conflict resolution (Last Write Wins)

**Decision Criteria**:
- Interest (1-10): Is this engaging?
- Flow (1-10): Do I enter flow state?
- Complexity (1-10): How challenging?
- Market (1-10): Job demand?
- Growth (1-10): Long-term potential?

---

### Phase 3: MVP v0.3 - AI Integration
**Goal**: Deep dive into AI integration, understand if I like it

**Features**:
- Writing assistant (continue text, improve, summarize)
- Tone change (formal/casual)
- Embeddings generation for pages
- Semantic search (similarity-based)
- RAG: "Ask questions about my notes"
- Streaming responses

**Tech**:
- OpenAI API / Anthropic Claude
- Vector embeddings (text-embedding-ada-002)
- Vector database (Pinecone or pgvector)
- LangChain (if needed)
- Cost tracking & optimization

**Decision Criteria**: Same 5 metrics as Real-time

---

### Phase 4: MVP v0.4 - Data Engineering
**Goal**: Explore data engineering, understand if I like it

**Features** (2 weeks):
- Event tracking (page views, edits, searches)
- User behavior analytics
- Audit log (event sourcing pattern)
- Simple ETL pipeline
- Analytics dashboard (basic charts)

**Tech**:
- Spring Events / custom event system
- Analytics service
- Scheduled jobs (aggregations)
- Simple dashboard (React + recharts)

**Decision Criteria**: Same 5 metrics

---

### Phase 5: Production Ready
**Goal**: Deploy to production, portfolio-ready

**Tasks**:
- Security hardening (CORS, rate limiting, input validation)
- Performance optimization (DB indexes, query tuning, caching)
- Monitoring & logging (Spring Actuator, structured logs)
- Docker containerization
- Deploy to Railway/DigitalOcean
- CI/CD pipeline (GitHub Actions)
- Documentation polish

**Deliverable**: Live demo + spectacular README + demo video (optional)

---

### Phase 6: Specialization Deep Dive
**Based on exploration results**, choose ONE primary specialization and go deep:

- **If Real-time**: OT/CRDT, distributed real-time, scaling WebSockets
- **If AI**: Advanced RAG, vector DB optimization, LLM orchestration
- **If Data**: Airflow, dbt, data modeling, quality frameworks

---

## Tech Stack

### Backend
- **Language**: Java 17+
- **Framework**: Spring Boot 3.x
  - Spring Web (REST)
  - Spring Data JDBC → JPA/Hibernate (Month 3+)
  - Spring Security (JWT)
  - Spring WebSocket (Month 3)
  - Spring Events (event-driven)
- **Database**: PostgreSQL
- **Migrations**: Flyway
- **Testing**: JUnit 5, Mockito, TestContainers
- **API Docs**: OpenAPI / Swagger

### Frontend
- **Framework**: React 19+
- **Language**: TypeScript
- **Build Tool**: Vite
- **Architecture**: Feature-Sliced Design (FSD)
- **Styling**: Tailwind CSS
- **State**: Redux Toolkit (early), Context API (where needed)
- **i18n**: i18next
- **Testing**: Jest

### Infrastructure
- **Containerization**: Docker, Docker Compose
- **CI/CD**: GitHub Actions
- **Deployment**: DigitalOcean
- **Monitoring**: maybe Sentry

### Exploration Tech (Added Progressively)
- **Real-time** : WebSocket, Redis, STOMP
- **AI** : OpenAI/Anthropic API, Pinecone/Weaviate, LangChain
- **Data** : Event system, analytics, scheduled jobs

---

## Project Structure

```
my-notions/
├── backend/              # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/aleksandrm/mynotions/
│   │   │   │       ├── controller/   # REST endpoints
│   │   │   │       ├── service/      # Business logic
│   │   │   │       ├── repository/   # Data access
│   │   │   │       ├── model/        # Domain models
│   │   │   │       ├── dto/          # Data transfer objects
│   │   │   │       ├── mapper/       # DTO mappers
│   │   │   │       ├── security/     # Auth filters, security config
│   │   │   │       ├── config/       # Spring configuration
│   │   │   │       └── utils/        # Helpers (JWT, passwords)
│   │   │   └── resources/
│   │   │       ├── db/migration/     # Flyway migrations
│   │   │       └── application.properties
│   │   └── test/                     # Tests
│   ├── pom.xml                       # Maven dependencies
│   └── README.md                     # Backend docs
│
├── frontend/             # React application
│   ├── src/
│   │   ├── app/          # App initialization
│   │   ├── pages/        # Page components (FSD)
│   │   ├── features/     # Feature modules (FSD)
│   │   ├── entities/     # Business entities (FSD)
│   │   ├── shared/       # Shared utilities (FSD)
│   │   └── widgets/      # Complex UI blocks (FSD)
│   ├── package.json
│   └── README.md         # Frontend docs
│
├── deploy/               # Deployment configs
│   ├── docker-compose.yml
│   ├── Dockerfile.backend
│   └── Dockerfile.frontend
│
├── .github/
│   └── workflows/        # CI/CD pipelines
│
├── Makefile              # Convenience commands
└── README.md             # This file
```

See [Backend README](./backend/README.md) and [Frontend README](./frontend/README.md) for details.

---

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- PostgreSQL (or use Docker)

### Quick Start

```bash
# 1. Clone the repository
git clone https://github.com/AleksandrMihasenko/my-notions.git
cd my-notions

# 2. Start infrastructure (PostgreSQL)
docker-compose -f deploy/docker-compose.yml up -d

# 3. Start backend
cd backend
./mvnw spring-boot:run
# Backend runs on http://localhost:8081
# Swagger UI: http://localhost:8081/swagger-ui.html

# 4. Start frontend (in another terminal)
cd frontend
npm install
npm run dev
# Frontend runs on http://localhost:5173
```

### Development

```bash
# Run backend tests
cd backend
./mvnw test

# Run frontend tests
cd frontend
npm test

# Build for production
make build
```

See detailed setup instructions in:
- [Backend Setup](./backend/README.md#setup)
- [Frontend Setup](./frontend/README.md#setup)

---

## Development Progress

### Backend Foundation

| Feature | Status | Notes |
|---------|--------|-------|
| User Registration | ✅ Done | Auth flow working |
| JWT Authentication | ✅ Done | Login/register issues tokens |
| Workspace CRUD | 📋 Planned | Month 1 |
| Pages CRUD | 📋 Planned | Month 1-2 |
| Tests (>70%) | 📋 Planned | Throughout Month 1-2 |
| React UI | 🔄 In Progress | Scaffolding in place |

### Exploration Status

| Phase | Status | Decision |
|-------|--------|----------|
| Real-time Systems | 📋 Not Started | TBD Month 3 |
| AI Integration | 📋 Not Started | TBD Month 4 |
| Data Engineering | 📋 Not Started | TBD Month 5 |

---

## Architecture Decisions

### Key Decisions Log
See [Architecture Decision Records](./docs/adr/) for detailed ADRs (coming soon).

### Current Architecture

**Pattern**: Simple Layered Architecture (Controller → Service → Repository)

**Rationale**:
- Clean separation of concerns
- Easy to understand and test
- Good for learning Spring basics
- Can evolve to Clean Architecture later

### Future Evolution

**Future**: 
- Add DTO layer (separate Entity from API contracts)
- Introduce domain events
- Maybe Clean Architecture (if project grows)

**Possible**:
- Event-driven patterns
- CQRS elements (if needed for real-time)

---

## Learning Approach

**80% Practice / 20% Theory**

- **Project first**: Build features, learn concepts as needed
- **Small iterations**: Ship MVPs, then improve
- **Test everything**: Learn testing by writing tests
- **Document decisions**: ADRs for architectural choices
- **Regular reflection**: Decision logs after each exploration phase

**Not trying to build enterprise product** - trying to **learn effectively** while building something real.

---

## Notes

### What This Project Is
- ✅ Learning playground
- ✅ Exploration tool
- ✅ Portfolio piece
- ✅ Practice ground for backend engineering

### What This Project Is NOT
- ❌ Production SaaS product
- ❌ Trying to compete with Notion
- ❌ Perfect enterprise architecture (at first)
- ❌ Tutorial hell - I'm building, not following tutorials

---

## Contributing

This is a personal learning project, but:
- **Code reviews welcome** - please critique my architecture!
- **Suggestions appreciated** - especially on best practices
- **No PRs needed** - this is my learning journey
---

**Last Updated**: November 2025  
**Current Phase**: Month 1 - Backend Foundation  
**Next Milestone**: MVP v1.1
