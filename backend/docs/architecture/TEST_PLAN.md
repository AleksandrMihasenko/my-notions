# Test Plan for Architecture Refactors

Updated: 2026-02-15

Goal: improve boundaries without behavior change.

## Baseline suites (must stay green)

- `backend/src/test/java/com/aleksandrm/mynotions/controller/AuthControllerIntegrationTest.java`
- `backend/src/test/java/com/aleksandrm/mynotions/controller/WorkspaceControllerIntegrationTest.java`
- `backend/src/test/java/com/aleksandrm/mynotions/controller/PageControllerIntegrationTest.java`
- Repository integration tests under `backend/src/test/java/com/aleksandrm/mynotions/repository/`

## Tests to add for AR-01 (`CurrentUserProvider`)

- Unit tests for `WorkspaceService` with mocked current user provider:
  - list returns only owner workspaces.
  - get/update/delete non-owned or missing workspace -> not found.
- Unit tests for `PageService` with mocked current user provider:
  - create/update/delete follows owner boundary.

## Tests to add for AR-02 (unified error contract)

- Validation error shape test (400): contains code/message/fieldErrors.
- Business error shape tests (401/404/409): same top-level DTO fields.
- Runtime exception shape test (500) + verify logging path.

## Tests to add for AR-03 (frontend login seam)

- `LoginForm` submit test:
  - dispatches login use-case with email/password.
- Login success test:
  - user state is updated with auth data/token handling path.
- Login failure test:
  - error message state is rendered and no auth state set.

## Non-functional checks

- No endpoint path/status regressions for existing CRUD/auth APIs.
- Existing JSON fields used by current integration tests remain compatible (or tests updated intentionally with new contract).
