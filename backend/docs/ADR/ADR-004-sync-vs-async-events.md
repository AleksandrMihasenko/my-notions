# ADR-004: Sync vs Async events

**Date:** 08 March 2026

**Context:**
Current behavior: Spring @EventListener handlers run synchronously in the request transaction, and event persistence failure causes request failure (500) with rollback of domain
mutation.

**Decision:** Keep synchronous transactional listeners for Auth/Workspace/Page events.

**Rationale:**
- Simple CRUD and Audit log functionality
- Asynchronous will be reconsidered in the next phase (Month 3–4)

**Trade-offs:**
- Need higher availability and UI.
- Integrations require more work. and non-blocking publishes.
- No domain change without an audit row
- Request latency/coupling
- Client can get 500 if event logging fails

**Status:** Temporary (Accepted for MVP)

**Review date:** 30 April 2026

**Revisit triggers:**
- Performance degradation during a high load
- Increased complexity in handling failures
- Need for better monitoring and alerting

**Next Steps:**
- Evaluate asynchronous event handling for performance and scalability.
- Implement retry mechanisms for failed event deliveries.
- Consider using message queues for decoupling and improved fault tolerance.
