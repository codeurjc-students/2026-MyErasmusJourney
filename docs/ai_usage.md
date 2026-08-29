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
| Plugins / Skills | create-agent, agent-customization |89-detailed-experience-page
| Subagents | Explore (codebase search & analysis) |
| MCP Servers | None |
| Context files | Repository structure, source code, build logs |

---

# Usage Log

## 2026-08-28

### Phase

Phase 3 — Basic Functionality

### Objective

Continue the development and testing of the application, focusing on the implementation and validation of the current functionality.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

* Model: GPT-5.5
* Interaction mode: Conversational chat
* Reasoning: Standard
* Agentic mode: Disabled
* IDE integration: None
* Plugins/Skills: None

### How it was used

* Assisted with the implementation and debugging of the current functionality.
* Reviewed existing code and test behaviour to identify potential problems.
* Helped analyse errors encountered during development and proposed possible solutions.
* Reviewed the implementation to ensure that the changes were consistent with the existing project architecture and testing strategy.

### Complements

None.

### Context Files

* Existing project source code.
* Existing backend and frontend test suite.

### AI-assisted Development Files

None.

### Files Affected

* Current backend and frontend implementation and test files.

### Human Review

All suggestions and proposed changes were reviewed by the user and manually validated before being incorporated into the project.


## 2026-08-26

### Phase

Phase 3 — Basic Functionality

### Objective

Continue the implementation and testing of the application's user and experience functionality.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

* Model: GPT-5.5
* Interaction mode: Conversational chat
* Reasoning: Standard
* Agentic mode: Disabled
* IDE integration: None
* Plugins/Skills: None

### How it was used

* Helped diagnose and fix issues related to date handling in the Experience tests.
* Reviewed frontend and backend test behaviour after changes to the experience pagination and API responses.
* Assisted in identifying the cause of failing assertions when retrieving experiences from the API.
* Helped adapt the tests to the current implementation and expected API behaviour.

### Complements

None.

### Context Files

* Existing Experience service and API implementation.
* Existing Experience frontend components.
* Existing Vitest and backend integration tests.

### AI-assisted Development Files

None.

### Files Affected

* Experience-related frontend tests.
* Experience-related backend/integration tests.

### Human Review

The proposed solutions and test modifications were reviewed by the user and validated against the current project implementation before being incorporated.


## 2026-08-25

### Phase

Phase 3 — Final Polishing

### Objective

Make small visual and test reliability improvements: ensure experience descriptions span the full card width under the header, improve category chip wrapping and typography, rebalance the Experiences page grid slightly, and make unit/integration tests router-aware.

### Tool

ChatGPT (IDE-assisted)

### Version

GPT-5 mini

### Configuration

* Model: GPT-5 mini
* Interaction mode: Conversational chat integrated with IDE
* Reasoning: Standard
* Agentic mode: Enabled (IDE-integrated edits)
* IDE integration: VS Code (apply_patch edits)

### How it was used

* Adjusted `Experience` component markup and `Experience.css` to place descriptions in a full-width block and tune typography and wrapping.
* Rebalanced the `ExperiencesPage` grid to shift minimal width from the filter to the experiences area.
* Updated unit and integration tests to render inside `MemoryRouter` and corrected rating assertions to match displayed format.

### Files Affected

* frontend/web/src/components/Experience.tsx
* frontend/web/src/components/Experience.css
* frontend/web/src/pages/ExperiencesPage/ExperiencesPage.tsx
* frontend/web/tests/unit/componentes/Experience.test.tsx
* frontend/web/tests/unit/pages/ExperiencesPage.test.tsx
* frontend/web/tests/integration/Experiences.test.tsx

### Human Review

Developer reviewed and validated the edits locally where possible; test changes were limited to test files only.

## 2026-08-24

### Phase

Phase 3 — Basic Functionality

### Objective

Improve the frontend presentation of the Experience Form while maintaining its existing functionality.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

* Model: GPT-5.5
* Interaction mode: Conversational chat
* Reasoning: Standard
* Agentic mode: Disabled
* IDE integration: None
* Plugins/Skills: None

### How it was used

* Helped improve the visual styling of the Experience Form in React.
* Reviewed the existing component structure and suggested improvements to its layout and presentation.
* Adjusted the form's visual hierarchy, spacing and alignment.
* Focused the changes on the React component itself without modifying the associated CSS file.
* Preserved the existing functionality while improving the user interface.

### Complements

None.

### Context Files

