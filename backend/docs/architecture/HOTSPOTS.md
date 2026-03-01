# Architecture Hotspots

Updated: 2026-02-15

| Path                                                                                   | Issue                                                                                  | Impact                                                              | Priority | Status |
|----------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|---------------------------------------------------------------------|----------|--------|
| `backend/src/main/java/com/aleksandrm/mynotions/service/WorkspaceService.java`         | Service directly reads `SecurityContextHolder` and casts principal                     | Application logic coupled to framework context; harder unit testing | High     | Open   |
| `backend/src/main/java/com/aleksandrm/mynotions/service/PageService.java`              | Same direct security-context dependency pattern                                        | Repeated coupling and duplicated auth extraction logic              | High     | Open   |
| `backend/src/main/java/com/aleksandrm/mynotions/service/AuthService.java`              | Mixed responsibilities: credentials, token generation, event logging, response mapping | Lower cohesion, harder evolution of auth/audit boundaries           | High     | Open   |
| `backend/src/main/java/com/aleksandrm/mynotions/exception/GlobalExceptionHandler.java` | Inconsistent error shape (`Map` vs `String`) + catch-all without logging               | Client parsing complexity and poor observability                    | Medium   | Open   |
| `frontend/src/features/AuthByUsername/ui/LoginForm/LoginForm.tsx`                      | UI-only login (no submit/use-case/API boundary)                                        | Feature boundary is incomplete; architecture intent not executable  | High     | Open   |

## Notes

- Repositories intentionally use JDBC (ADR-001), which is fine for learning SQL.
- The immediate boundary improvements should focus on application/service seams, not switching persistence technology.
