# Use Case Trace: Register

Updated: 2026-03-01

## Goal

User registers from UI and receives a JWT token.

## End-to-end path

1. UI trigger
   - `frontend/src/widgets/Navbar/ui/Navbar.tsx`
   - User clicks register button -> opens `RegisterModal`.
2. Register form render
   - `frontend/src/features/AuthByUsername/ui/RegisterModal/RegisterModal.tsx`
   - Renders `RegisterForm`.
3. Boundary gap on the frontend
   - `frontend/src/features/AuthByUsername/ui/RegisterForm/RegisterForm.tsx`
   - Inputs and button exist, but no submitted handler/use-case/API call yet.
4. Backend endpoint contract
   - `backend/src/main/java/com/aleksandrm/mynotions/controller/AuthController.java`
   - `POST /api/auth/register` with `RegisterRequest`.
5. Application logic
   - `backend/src/main/java/com/aleksandrm/mynotions/service/AuthService.java#register`
   - Create a user, generates JWT, logs event.
6. Persistence
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/UserRepository.java#save`
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/EventRepository.java#logEvent`
7. Response
   - `AuthResponse` returned with token and user fields.

[Diagram link](../diagrams/register-login-flow.png)

## Boundary violations/weaknesses

- Frontend feature boundary isn’t implemented (UI without application use-case).
- Auth service combines auth + audit logging + DTO mapping in one class.
- Error response contract is not unified, which complicates frontend integration once login call is wired.

## Target boundary shape (small-step direction)

- Frontend: `RegisterForm` -> `registerByUsername` use-case -> API client -> `userSlice`.
- Backend: `AuthService` orchestrates auth only; audit logging moves behind dedicated abstraction/event publisher.

## State ownership
- Users table in PostgreSQL: source of truth for identity/credentials.
- Events table in PostgreSQL: source of truth for auth audit logging.
- JWT token: auth state, issued by backend, stored client-side.
- Spring security context: request-scoped auth state, not persisted.

## Data flow
- User submits a register form with email and password.
- Frontend calls `registerByUsername` use-case with credentials.
- Use-case validates credentials, calls backend API.
- Backend checks email uniqueness, hashes password, saves a user, generates JWT, logs event.
- Response is mapped to `AuthResponse` and returned to frontend.
- Frontend stores token and user data in Redux store.

## Trade-offs
- JWT stateless auth: + horizontal scalability. — hard immediate token revocation.
- BCrypt hashing: + secure password storage. — extra CPU cost on AUTH.
- Spring Events for auth audit: + loose coupling in monolith. — no durable delivery guarantee on a crash.
- JDBC repositories: + explicit SQL control/learning. — more boilerplate/manual mapping.
