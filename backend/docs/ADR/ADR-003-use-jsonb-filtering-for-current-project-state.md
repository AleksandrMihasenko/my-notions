# ADR-003: Use JSONB filtering for the current project state

**Date:** 08 March 2026


**Context:**
Need to use simple JSONB filtering for the current project state as it minimizes implementation complexity for MVP delivery.
I know the current limits of this decision (query complexity, indexing risk). Furthermore, it will be changed and refactored in the future.

**Decision:** Keep JSONB metadata filtering for MVP (/api/events) and defer typed filter columns to a later migration.

**Filter Contract (MVP):**
For entity-related events, `metadata` must contain:
- `entityType` (string, e.g. `WORKSPACE`, `PAGE`)
- `entityId` (number, positive long)
If these keys are missing/invalid, `/api/events` filtering by `entityType`/`entityId` is not guaranteed.

**Rationale:**
- Early-stage project — MVP for delivery speed

**Trade-offs:**
- Performance risk: those JSON expressions are harder to index well for your common filters.
- Query complexity grows: each new filter adds JSON extraction/casts.

**Revisit Trigger:**
Move to typed columns (`entity_type`, `entity_id`) when one of these happens:
- `/api/events` is used by UI screens as a core feature
- query performance degrades under realistic data volume
- filter requirements expand beyond current keys
- offset pagination becomes a bottleneck

**Target Migration Direction (next step, not now):**
- Add typed columns: `entity_type`, `entity_id` (nullable for non-entity events)
- Add indexes for common filters/sort:
    - `(user_id, created_at DESC)`
    - `(user_id, event_type, created_at DESC)`
    - `(user_id, entity_type, entity_id, created_at DESC)`
- Keep JSONB `metadata` for flexible extra context

**Status:** Temporary (Accepted for MVP)

**Review date:** 31 March 2026
