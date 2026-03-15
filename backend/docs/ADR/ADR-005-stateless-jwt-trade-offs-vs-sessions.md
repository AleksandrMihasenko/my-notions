# ADR-005 Stateless JWT trade-offs vs sessions

**Date:** 15 March 2026

**Context:**
Need an authentication mechanism for REST API. There are two main options:
1. Session-based — server stores session state, client gets session ID cookie.
2. Stateless JWT — server signs token, client stores and sends it each request.

**Decision:** Stateless JWT with short expiry.

**Rationale:**
- REST API is stateless by design — JWT aligns naturally
- No server-side session storage needed (simpler backend logic)
- Easy to use from any client (mobile, SPA, CLI)
- Good learning exercise for token-based auth

**Trade-offs:**
- No built-in token revocation (token blacklist, short expiry, refresh token rotation)
- Token grows with claims — larger than the session cookie
- Secret key management is critical
- Sessions easier to invalidate (delete server-side)

**Consequences**:
- Short expiry times are recommended to limit revocation risk
- Refresh token pattern needed, for better UX (future)
- JWT secret must be secured in production (not in source code)

**Status:** Decided
