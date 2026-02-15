# Architecture Docs

Updated: 2026-02-15

Purpose:
- Keep architecture understanding explicit and versioned.
- Track technical debt as small, executable steps.
- Document important design decisions and use-case boundaries.

How to use:
1. Start with `ARCHITECTURE_MAP.md` to understand system shape.
2. Review `HOTSPOTS.md` to pick one boundary/smell to improve.
3. Execute one item from `REFACTOR_BACKLOG.md`.
4. Verify with tests from `TEST_PLAN.md`.
5. If design direction changes, add/update an ADR in `backend/docs/ADR/`.

Current scope:
- Backend: Spring Boot + JDBC + JWT auth + CRUD (workspaces/pages).
- Frontend: React app shell + auth UI skeleton + routing/pages.
