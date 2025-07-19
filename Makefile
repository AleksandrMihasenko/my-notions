# Makefile for My-notion project
# Supports both local development and production deployment

COMPOSE_LOCAL = deploy/docker-compose.yml
COMPOSE_PROD = deploy/docker-compose.prod.yml
DOCKERHUB_USER = yourdockerhub  # <-- TODO: add account details

## ---------- 🧪 LOCAL DEVELOPMENT ----------
dev: ## Start full dev environment (MySQL + Backend + Frontend)
	make dev-db &
	make dev-backend &
	make dev-frontend

dev-db: ## Start MySQL only (for local development)
	docker-compose -f $(COMPOSE_LOCAL) up -d mysql

dev-frontend: ## Start Vite dev server (frontend with hot reload)
	cd frontend && npm install && npm run dev

dev-backend: ## Start Spring Boot backend with hot reload (local db, env vars passed inline)
	cd backend && \
	SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/my_notions_network \
	SPRING_DATASOURCE_USERNAME=username \
	SPRING_DATASOURCE_PASSWORD=password \
	./mvnw spring-boot:run

dev-down: ## Stop local dev containers (MySQL)
	docker-compose -f $(COMPOSE_LOCAL) down

dev-logs: ## View MySQL logs
	docker-compose -f $(COMPOSE_LOCAL) logs -f mysql

## ---------- 🚀 PRODUCTION ----------
up-prod: ## Start containers from prebuilt images (production)
	docker-compose -f $(COMPOSE_PROD) up -d

down-prod: ## Stop production containers
	docker-compose -f $(COMPOSE_PROD) down

logs-prod: ## Logs for production services
	docker-compose -f $(COMPOSE_PROD) logs -f

## ---------- 🏗️ BUILD & PUSH ----------
build-push: ## Build and push Docker images (edit your dockerhub repo!)
	docker build -t yourdockerhub/notion-backend:latest -f deploy/backend/Dockerfile .
	docker build -t yourdockerhub/notion-frontend:latest -f deploy/frontend/Dockerfile .
	docker build -t yourdockerhub/notion-mailer:latest -f deploy/mailer/Dockerfile .
	docker push yourdockerhub/notion-backend:latest
	docker push yourdockerhub/notion-frontend:latest
	docker push yourdockerhub/notion-mailer:latest

## ---------- Utilities ----------
help: ## Show all available commands
	@echo ""
	@echo "Available commands:"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-20s\033[0m %s\n", $$1, $$2}'
	@echo ""
