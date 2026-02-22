# ADR-002: Spring Events vs Kafka for Domain Events

**Date:** 22 February 2026                                                                                                                                                       


**Context:** 
Need to use Spring Events or Kafka for Domain Events for audit log use cases.
Also, I want to explore both options and compare them. And want to decouple events from the domain.

**Decision:** Spring Events

**Rationale:**
- Monolith architecture — no need for inter-process messaging
- Zero infrastructure overhead — runs within the same JVM
- Early-stage project — complexity of Kafka is not justified yet
- Audit log use case does not require guaranteed delivery guarantees
- Kafka will be reconsidered in the exploration phase (Month 3–4)

**Trade-offs:**
- Spring Events don't have guarantees of delivery if the application or JVM crashes
- Kafka has guarantees of delivery, but complexity and overhead

**Status:** Decided