* Existing React Experience Form component.
* Existing frontend project structure.

### AI-assisted Development Files

None.

### Files Affected

* React Experience Form component.

### Human Review

All proposed changes were reviewed by the user and manually validated in the application before being incorporated into the project.

## 2026-08-24

### Phase

Phase 3 — Basic Functionality

### Objective

Add pagination support to the Experiences API consumption in the frontend: update the DTOs to accept a Page-like response, adapt the `experience` service to accept pagination parameters and construct query strings, update the `ExperiencesPage` component to read the `content` field, and update unit tests accordingly.

### Tool

ChatGPT / GitHub Copilot

### Version

GPT-5.5

### Configuration

* Model: GPT-5.5
* Interaction mode: Conversational chat integrated with IDE
* Reasoning: Standard
* Agentic mode: Enabled (IDE-integrated edits)
* IDE integration: VS Code GitHub Copilot
* Plugins / Skills: agent-customization, create-agent

### How it was used

* Implemented a new `ExperiencePageDTO` to represent paged API responses.
* Changed `createExperienceService#getAll` to accept optional `page` and `size` parameters and to build a query string when provided.
* Updated `ExperiencesPage` to extract the `content` array from the page response.
* Modified unit tests for the shared service and the page to mock and assert a page-like response (`{ content: [...] }`) and to verify the paginated request URL when pagination args are provided.

### Files Affected

* frontend/shared/src/models/ExperienceSimpleDTO.ts
* frontend/shared/src/services/experience.service.ts
* frontend/shared/tests/unit/services/experience.service.test.tsx
* frontend/web/src/pages/ExperiencesPage/ExperiencesPage.tsx
* frontend/web/tests/unit/pages/ExperiencesPage.test.tsx

### Human Review

All changes were reviewed and validated by the developer. The modifications were intentionally limited to the frontend files listed above to avoid collateral changes.

## 2026-08-20

### Phase

Phase 3 — Basic Functionality

### Objective

Improve the visual design and usability of the Experience Form in the React frontend.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

* Model: GPT-5.5
* Interaction mode: Conversational chat
* Reasoning: Standard
* Agentic mode: Disabled
* IDE integration: None
* Plugins/Skills: None

### How it was used

* Helped improve the layout and visual structure of the Experience Form.
* Suggested and applied Tailwind CSS utility classes to improve spacing, alignment and responsive behaviour.
* Reviewed the organization of the form fields and their visual hierarchy.
* Adjusted the form layout to provide a clearer and more consistent user experience.
* Preserved the existing functionality while focusing on the presentation layer.

### Complements

None.

### Context Files

* Existing React Experience Form component.
* Existing Tailwind CSS configuration and frontend structure.

### AI-assisted Development Files

None.

### Files Affected

* React Experience Form component.

### Human Review

All styling suggestions were reviewed by the user and manually validated in the application before being incorporated into the project.

## 2026-08-18

### Phase

Phase 3 — Basic Functionality

### Objective

Implement and validate the backend functionality for creating new experiences, including the corresponding automated tests.

### Tool

ChatGPT

### Version

GPT-5.5

### Configuration

* Model: GPT-5.5
* Interaction mode: Conversational chat
* Reasoning: Standard
* Agentic mode: Disabled
* IDE integration: None
* Plugins/Skills: None

### How it was used

* Helped design and implement the server-side tests for creating new experiences.
* Reviewed the existing experience creation flow between the controller, service, mapper and repository layers.
* Assisted in identifying the required test cases and assertions for the new experience functionality.
* Helped diagnose and correct problems encountered while running the tests.
* Reviewed the integration between the experience creation endpoint and the persistence layer.

### Complements

None.

### Context Files

* Existing backend Experience-related classes.
* Existing backend test suite.
* Experience DTOs, mapper, service, controller and repository.

### AI-assisted Development Files

None.

### Files Affected

* Backend Experience-related test files.
* Existing Experience service and API test infrastructure where required.

### Human Review

All testing guidance and proposed changes were reviewed by the user and validated against the existing implementation before being incorporated into the project.

## 2026-08-17

### Phase

Phase 3 — Basic Functionality

### Objective

Continue the development of the frontend by implementing the HTML/JSX structure of a React component and ensuring its behaviour is correctly covered by automated tests.

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

- Helped implement and refine the HTML/JSX structure of the React component.
- Reviewed the component structure and its interaction with the existing frontend logic.
- Helped implement unit tests using Vitest.
- Helped implement integration tests using Vitest and Testing Library.
- Reviewed test setup, assertions and service interactions to ensure that the implemented functionality was correctly covered.

