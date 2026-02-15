# Refactor Backlog (Small Steps)

Updated: 2026-02-15

Rule: each task should be executable in less than one working day.

| ID | Task | Scope | Acceptance Criteria | Est. |
|---|---|---|---|---|
| AR-01 | Extract `CurrentUserProvider` boundary | Backend services (`WorkspaceService`, `PageService`) | Services no longer call `SecurityContextHolder` directly; tests can mock current user | 0.5 day |
| AR-02 | Unify API error contract | `GlobalExceptionHandler` + new `ErrorResponse` DTO | All error responses use one shape; catch-all logs stack trace; integration tests updated | 0.5 day |
| AR-03 | Create frontend login use-case seam | `features/AuthByUsername` + `entities/User` | Login form submit calls backend; success updates user state; failure renders error | 1 day |

## Suggested execution order

1. AR-01 (service boundary cleanup)
2. AR-02 (API contract stabilization)
3. AR-03 (connect UI to API with clearer feature boundary)

## Out of scope (for now)

- Migrating JDBC repositories to JPA.
- Large package restructuring.
- Full clean architecture rewrite.
