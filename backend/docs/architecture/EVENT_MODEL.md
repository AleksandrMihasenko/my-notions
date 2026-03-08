# Event Model

Updated: 2026-03-08

## Purpose
- Define the current event model used in the monolith.
- Keep one canonical list of events (name, meaning, trigger point).
- Document JSONB payload contracts and schema versioning rule.
- Clarify terminology: domain event vs application event vs audit log row.

## Event Transport Decision
- Transport/mechanism decision is documented in ADR-002:
  - `backend/docs/ADR/ADR-002-spring-events-vs-kafka.md`
- Current state:
  - Spring `ApplicationEventPublisher` publishes in-process events.
  - `@EventListener` persists events into `events` table via `EventRepository`.

## Terminology
- `Domain event`:
  - Business fact that happened in domain language.
  - Example: `WORKSPACE_UPDATED`, `PAGE_DELETED`.
- `Application event`:
  - Technical in-process message used to react to actions inside the app.
  - In this project, Java records like `WorkspaceUpdatedEvent` / `PageDeletedEvent`.
- `Audit log row`:
  - Persisted record in `events` table (`event_type`, `user_id`, `created_at`, `metadata` JSONB).
  - Immutable historical trace used for `/api/events`.

Current mapping:
- One application event is emitted from service layer.
- Listener persists one audit log row in `events`.
- Event type string (`event_type`) is the canonical external event name.

## Event Catalog

| Event Type | Meaning | Fired When | Publisher (service) |
|---|---|---|---|
| `USER_REGISTERED` | User account successfully created | After user save in register flow | `AuthService#register` |
| `USER_LOGGED` | User successfully authenticated | After credentials validated and token generated | `AuthService#login` |
| `WORKSPACE_CREATED` | Workspace created | After workspace insert succeeds | `WorkspaceService#create` |
| `WORKSPACE_UPDATED` | Workspace updated | After workspace update succeeds | `WorkspaceService#update` |
| `WORKSPACE_DELETED` | Workspace deleted | After workspace delete succeeds | `WorkspaceService#delete` |
| `PAGE_CREATED` | Page created | After page insert succeeds | `PageService#create` |
| `PAGE_UPDATED` | Page updated | After page update succeeds | `PageService#update` |
| `PAGE_DELETED` | Page soft-deleted | After page soft-delete succeeds | `PageService#delete` |

## Payload Schemas (JSONB metadata)

Notes:
- Stored in `events.metadata` (JSONB).
- Filter contract for `/api/events` depends on:
  - `entityType` (string)
  - `entityId` (number)
- JSONB strategy is temporary and documented in ADR-003.

### Auth events
- `USER_REGISTERED`
- `USER_LOGGED`

```json
{
  "schemaVersion": 1
}
```

Current implementation stores `{}` for auth events. `schemaVersion` is the target contract for next touch.

### Workspace events
- `WORKSPACE_CREATED`
- `WORKSPACE_UPDATED`
- `WORKSPACE_DELETED`

```json
{
  "schemaVersion": 1,
  "workspaceId": 123,
  "name": "Personal",
  "entityType": "WORKSPACE",
  "entityId": 123
}
```

### Page events
- `PAGE_CREATED`
- `PAGE_UPDATED`
- `PAGE_DELETED`

```json
{
  "schemaVersion": 1,
  "pageId": 456,
  "workspaceId": 123,
  "title": "Notes",
  "entityType": "PAGE",
  "entityId": 456
}
```

## Versioning Rule
- Metadata version field: `schemaVersion` (integer).
- Rules:
  - Additive-only changes within same version.
  - Breaking changes require incrementing `schemaVersion`.
  - Consumers (`/api/events` filtering) must rely only on stable keys:
    - `entityType`
    - `entityId`

## Delivery and Consistency Semantics
- Current mode: synchronous listener handling in request transaction.
- If event persistence fails:
  - request fails (`500`)
  - domain mutation is rolled back
- This is intentional for current learning phase to guarantee "no domain change without audit row".

## Known Gaps / Next Step
- `/api/events` integration tests for filter/pagination behavior.
- Move filter-critical keys from JSONB to typed columns (`entity_type`, `entity_id`) when ADR-003 triggers are met.
- Evaluate keyset pagination for large event history.