### Complements

None.

### Context Files

- React component source files.
- Existing Vitest unit and integration test files.
- Frontend service implementations used by the component.

### AI-assisted Development Files

None.

### Files Affected

- React component files involved in the implemented functionality.
- Corresponding Vitest unit test files.
- Corresponding Vitest integration test files.

### Human Review

The proposed component implementation and test changes were reviewed by the user and adapted to the existing project architecture before being incorporated into the project.

## 2026-08-16

### Phase

Phase 3 — Basic Functionality

### Objective

Continue the development of the frontend functionality by implementing the HTML structure of the React component and ensuring its behaviour is correctly covered by automated tests.

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

- Helped implement and refine the HTML/JSX structure of the React component.
- Reviewed the component structure and its interaction with the existing frontend logic.
- Helped create and adjust Vitest unit tests for the component.
- Helped create and adjust Vitest integration tests to verify the component's behaviour with the application services.
- Reviewed test assertions and test setup to ensure that the implemented functionality was correctly covered.

### Complements

None.

### Context Files

- React component source files.
- Existing Vitest unit and integration test files.
- Frontend service implementations used by the component.

### AI-assisted Development Files

None.

### Files Affected

- React component files involved in the implemented functionality.
- Corresponding Vitest unit test files.
- Corresponding Vitest integration test files.

### Human Review

The proposed component implementation and test changes were reviewed by the user and adapted to the existing project architecture before being incorporated into the project.

## 2026-08-15

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Complete and improve the automated test coverage of the user and city-related backend components, including services, entities, DTOs and mappers.

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

- Investigated and fixed problems in `UserService` unit tests related to the Spring Security context.
- Reviewed how the authenticated user is obtained from the `SecurityContext` during unit testing.
- Assisted in configuring the security context correctly in the tests without requiring the complete Spring application context.
- Designed and implemented unit tests for the `CityService`.
- Designed and implemented integration tests for city-related functionality.
- Added and reviewed tests for the `City` entity.
- Added tests for the city DTOs to verify their behaviour and data handling.
- Added and reviewed tests for the `CityMapper` to ensure the correct transformation between DTOs and domain entities.
- Reviewed the test structure to maintain isolation between unit tests and integration tests.

### Complements

None.

### Context Files

- `backend/src/main/java/.../service/UserService.java`
- `backend/src/test/java/.../service/UserServiceTest.java`
- `backend/src/main/java/.../service/CityService.java`
- `backend/src/main/java/.../domain/City.java`
- City DTO classes.
- `CityMapper` implementation.
- Existing backend unit and integration test suites.

### AI-assisted Development Files

None.

### Files Affected

- `backend/src/test/java/.../service/UserServiceTest.java`
- `backend/src/test/java/.../service/CityServiceTest.java`
- `backend/src/test/java/.../domain/CityTest.java`
- City DTO test files.
- `backend/src/test/java/.../mapper/CityMapperTest.java`
- Related backend integration test files.

### Human Review

All proposed test cases, security context configuration and testing strategies were reviewed, adapted and validated by the user before being incorporated into the project.

## 2026-08-14

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Improve the application's authentication persistence after a browser page reload, ensuring that an authenticated user can be restored without requiring the user to log in again.

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

- Analysed the authentication lifecycle in the React application.
- Identified that the in-memory `userStore` was cleared when the page was reloaded.
- Designed a React hook in the `App` component to retrieve the authenticated user's information from the backend when the application starts.
- Used the existing authentication cookie to allow the backend to identify the user without requiring the credentials to be entered again.
- Helped integrate the retrieved user information back into the application's user state.
- Reviewed the solution to ensure that normal navigation and initial application loading continued to work correctly.

### Complements

None.

### Context Files

- `frontend/web/src/App.tsx`
- Frontend user store implementation.
- Frontend authentication service.
- Backend `/users/me` endpoint.

### AI-assisted Development Files

None.

### Files Affected

- `frontend/web/src/App.tsx`

### Human Review

The proposed hook and authentication flow were reviewed, adapted and validated by the user before being incorporated into the project.

## 2026-08-11

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Improve the visual design and layout of the Sign Up page to provide a cleaner and more consistent user interface.

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

