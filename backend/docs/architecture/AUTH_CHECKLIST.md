# Auth Checklist
Updated: 2026-03-01
Purpose: Pre-implementation and code review guardrails for auth features.

## 1) Input validation
- Request DTO validation exists (`@NotBlank`, format/size constraints).
- Validation errors follow unified API error contract.

## 2) Credential handling
- Register hashes password before persistence.
- Login verifies password via hash comparison only.
- Password is never logged or returned in responses.

## 3) Identity rules
- Email uniqueness is enforced at DB level and handled in service/API layer.
- Duplicate email returns predictable business error response.

## 4) Token contract
- JWT claims and expiration are explicitly defined.
- Protected endpoints require valid JWT.
- Invalid/expired token behavior is consistent and tested.
- Bearer <token> is required and validated consistently.

## 5) Error contract
- Auth endpoints return one error shape (`code`, `message`, optional `fieldErrors`).
- 4xx vs. 5xx mapping is explicit and stable.

## 6) Audit/events
- Register/login emits or persists auth events with required metadata.
- Event failure behavior is explicit (best effort or request-failing).

## 7) State ownership
- Source of truth: PostgreSQL (`users`, `events`).
- Derived state: JWT token.
- Request-scoped state: Spring Security context (not persisted).

## 8) Tests
- Register happy path.
- Register duplicate email.
- Login happy path.
- Login wrong password.
- Login unknown user.
- Protected endpoint with missing/invalid/expired token.