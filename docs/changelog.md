# Changelog

All notable changes to this project will be documented in this file.

## 0.0.2 - 2026-06-29

### Added

- *Vitest* configuration for unit testing website pages and components
- Experiences page unit tests
- Experience component unit tests

### Updated

- Web's dependencies, adding *Vitest*.
- Experiences page to satisfy unit tests and handle services errors.

### Notes

- All unit tests have been implemented and passed
- Website unit tests can be run with the following command:
	`npx vitest

## 0.0.2 - 2026-06-25

### Added

- *H2* database for server tests
- Application properties file for tests

### Updated

- Backend dependencies, adding *H2* dependency

### Notes

- Frontend doesn't have unit tests yet
- All backends unit tests have been implemented with JUnit and can be executed with:
	`mvn run tests

## 0.0.2 - 2026-06-23

### Added

- Experience entity unit tests
- ExperienceMapper unit tests
- ExperienceService unit tests
- ExperienceRestController unit tests
- ExperienceSimpoleDTO unit tests

### Notes

- Frontend doesn't have unit tests yet
- All backends unit tests have been implemented with JUnit.
## 0.0.2 - 2026-06-23

### Added

- Experiences page, shows the list of experiences saved in the database
- Experience component, displays the summarized information of an experience
- Environment variables for website allowing to never display sensitive information such as the API URL.
- API Client file, it allows to create an instance with all the requests that can be made from the web to the API as methods.
- Experience service, holds the method to obtain all the experiences and its constructor. The service is expected to be used in the same way as the API Client instance.
- Routes constants, a translator for the router. This gives a quick access when needing to change the URL of a component or page.
- Router, it makes sure to display the page or component correspondent to the URL without reloading the entire website giving a more fluent experience to the user.
- ExperienceSimpleDTO, the same DTO as in the API but adapted to typescript so it's information can be displayed in components.

### Notes

- Minimal functionality has been completed
- Environment variables have been implemented in website project

## 0.0.2 - 2026-06-20

### Added

- OPEN API dependency to backend for endpoints documentation

### Updated

- Updating experience entity with rating field.

### Notes

- Backend minimal functionality is completed.
- Frontend hasn't been developed yet for minimal functionality.

## 0.0.2 - 2026-06-19

### Added

- Creation of experience entity.
- Creation of experience service
- Creation of experience repository
- Creation of experience rest controller.
- Creation of experience simple DTO.
- Implementation of experience mapper from entity to simple DTO.
- Docker container for MySQL database.

### Updated

- Updating pom.xml of API to read environment variables for database and adding mapstruct dependency.

### Notes

- Backend has almost achieved minimal functionality.
- Frontend hasn't been developed yet for minimal functionality.

## 0.0.2 - 2026-06-18

### Added

- Complementary technology; interactive maps to display either Erasmus destinations or city experiences.
- Creation of backend folder and API project with Springboot.
- Creation of frontend folder using a pnpm workspace and React proyect in web folder.

### Updated

- Phase change, end of analysis and design phase, beginning of creation of tests and workflows.

### Documentation

- Expanded planned technologies documentation.

### Notes

- Project advances to test creation.
- No functional implementation has been developed yet.

## 0.0.1 - 2026-06-17

### Added

- City Ranking page wireframe.
- City Ranking page documentation.

### Updated

- Web Interface documentation with navigation routes for every page.

### Documentation

- Refined application design.
- Expanded navigation and usability documentation.

### Notes

- Project remains in the analysis and design phase.
- No functional implementation has been developed yet.

---

## 0.0.1 - 2026-06-16

### Added

- Initial project repository structure.
- Project README with functional overview.
- Objectives documentation.
- Methodology documentation.
- Functional requirements documentation.
- User roles and permissions documentation.
- Domain entities definition.
- Wireframes and navigation analysis.
- State of the art analysis.
- Advanced algorithms definition.
- CHANGELOG.md creation.
- AI_USAGE.md creation.

### Documentation

- Defined project scope and objectives.
- Defined basic, intermediate and advanced functionalities.
- Defined entities and relationships.
- Defined navigation flow and user interface prototypes.
- Defined project development methodology and milestones.

### Notes

- This version contains only analysis and design artifacts.
- No functional implementation has been developed yet.

---
🏠 [Home](../README.md) | 📚 Documentation
---