# Use Case Trace: Create Workspace

Updated: 2026-03-01

## Goal

User creates a workspace from UI.

## End-to-end path

1. UI trigger
   - `frontend/src/widgets/Navbar/ui/Navbar.tsx`
   - User clicks create workspace button -> opens `CreateWorkspaceModal`.
2. Create a workspace form render
   - `frontend/src/features/Workspace/ui/CreateWorkspaceModal/CreateWorkspaceModal.tsx`
   - Renders `CreateWorkspaceForm`.
3. Boundary gap on the frontend
   - `frontend/src/features/Workspace/ui/CreateWorkspaceModal/CreateWorkspaceModal.tsx`
   - Inputs and button exist, but no submitted handler/use-case/API call yet.
4. Backend endpoint contract
   - `backend/src/main/java/com/aleksandrm/mynotions/controller/WorkspaceController.java`
   - `POST /api/workspaces` with `CreateWorkspaceRequest`.
5. Application logic
   - `backend/src/main/java/com/aleksandrm/mynotions/service/WorkspaceService.java#createWorkspace`
   - Create a workspace, validate owner/context, save the workspace, publish/log WORKSPACE_CREATED.
6. Persistence
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/WorkspaceRepository.java#save`
   - `backend/src/main/java/com/aleksandrm/mynotions/repository/EventRepository.java#logEvent`
7. Response
   - `WorkspaceResponse` returned workspace fields.

[Diagram link](../diagrams/create-workspace-flow.png)

## Boundary violations/weaknesses

- Frontend feature boundary isn’t implemented (UI without application use-case).
- Error response contract is not unified, which complicates frontend integration once create-workspace API is wired.
- Sync event persistence in same transaction: + consistency, - higher latency/coupling.
- Rollback on event failure: + no missing audit events, - create request can fail due to event path.

## Target boundary shape (small-step direction)

- Frontend: `CreateWorkspaceForm` -> `createWorkspace` use-case -> API client -> `workspaceSlice`.
- Backend: `WorkspaceService` orchestrates workspace creation only; audit logging moves behind dedicated abstraction/event publisher.

## State ownership
- Workspaces table in PostgreSQL: source of truth for workspace state (name, ownerId, timestamps...).
- Events table in PostgreSQL: source of truth for workspace audit/event log.

## Data flow
- User submits a create workspace form with name and description.
- Frontend calls `createWorkspace` use-case with workspace details.
- Frontend basic validation, backend authoritative validation calls backend API.
- Backend creates a workspace, validates owner/context, saves the workspace, publishes/log WORKSPACE_CREATED.
- Response is mapped to `WorkspaceResponse` and returned to frontend.

## Trade-offs
- Synchronous event handling in request transaction: + consistency, - latency/coupling.
- Rollback on event persist failure: + no missing audit event, – create may fail.

## Invariants
- Workspace creation succeeds only if the WORKSPACE_CREATED event is persisted.
