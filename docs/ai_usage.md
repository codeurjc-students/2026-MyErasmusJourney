# AI Usage

This document records the use of Artificial Intelligence throughout the development of this project.

All AI-generated outputs have been reviewed, understood and adapted before being incorporated into the project.

---

# Tool Information

| Property | Value |
|----------|-------|
| Primary Tool | ChatGPT / GitHub Copilot |
| Provider | OpenAI / GitHub |
| Models Used | GPT-5.5, Claude Haiku 4.5 |
| Usage mode | Conversational Chat / IDE Integration |
| Reasoning level | Standard |
| Agent mode | Enabled (IDE-integrated subagents) |
| IDE integration | VS Code GitHub Copilot |
| Plugins / Skills | create-agent, agent-customization |
| Subagents | Explore (codebase search & analysis) |
| MCP Servers | None |
| Context files | Repository structure, source code, build logs |

---

# Usage Log

## 2026-07-18

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Provide CSS styles for the About Us page and generate unit test suggestions for `User`, `UserDTO` and `UserFormDTO`.

### Tool

ChatGPT (assistant)

### Version

GPT-5.5

### Configuration

- Model: GPT-5.5
- Interaction mode: Conversational chat
- Reasoning: Standard
- Agentic mode: Disabled
- IDE integration: None
- Plugins/Skills: None

### How it was used

- Provided CSS snippets and structure for the About Us page including hero section, team grid, responsive breakpoints, utility classes and accessibility considerations.
- Provided example unit test code (JUnit 5 + Mockito) and test case descriptions for `User`, `UserDTO` and `UserFormDTO` focusing on mapping, validation and basic behaviour (getters/setters, equals/hashCode where applicable).

### Complements

None.

### Context Files

- Conversation design brief and existing frontend HTML/CSS structure (conversation context)

### AI-assisted Development Files

None (snippets and test examples provided; no files modified automatically).

### Files Affected

- Suggested integration targets (manual):
	- `frontend/src/styles/about.css`
	- `backend/src/test/java/com/myerasmusjourney/backend/unit/UserTest.java`
	- `backend/src/test/java/com/myerasmusjourney/backend/unit/UserDTOTest.java`
	- `backend/src/test/java/com/myerasmusjourney/backend/unit/UserFormDTOTest.java`

### Human Review

The CSS and test suggestions were reviewed by the user and are pending manual integration into the codebase.

---

- User-provided design brief and current Home page wireframe (conversation context)

### AI-assisted Development Files

None (snippets provided; no files were modified automatically).

### Files Affected

None (manual integration recommended: e.g., `frontend/src/styles/home.css`).

### Human Review

The CSS suggestions were reviewed by the user and accepted for manual integration.

---

## 2026-07-17

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Provide CSS styles for the About Us page, including layout, typography, responsive sections and team card presentation.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

- Model: GPT-5.5
- Interaction mode: Conversational chat
- Reasoning: Standard
- Agentic mode: Disabled
- IDE integration: None
- Plugins/Skills: None

### How it was used

- The assistant generated styles for the About Us page structure, including hero section layout, team member cards, responsive grid behaviour and utility spacing classes.
- The suggestions included color contrast guidance, mobile-first breakpoints, and accessible typography for page headings and body text.

### Complements

None.

### Context Files

- Conversation context describing the About Us page design requirements.

### AI-assisted Development Files

None.

### Files Affected

- `frontend/web/src/pages/AboutUs/AboutUs.css` (suggested target for manual CSS integration)

### Human Review

The user reviewed and accepted the About Us CSS suggestions for manual implementation.

---

## 2026-07-16

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Request initial set of styles for the Home page (layout, typography, color palette and base components).

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

- Model: GPT-5.5
- Interaction mode: Conversational chat
- Reasoning: Standard
- Agentic mode: Disabled
- IDE integration: None
- Plugins/Skills: None

### How it was used

- The assistant provided an initial CSS baseline for the Home page including grid/flex layout, typography scale, color variables, and base component styles (cards, buttons, header).
- The user reviewed the suggestions and requested further refinements (see 2026-07-17 entry).

### Complements

None.

### Context Files

- User-provided design brief (conversation context)

### AI-assisted Development Files

None.

### Files Affected

None (snippets provided; integration pending).

### Human Review

The initial styles were reviewed by the user; further refinements were requested and provided on 2026-07-17.

---

## 2026-07-12

### Phase

Phase 2 — Repository Setup, Testing and Continuous Integration

### Objective

Improve the technical documentation of the project by completing the *Development Guide*, refining the software architecture documentation and documenting the technologies and development tools used throughout the implementation.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

