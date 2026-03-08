# Use Case Trace: Get events

Updated: 2026-03-07

## Goal
   - current scope: an authenticated user reads their own events.
   - future scope: admin/system-wide audit (separate note).

## End-to-end path

1. UI trigger (without implementation yet)
2. Backend endpoint contract
   - `backend/src/main/java/com/aleksandrm/mynotions/controller/EventsController.java`
   - `GET /api/events` with query-param contract wording (type, entityType, entityId, from, to, page, size).
3. Application logic
   - `backend/src/main/java/com/aleksandrm/mynotions/service/EventsService.java#getEvents`
   - Fetch events from a repository.
4. Persistence
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/EventRepository.java#findEvents`
   - Fetch events from a database.
5. Response
   - `EventResponse` returned with an events list.

[Diagram link](../diagrams/get-events-of-the-system.png)

## Boundary violations/weaknesses
   - Metadata filtering is weak if the schema is inconsistent with the use-case.
   - MVP implementation exists, but integration tests for `/api/events` are still missing.
   - Current pagination uses `OFFSET`; for growth this may need keyset/cursor pagination.

## Target boundary shape (small-step direction)
   - controller only parses params
   - service validates filter rules/defaults
   - repository builds SQL and maps DTO

## State ownership
   - Events table in PostgreSQL: source of truth for events.
   - Request state: filter params.

## Data flow
   - Request params.
   - Filter object.
   - Repository query.
   - Page response.

## Trade-offs
   - Flexible JSONB metadata vs strict typed columns.
   - JSONB filtering is temporary; see ADR-003 (backend/docs/ADR/ADR-003-use-jsonb-filtering-for-current-project-state.md).

## Invariants:
   - Events are immutable.

## Filter contract (MVP)
For entity-related events, `metadata` must contain:
- `entityType` (string, e.g. `WORKSPACE`, `PAGE`)
- `entityId` (number, positive long)

## Next step
- Planned migration: typed columns (entity_type, entity_id) + index strategy (ADR-003).
