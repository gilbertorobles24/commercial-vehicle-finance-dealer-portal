# Commercial Vehicle Finance Dealer Portal

Dealer-facing prototype for commercial vehicle financing — built with modern event-driven microservices.

## Tech Stack (CQRS-lite + Event Sourcing influences)

**Backend**
- Java 21 / Spring Boot 3.x
- Spring Web / Spring Data JPA / Spring Security
- Apache Kafka (event bus) + Schema Registry (if using Avro)
- PostgreSQL (write side) + MongoDB / Elasticsearch (read models — depending on CQRS depth)
- Docker + Compose for local dev

**Frontend**
- Nuxt 3 (SSR + Vue 3 Composition API)
- PrimeVue + PrimeIcons
- Pinia / VueUse
- Tailwind CSS or UnoCSS

**Architecture highlights**
- Microservices (bounded contexts: dealer, credit, contracts, documents…)
- Event-driven communication via Kafka
- CQRS-lite (separate command & query paths, materialized views)
- Hexagonal / Clean Architecture in services
- OpenAPI docs + SpringDoc
- GitHub Actions CI/CD (build, test, lint, Docker build)

## Project Structure (monorepo — recommended for prototype)

.
├── backend/
│   ├── dealer-service/
│   ├── credit-decision-service/
│   ├── contract-service/
│   └── shared/               # shared libraries, events, DTOs
├── frontend/                 # Nuxt 3 app
├── infra/                    # docker-compose, k8s manifests, helm (later)
├── docs/                     # ADR, architecture diagrams
└── .github/workflows/        # CI/CD