- Model: GPT-5.5
- Interaction mode: Conversational chat
- Reasoning: Standard
- Agentic mode: Disabled

### How it was used

The AI was used to review and improve the technical documentation created during Phase 2. Existing drafts were provided and the AI suggested improvements to their structure, wording and level of technical detail while preserving the original meaning.

The AI assisted in:

- Expanding the descriptions of the development tools used throughout the project.
- Completing the documentation of the main software technologies, including React, Spring Boot, Java, OpenAPI, Maven and Vite.
- Improving the explanation of the overall software architecture.
- Reviewing and refining the descriptions of the client and server architectures.
- Analysing the proposed architecture diagrams and suggesting improvements regarding abstraction level, UML notation and documentation structure.
- Recommending the separation of the architecture into a high-level component diagram and independent frontend and backend class diagrams.
- Reviewing the documentation for technical accuracy, readability and consistency.
- Improving the English wording of the documentation.

### Complements

None.

### Context Files

None.

### AI-assisted Development Files

No AI-specific context files (such as `CLAUDE.md`, Cursor Rules or Spec-Driven Development documents) were used during this task.

### Human Review

All generated content was manually reviewed, adapted and validated before being incorporated into the project documentation.

---

## 2026-07-11

### Phase

Phase 2 — Repository Setup, Testing and Continuous Integration

### Objective

Improve the technical documentation required for Phase 2 by reviewing the *Development Guide*, refining the description of the project technologies, and ensuring that the documentation follows the official TFG guidelines.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

- Model: GPT-5.5
- Interaction mode: Conversational chat
- Reasoning: Standard
- Agentic mode: Disabled

### How it was used

The AI was used as a documentation assistant to review and improve the project's technical documentation.

The AI:
- Reviewed the *Technologies* section of the Development Guide.
- Improved the technical descriptions of React, Spring Boot, Java, OpenAPI and Maven.
- Suggested including MySQL as an execution technology and distinguishing between execution technologies and development tools according to the TFG guidelines.
- Improved the wording and technical accuracy of the documentation.
- Ensured consistency with the writing style used throughout the rest of the project documentation.
- Suggested improvements to the overall organization of the documentation without changing the project's technical decisions.

### Complements

None.

### Context Files

None.

### AI-assisted Development Files

No AI-specific context files (such as `CLAUDE.md`, Cursor Rules or Spec-Driven Development documents) were used during this task.

### Files Affected

- `docs/development_guide.md`
- `docs/ai_usage.md`

### Human Review

All generated text was carefully reviewed, corrected where necessary, and adapted before being incorporated into the project documentation. The final technical descriptions and documentation structure remain the responsibility of the project author.

---

## 2026-07-10

### Phase
Phase 2 – Repository Setup, Testing and Continuous Integration

### Objective
Improve and complete the technical project documentation required for Phase 2, particularly the *Development Guide*. The objective was to transform an initial draft into a more complete and professional document aligned with the project's documentation guidelines.

### Tool
ChatGPT

### Version
GPT-5.5

### Configuration
- Model: GPT-5.5
- Interaction mode: Conversational chat
- Reasoning: Standard
- Agentic mode: Disabled

### How it was used
The existing documentation draft was provided to the AI. The AI reviewed the structure, identified missing technical information required by the project guidelines, and suggested improvements to the explanations and organization while preserving the original content and writing style.

The AI also:
- Improved the explanation of the SPA architecture.
- Expanded the description of the frontend, backend and database responsibilities.
- Added information about REST API communication and Spring Data JPA.
- Reorganized the technical overview table.
- Suggested a consistent visual style using icons and section separators.
- Reviewed the document for clarity, consistency and professional English.

### Complements
None.

### Context Files
None.

### AI-assisted Development Files
No AI-specific context files (such as `CLAUDE.md`, Cursor Rules or Spec-Driven Development documents) were used during this task.

### Human Review
All generated content was manually reviewed, adapted and validated before being incorporated into the project documentation.

---

## 2026-07-09

### Phase

Phase 2 — Continuous Integration

### Objective

Finalize automated quality control.

### Usage

- Reviewed backend CORS configuration.
- Solved CI execution issues.
- Reviewed test execution order.
- Validated successful execution of all automated tests.

### Technologies

- Spring Boot
- GitHub Actions

---

## 2026-07-08

### Phase

Phase 2 — Continuous Integration

### Objective

Configure GitHub Actions.

### Usage

- Designed the CI workflows.
- Distinguished between basic and complete quality controls.
- Configured workflow triggers.
- Reviewed Maven execution inside GitHub Actions.
- Solved CI failures.
- Reviewed branch protection strategy.

### Technologies

- GitHub Actions
- Maven
- pnpm

---

