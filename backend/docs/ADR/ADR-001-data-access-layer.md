# ADR-001: Data Access Layer — JDBC vs JPA for Repositories

**Date:** February 2026                                                                                                                                                       
**Status:** Decided

**Context:** Need data access layer for CRUD operations  

**Options:**
1. Spring Data JPA — less code, magic
2. JDBC Template — more control, explicit SQL
3. jOOQ — type-safe SQL  

**Decision:** JDBC Template

**Reasons:**
- Learning SQL deeply (DE goal)
- More control over queries
- Better understanding of what happens

**Consequences:**
- More boilerplate code
- Manual mapping (RowMapper)
- Will evaluate JPA in Month 3
