# High-Level Architecture Map

Updated: 2026-02-15

## Backend (`backend/src/main/java/com/aleksandrm/mynotions`)

- `controller/`
  - Responsibility: HTTP endpoints, request validation, response status codes.
  - Files: `AuthController`, `WorkspaceController`, `PageController`.
- `service/`
  - Responsibility: use-case orchestration, business checks, DTO mapping.
  - Files: `AuthService`, `WorkspaceService`, `PageService`, `PasswordService`.
- `repository/`
  - Responsibility: SQL and persistence mapping via `JdbcTemplate`.
  - Files: `UserRepository`, `WorkspaceRepository`, `PageRepository`, `EventRepository`.
- `model/`
  - Responsibility: persistence/domain data objects.
  - Files: `User`, `Workspace`, `Page`, `Event`.
- `dto/`
  - Responsibility: API contracts for request/response.
- `security/` + `config/SecurityConfig`
  - Responsibility: JWT auth filter, principal extraction, endpoint protection.
- `exception/GlobalExceptionHandler`
  - Responsibility: HTTP mapping for validation/business/runtime exceptions.
- `resources/db/migration`
  - Responsibility: schema evolution (Flyway).

## Frontend (`frontend/src`)

- `app/`
  - Responsibility: application bootstrap, providers (router/store/theme/error boundary), global styles.
- `shared/`
  - Responsibility: reusable UI primitives, utilities, route config, i18n config.
- `widgets/`
  - Responsibility: page-level composed UI blocks (`Navbar`, `Sidebar`, `ThemeSwitcher`).
- `features/AuthByUsername/`
  - Responsibility: login modal/form UI (currently mostly presentational).
- `entities/User/`
  - Responsibility: user domain state types and slice.
- `pages/`
  - Responsibility: route targets (`MainPage`, `NoteListPage`, `NotFoundPage`, `ErrorPage`).

## Current main runtime flows

1. Auth
   - `POST /api/auth/register` and `POST /api/auth/login` -> `AuthController` -> `AuthService` -> `UserRepository` (+ `EventRepository`) -> `AuthResponse`.
2. Workspace CRUD
   - `/api/workspaces/**` -> `WorkspaceController` -> `WorkspaceService` -> `WorkspaceRepository`.
3. Page CRUD
   - `/api/workspaces/{id}/pages`, `/api/pages/{id}` -> `PageController` -> `PageService` -> `PageRepository`.