## 2026-07-05

### Phase

Phase 2 — System Testing

### Objective

Implement Selenium UI tests.

### Usage

- Investigated Selenium configuration.
- Solved Firefox execution problems.
- Configured frontend startup from Java.
- Investigated ProcessBuilder behaviour.
- Configured environment variables during test execution.
- Reviewed synchronization between backend and frontend.

### Technologies

- Selenium
- Firefox
- ProcessBuilder
- JUnit

---

## 2026-07-03

### Phase

Phase 2 — Frontend Development

### Objective

Generate frontend coverage reports.

### Usage

- Investigated coverage providers.
- Compared V8 and Istanbul coverage.
- Solved coverage problems caused by the Shared module.
- Designed the final testing commands.

### Technologies

- Vitest
- Istanbul

---

## 2026-07-01

### Phase

Phase 2 — Frontend Development

### Objective

Configure frontend automated tests.

### Usage

- Configured Vitest.
- Reviewed Testing Library usage.
- Designed service mocking.
- Configured frontend integration tests.

### Technologies

- Vitest
- Testing Library

---

## 2026-06-29

### Phase

Phase 2 — Frontend Development

### Objective

Configure environment variables.

### Usage

- Investigated React environment variables.
- Configured Vite environment files.
- Designed API URL injection.
- Avoided hardcoded backend URLs.

### Technologies

- React
- Vite

---

## 2026-06-27

### Phase

Phase 2 — Frontend Development

### Objective

Design the frontend architecture.

### Usage

- Discussed frontend folder structure.
- Compared different service organization strategies.
- Designed the Shared module.
- Reviewed dependency encapsulation.
- Discussed service instances versus independent functions.

### Technologies

- React
- TypeScript
- Vite

---

## 2026-06-25

### Phase

Phase 2 — Backend Development

### Objective

Generate OpenAPI documentation.

### Usage

- Investigated SpringDoc configuration.
- Solved OpenAPI generation problems.
- Fixed compatibility issues.
- Generated OpenAPI documentation.

### Technologies

- SpringDoc OpenAPI

---

## 2026-06-23

### Phase

Phase 2 — Backend Development

### Objective

Configure Testcontainers.

### Usage

- Investigated Testcontainers integration with Spring Boot.
- Solved DataSource configuration problems.
- Configured MySQL Testcontainers.
- Reviewed test lifecycle.
- Improved integration test isolation.

### Technologies

- Testcontainers
- Spring Boot
- MySQL

---

## 2026-06-21

### Phase

Phase 2 — Backend Development

### Objective

Implement automated backend tests.

### Usage

- Discussed JUnit testing strategy.
- Reviewed repository mocking.
- Configured service unit tests.
- Analysed JaCoCo warnings.
- Reviewed integration testing strategy.

### Technologies

- Spring Boot
- JUnit
- Mockito
- JaCoCo

---

## 2026-06-17

### Phase
Phase 1 — Analysis

### Objective

Complete the project analysis and documentation.

### Usage

- Designed the navigation flow between pages.
- Reviewed wireframes.
- Discussed similar existing applications for the State of the Art.
- Defined the complementary technology section.
- Designed the destination ranking algorithm.
- Designed the trending cities algorithm.
- Created CHANGELOG and AI usage documentation.

### Files

- docs/web-interface.md
- docs/state-of-the-art.md
- docs/advanced-algorithms.md
- CHANGELOG.md
- AI_USAGE.md

---

## 2026-06-16

### Phase
Phase 1 — Functional Analysis

### Objective
Define the project scope and the initial functional requirements.

### Usage

- Brainstormed possible functionalities for the application.
- Classified functionalities into Basic, Intermediate and Advanced.
- Reviewed user roles and permissions.
- Discussed possible advanced algorithms.
- Reviewed entity relationships.
- Generated and improved project documentation in Markdown.
- Improved English wording of the documentation.

### Files

- README.md
- docs/objectives.md
- docs/functionalities.md
- docs/entities.md
- docs/user-permits.md

---

# Context Provided to the AI

During the conversations, the following contextual information was shared with the AI:

- Source code fragments.
- Build logs.
- Stack traces.
- GitHub Actions logs.
- Test execution outputs.
- Maven outputs.
- Wireframes.
- Documentation drafts.
- Project architecture.
- Repository structure.

No external project context files (CLAUDE.md, AGENTS.md or similar) have been used.

---

# Author Responsibility

Artificial Intelligence has been used as a support tool during the development of this project.

Every suggestion has been analysed and validated before being incorporated into the project.

The final implementation, architectural decisions and documentation remain the sole responsibility of the author.

---
🏠 [Home](../README.md) | 📚 Documentation
---