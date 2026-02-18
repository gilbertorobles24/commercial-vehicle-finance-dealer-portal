# Commercial Vehicle Finance Dealer Portal

I designed a domain-driven commercial lending platform with event-driven workflow, secure dealer-bound data ownership, audit trails, and extensible microservice-ready architecture. I structured it to evolve toward Kafka-based event processing and CQRS.

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

## Getting Started

```bash
git clone https://github.com/YOUR_USERNAME/commercial-vehicle-finance-dealer-portal.git
cd commercial-vehicle-finance-dealer-portal
docker compose up -d
```

## Core Domain Story

**Dealer-Facing Commercial Vehicle Financing Workflow**

A dealer at a commercial truck/heavy equipment dealership needs to quickly finance customer purchases or leases. The core flow:

1. Dealer creates/submits a financing application with vehicle details, buyer info, deal structure (e.g., down payment, term, rate), and supporting docs.
2. Application progresses through states via events:
   - Draft → Submitted (dealer submits)
   - Submitted → Under Review (credit check triggered)
   - Under Review → Decision Made (Approved / Rejected / Pending Docs)
   - Approved → Funded (final docs signed, funds disbursed)
3. Kafka events publish every major state change (e.g., `ApplicationSubmitted`, `CreditDecisionMade`, `DocumentsRequested`, `ApplicationFunded`).
4. Events trigger:
   - Real-time status updates & notifications to dealer (frontend polling or WebSocket if extended)
   - Mock external calls (credit bureau, document verification)
   - Audit trail: All changes immutable via event log
5. Dealer sees real-time dashboard with application status, history, and actions (upload docs, respond to requests).

This CQRS-lite + event-driven design separates commands (write-side: submit, update docs) from queries (read-side: status views, materialized for speed).

## Epics / Key User Stories & Screens

These 5 high-level epics cover the MVP flows — focused on dealer experience, event triggers, and CQRS separation. Each could break into smaller user stories/tasks.

1. **Application Creation & Draft Management**  
   - As a dealer, I can create a new financing application in draft mode so I can enter vehicle/buyer details, deal terms, and save progress without submitting.  
   - As a dealer, I can view/edit my draft applications from a dashboard.  
   - Screen: "New Application" form (multi-step wizard: vehicle info, buyer profile, financing terms, attachments).  
   - Events: `ApplicationDraftCreated`, `ApplicationUpdated`.

2. **Application Submission & Initial Processing**  
   - As a dealer, I can submit a completed application so it enters the review pipeline and triggers credit checks.  
   - System auto-publishes `ApplicationSubmitted` event → mock credit bureau call + notification to dealer ("Submitted – Under Review").  
   - Screen: Application detail view with "Submit" button + confirmation modal.  
   - Events: `ApplicationSubmitted`, `CreditCheckInitiated` (mock).

3. **Real-Time Status Tracking & Notifications**  
   - As a dealer, I can see real-time status updates for all my applications so I know exactly where each stands (Draft, Under Review, Pending Docs, Approved, Funded, Rejected).  
   - As a dealer, I receive in-app notifications for key events (e.g., "Additional documents required", "Approved – Proceed to signing").  
   - Screen: Dealer Dashboard (list/table of applications with status badges, filters by state/date, clickable to details).  
   - Events drive read-model updates (materialized views for fast queries).

4. **Document Requests & Uploads During Review**  
   - As a dealer, I can upload requested documents (e.g., proof of income, title, insurance) in response to "Pending Docs" state.  
   - System triggers `DocumentsUploaded` event → advances to next review step or decision.  
   - Screen: Application detail page with document section (upload zone, list of required/received docs, status indicators).  
   - Events: `DocumentsRequested`, `DocumentsUploaded`, `ApplicationReSubmitted`.

5. **Decision & Funding Completion**  
   - As a dealer, I can view final decision (Approved/Rejected) and proceed to e-signing/funding steps if approved.  
   - On approval → `ApplicationApproved` event → mock funding process + `ApplicationFunded`.  
   - Full audit trail viewable (timeline of all events/changes).  
   - Screen: Application detail with decision summary, e-signature flow (mock), funding confirmation, and event history timeline.  
   - Events: `CreditDecisionMade` (with outcome), `ApplicationApproved`, `ApplicationRejected`, `ApplicationFunded`.

These epics highlight the **event-driven backbone** (Kafka publishes domain events → handlers update read models, trigger notifications) and make great talking points in interviews: bounded contexts, eventual consistency, real-time UX, auditability via events.

Future extensions: Multi-lender routing, automated decision engine, dealer performance analytics.