- Reviewed the existing Sign Up page component and its current layout.
- Suggested and implemented improvements to the page structure and visual presentation.
- Adjusted the React component's styling classes to improve spacing, alignment, sizing and overall layout.
- Helped maintain consistency between the Sign Up page and the rest of the application's visual design.
- The styling changes were implemented directly through the React component without modifying the associated CSS file.

### Complements

None.

### Context Files

- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`

### AI-assisted Development Files

None.

### Files Affected

- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`

### Human Review

The proposed styling changes were reviewed and adapted by the user before being incorporated into the project. The user also verified that the changes only affected the React component and did not require modifications to the CSS file.

## 2026-08-10

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Help implement and debug integration tests for the User Profile page, including real authentication flow testing with cookies and asynchronous rendering assertions.

### Tool

ChatGPT / GitHub Copilot

### Version

GPT-5.5 / Claude Haiku 4.5

### Configuration

- Model: GPT-5.5 / Claude Haiku 4.5
- Interaction mode: Conversational chat / IDE integration
- Reasoning: Standard
- Agentic mode: Enabled
- IDE integration: VS Code GitHub Copilot
- Plugins/Skills: agent-customization, create-agent

### How it was used

- Assisted in creating integration tests for the User page using the real API instead of mocks.
- Helped configure the tests to use a cookie-aware fetch layer so the login cookies from the backend were preserved for the follow-up profile request.
- Suggested logging points to inspect the login response, cookie headers and profile fetch behavior during test execution.
- Helped diagnose the failure caused by the page loading profile data asynchronously and recommended waiting for the rendered content before asserting.

### Complements

None.

### Context Files

- `frontend/web/tests/integration/User.test.tsx`
- `frontend/shared/src/services/auth.service.ts`
- `frontend/shared/src/services/user.service.ts`
- `frontend/web/src/pages/UserPage/UserPage.tsx`

### AI-assisted Development Files

- `frontend/web/tests/integration/User.test.tsx`

### Files Affected

- `frontend/web/tests/integration/User.test.tsx`

### Human Review

All proposed test changes and debugging steps were reviewed and adapted by the user before being incorporated into the project.

---

## 2026-07-30

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Extend the automated test suite for both the backend and frontend while improving the user profile interface and maintaining the project documentation.

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

- Assisted in implementing the missing unit tests for the `UserService#getUserById()` method, covering successful retrieval, unauthorized access, unauthenticated users and missing users.
- Helped complete the frontend unit and integration tests for the Sign Up page after recent implementation changes.
- Assisted in implementing additional frontend tests for the `UserService`, adapting them to the latest service implementation.
- Reviewed the mocking strategy for frontend services to improve test isolation and maintainability.
- Suggested improvements to the styling and layout of the User Profile page to achieve a cleaner and more consistent interface.
- Assisted in maintaining the project documentation by generating AI usage records following the project's established documentation format.

### Complements

None.

### Context Files

- `backend/src/main/java/.../service/UserService.java`
- `backend/src/test/java/.../service/UserServiceTest.java`
- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`
- Frontend Sign Up test files.
- Frontend `UserService` test files.
- User Profile page components and styles.
- `docs/AI_USAGE.md`

### AI-assisted Development Files

None.

### Files Affected

- `backend/src/test/java/.../service/UserServiceTest.java`
- `frontend/web/src/tests/unit/SignUpPage.test.tsx`
- `frontend/web/src/tests/integration/SignUpPage.integration.test.tsx`
- `frontend/shared/src/services/user.service.ts`
- Frontend `UserService` test files.
- User Profile page components and styles.
- `docs/AI_USAGE.md`

### Human Review

All proposed tests, UI improvements and documentation updates were reviewed, adapted and validated by the user before being incorporated into the project.

---

## 2026-07-29

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Implement and complete the unit tests for the `UserService#getUserById()` method, ensuring the correct behaviour for authorization checks, repository interactions and exception handling.

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

- Assisted in designing the unit tests for the `getUserById()` service method.
- Reviewed the different execution paths depending on the authenticated user and authorization rules.
- Adapted the tests to the repository implementation using `findById()` returning an `Optional`.
- Suggested test cases covering successful retrieval, unauthorized access, unauthenticated users and missing users resulting in exceptions.
- Reviewed the mocking strategy for the repository and dependent methods to ensure the service logic was tested in isolation.

### Complements

None.

### Context Files

- `backend/src/main/java/.../service/UserService.java`
- `backend/src/test/java/.../service/UserServiceTest.java`

### AI-assisted Development Files

None.

### Files Affected

- `backend/src/test/java/.../service/UserServiceTest.java`

### Human Review

