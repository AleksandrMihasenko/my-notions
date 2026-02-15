# Use Case Trace: Login

Updated: 2026-02-15

## Goal

User logs in from UI and receives JWT token.

## End-to-end path

1. UI trigger
   - `frontend/src/widgets/Navbar/ui/Navbar.tsx`
   - User clicks login button -> opens `LoginModal`.
2. Login form render
   - `frontend/src/features/AuthByUsername/ui/LoginModal/LoginModal.tsx`
   - Renders `LoginForm`.
3. Boundary gap on frontend
   - `frontend/src/features/AuthByUsername/ui/LoginForm/LoginForm.tsx`
   - Inputs and button exist, but no submit handler/use-case/API call yet.
4. Backend endpoint contract
   - `backend/src/main/java/com/aleksandrm/mynotions/controller/AuthController.java`
   - `POST /api/auth/login` with `LoginRequest`.
5. Application logic
   - `backend/src/main/java/com/aleksandrm/mynotions/service/AuthService.java#login`
   - Finds user, verifies password, generates JWT, logs event.
6. Persistence
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/UserRepository.java#getUserByEmail`
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/EventRepository.java#logEvent`
7. Response
   - `AuthResponse` returned with token and user fields.

## Boundary violations / weaknesses

- Frontend feature boundary not implemented (UI without application use-case).
- Auth service combines auth + audit logging + DTO mapping in one class.
- Error response contract is not unified, which complicates frontend integration once login call is wired.

## Target boundary shape (small-step direction)

- Frontend: `LoginForm` -> `loginByUsername` use-case -> API client -> `userSlice`.
- Backend: `AuthService` orchestrates auth only; audit logging moves behind dedicated abstraction/event publisher.
