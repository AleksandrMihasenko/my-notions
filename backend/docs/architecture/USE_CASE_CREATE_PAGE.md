# Use Case Trace: Create Page

Updated: 2026-03-08

## Goal

User creates a page from UI.

## End-to-end path

1. UI trigger
   - `frontend/src/widgets/Navbar/ui/Navbar.tsx`
   - User clicks create page button -> opens `CreatePageModal`.
2. Create a page form render
   - `frontend/src/features/Page/ui/CreatePageModal/CreatePageModal.tsx`
   - Renders `CreatePageForm`.
3. Boundary gap on the frontend
   - `frontend/src/features/Page/ui/CreatePageModal/CreatePageModal.tsx`
   - Inputs and button exist, but no submitted handler/use-case/API call yet.
4. Backend endpoint contract
   - `backend/src/main/java/com/aleksandrm/mynotions/controller/PageController.java`
   - `POST /api/workspaces/{workspaceId}/pages` with `CreatePageRequest`.
5. Application logic
   - `backend/src/main/java/com/aleksandrm/mynotions/service/PageService.java#create`
   - Create a page, validate owner/context, save the page, publish/log PAGE_CREATED.
6. Persistence
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/PageRepository.java#save`
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/EventRepository.java#logEvent`
7. Response
   - `PageResponse` returned page fields.

[Diagram link](../diagrams/create-page-flow.png)

## Boundary violations/weaknesses

- Frontend feature boundary isn’t implemented (UI without application use-case).
- Error response contract is not unified, which complicates frontend integration once create-page API is wired.
- Sync event persistence in the same transaction: + consistency, - higher latency/coupling.
- Rollback on event failure: + no missing audit events, - create request can fail due to an event path.

## Target boundary shape (small-step direction)

- Frontend: `CreatePageForm` -> `createPage` use-case -> API client -> `pageSlice`.
- Backend: `PageService` orchestrates page creation only; audit logging moves behind dedicated abstraction/event publisher.

## State ownership
- Pages table in PostgreSQL: source of truth for page state (workspaceId, title, content, createdBy, timestamps).
- Events table in PostgreSQL: source of truth for page audit/event log.

## Data flow
- User submits a creation page form with name and description.
- Frontend calls `createPage` use-case with page details.
- Frontend basic validation, backend authoritative validation calls backend API.
- Backend creates a page, validates owner/context, saves the page, publishes/log PAGE_CREATED.
- Response is mapped to `PageResponse` and returned to frontend.

## Trade-offs
- Synchronous event handling in request transaction: + consistency, - latency/coupling.
- Rollback on event persist failure: + no missing audit event, – create may fail.

## Invariants
- Page creation succeeds only if the PAGE_CREATED event is persisted.