The proposed unit tests and testing strategy were reviewed, adapted and validated by the user before being incorporated into the project.

---

## 2026-07-27

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Update and improve unit tests for the Log in feature to increase reliability and coverage.

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

- Reviewed existing Log in unit tests and updated test cases to match current component behaviour.
- Fixed mock API responses, corrected assertions, and added edge-case checks to improve coverage.
- Provided concrete test code snippets and guidance for integrating the updates with Vitest.

### Complements

None.

### Context Files

- `frontend/web/src/pages/LoginPage/LoginPage.tsx`
- Existing Log in unit test files in the frontend test suite.

### AI-assisted Development Files

None.

### Files Affected

- `frontend/web/src/tests/login.test.ts`
- `frontend/web/src/pages/LoginPage/LoginPage.test.tsx`

### Human Review

The updated Log in unit tests were reviewed by the user and are ready for validation.

---

## 2026-07-23

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Improve the application's authentication mechanism by migrating JWT management to HTTP cookies and document the implementation decision regarding the use of self-signed SSL certificates during development and Continuous Integration.

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

- Reviewed the implications of using self-signed SSL certificates in development, production and GitHub Actions workflows.
- Assisted in evaluating different alternatives for handling HTTPS during Continuous Integration.
- Helped document the implementation decision explaining why SSL is disabled in CI while remaining enabled for production deployments.
- Assisted in updating the frontend API requests to include credentials, allowing the browser to automatically manage JWT authentication cookies.
- Reviewed the advantages of using HTTP-only cookies over manually storing JWT tokens in the client.

### Complements

None.

### Context Files

- Backend security configuration.
- GitHub Actions workflow files.
- Frontend API client implementation.
- Implementation decision documentation.

### AI-assisted Development Files

None.

### Files Affected

- Frontend API client (`frontend/shared/...`)
- Backend security configuration.
- GitHub Actions workflow files.
- `docs/implementation-decisions.md`

### Human Review

The proposed authentication changes and the implementation decision were reviewed, adapted and validated by the user before being incorporated into the project.

---

## 2026-07-22

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Support and fix integration tests for the Sign Up page, including a new Sign Up integration test and a repaired existing `SignUpPage` integration test.

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

- Helped implement a new integration test for the Sign Up flow.
- Fixed an existing Sign Up page integration test by correcting test setup and assertions.

### Complements

None.

### Context Files

- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`
- Existing Sign Up integration test files in the frontend test suite.

### AI-assisted Development Files

None.

### Files Affected

- `frontend/web/src/tests/unit/SignUpPage.test.tsx`
- `frontend/web/src/tests/integration/SignUpPage.integration.test.tsx`

### Human Review

The integration test guidance and fix were reviewed by the user and are ready for validation.

---

## 2026-07-20

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Provide responsive styles for the Sign Up form and create Vitest unit tests for `userService` and `SignUpPage`.

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

- Provided CSS and responsive rules for the Sign Up form (`SignUpPage`), covering layout, validation UI, and mobile-first breakpoints to ensure full responsiveness.
- Implemented Vitest unit test examples for `userService` and `SignUpPage` (component rendering, form interactions and mocked API calls), and provided test files and setup guidance.

### Complements

None.

### Context Files

- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`
- `frontend/web/src/pages/SignUpPage/SignUpPage.css`

### AI-assisted Development Files

- `frontend/web/src/tests/userService.test.ts`
- `frontend/web/src/pages/SignUpPage/SignUpPage.test.tsx`

### Files Affected

- `frontend/web/src/pages/SignUpPage/SignUpPage.css`
- `frontend/web/src/tests/userService.test.ts`
- `frontend/web/src/pages/SignUpPage/SignUpPage.test.tsx`

### Human Review

The responsive styles and Vitest tests were reviewed by the user and are ready for integration.

---

## 2026-07-19

### Phase

Phase 3 — Basic Functionality and Docker

### Objective

Assist with the Sign Up page form implementation in `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`.

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

- Reviewed the Sign Up page form structure and provided guidance on field layout, input validation, and state handling in `SignUpPage.tsx`.
- Suggested a clean, user-friendly form approach with controlled React inputs, error handling, and form submission flow.

### Complements

None.

### Context Files

- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`

### AI-assisted Development Files

None.

### Files Affected

- `frontend/web/src/pages/SignUpPage/SignUpPage.tsx`

### Human Review

The suggested Sign Up form improvements were reviewed by the user and are pending manual integration.

---

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