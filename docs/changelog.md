# Changelog

All notable changes to this project will be documented in this file.

## 0.0.3 - 2026-07-27

### Added

- Log in page unit and integration tests.
- Log in End to End tests
- Log in page Selenium test
- Authentication service unit tests for log in.

### Updated

- Sign up integration tests in client side, taking into account the log in page.
- Log in page, completing the styles.
- Postman collection adding log in and get user information requests.
- User service unit tests, adding get user information test and a before all method to obtain credentials.


### Notes

- I have noticed selenium tests for the About us  and home page are missing, styles on the log in page are finally completed.

## 0.0.3 - 2026-07-25

### Updated

-  User service integration and unit tests, implementing tests to check the get user information functionality.

### Notes

- Selenium and end to end tests are still missing, as well as integration tests in client side.

## 0.0.3 - 2026-07-24

### Added

- Log in page component in client, with initial styles method to retrieve information from form and structure
- Authentication service in client, implementing log in request from client to server.
- User store, to store the user credentials after successful log in, being used for example by the header to show the main information of user.
- User simple DTO, it holds the basic and main information of the user.

### Updated

- Router and routes in client, adding log in page route
- Global styles, adding more common styles between components.
- User service, implementing the call to *"/users/me"* endpoint to obtain user's main information.
- Client dependencies, implementing zustand.
- Header component, swapping the sign up and log in links by the user's information after log in.
- Sign up unit test in client and component adding the link to log in.

### Notes

- Styles in the log in component are not definitive and the user store is working, the header updates without needing to reload the site.

## 0.0.3 - 2026-07-23

### Added

- Implementation Decision 002, to solve self-signed certificate testing dilemma
- Self-signed certificate for server

### Updated

- Security configuration allowing secure connections
- Api Client in client, allowing to use and receive Json Web Tokens as credentials. 

### Notes

- Security has been implemented, but there are not many endpoints so it's mostly empty.

## 0.0.3 - 2026-07-22

### Added

- Client user service unit tests
- Sign up page unit tests
- Sign up Selenium test
- Json Web tokens folder to create, refresh and delete JWT
- Security configuration file, setting the type of users allowed to use the existing endpoints.
- Password Encoder to encode the user's passwords when created.
- Authentication controller implementing the endpoints to login and logout with JWT.

### Updated

- Home page, About us page and experiences page styles making them responsive.
- Sign up page giving it styles and making it responsive.
- GitHub Actions workflows  for the new "v1" version of the API URLs
- Experiences Selenium test
- User unit tests implementing a specific test for entity's setters.
- Backend dependencies, adding Json Web Tokens and Spring Security
- User service tests, taking into account the new password encoder.
### Notes

- Some sign up integration tests have been giving some trouble, specially the "email already registered" test

## 0.0.3 - 2026-07-20

### Added

- Client user service unit tests
- Sign up page unit tests

### Updated

- Home page, About us page and experiences page styles making them responsive.
- Sign up page giving it styles and making it responsive.
### Notes

- Integration tests on the client side are still due but in production mode sign up works as it should.

## 0.0.3 - 2026-07-19

### Added

- User service, mapper and rest controller.
- User service and mapper unit tests
- User service integration tests.
- End to End sign-up endpoint test.
- User service in client side.
- Sign up form page, it communicates with 

### Updated

- User entity adding setters and getters.
- UserDTO, changing attribute name to it matches user entity.
- UserDTO unit tests changing attribute name
- ClientAPI in shared client folder, creating a new type called ClientAPI for components and pages.
- Postman colleciton, adding the sign-up endpoint request to the collection.
### Notes

- Sign-up form works, detecting mismatching passwords or when server tells the email is already registered. Styles for the sign-up page are still missing as well as client side testing.

## 0.0.3 - 2026-07-18

### Added

- About us page and styles, giving users information about developers and the reasons behind its creation.
- User entity and DTOs for the creation of users in a future sign up form.
- User unit tests, checking the DTOs and the entities.

### Updated

- Router and routes, adding the new about us page.
### Notes

- Two DTOs have been created for user, one with the user information after creation and another that will represent the information represented in the form.

## 0.0.3 - 2026-07-17

### Added

- Tailwind css to web dependencies.
- Home page component.
- Web's general styles settings.

### Updated

- Experiences page selenium test, taking into account the new home page.
- .gitIgnore to dismissed the coverage files generated by testing tools.
- Web router and routes, changing the experiences page to "/experiences" endpoint and assigning main endpoint to home page.

### Notes

- Home page has been added after passing pull request workflow, it explains the functionalities the application will offer.

## 0.0.3 - 2026-07-16

### Added

-  Logo image in the header, which also serves as link to go back to home page.

### Updated

- Backend dependencies, updating Maven to 4.1.0.
- Header component, adding styles and format to the links.

### Notes

- Home page styles are giving some trouble to center the elements.
- Basic functionalities and docker phase has started.

## 0.0.2 - 2026-07-14

### Added

-  Postman Collection has been created with examples of requests that can be sent to the API.
-  Development guide explaining architecture, quality assurance and how to execute the application.

### Updated

-  AI usage document with entries until the current date.
-  Readme with development guide.

### Notes

-  Anyone can now execute the application in local and test the API with the Postman Collection.

## 0.0.2 - 2026-07-12

### Added

-  Architecture diagram
- Client and Server class diagrams
- Generating OPENAPI documentation

### Notes

-  Original architecture diagram has been reduced and divided into the class diagrams and the final architecture diagram.
- API documentation can be found on the API folder inside the documentation folder.

## 0.0.2 - 2026-07-10

### Added

- Environment variables in pull request workflow for integration tests of frontend.

### Updated

- Pull request workflow

### Notes

- Solved pull request endless waiting problem by forcing to website process to listen to all local petitions and adding environment variables for frontend integration tests in workflow.

## 0.0.2 - 2026-07-09

### Added

- Commit workflow that executes unit tests of backend and frontend when commits are pushed to any branch of repository.
- Pull request workflow to execute all tests when a pull request is made from the main branch.

### Updated

- Repository's workflows

### Notes

- Although commit workflow executes perfectly, pull request workflow doesn't seem to be able to execute the Selenium tests properly, it remains waiting to detect if the website is operational.

## 0.0.2 - 2026-07-08

### Added

- Selenium test for experiences page.

### Updated

- Backend dependencies, adding Selenium.

### Notes

- Pnpm version mismatched between Intellij IDEA and terminal, giving trouble to start the frontend inside the Selenium test with a Process Builder.

## 0.0.2 - 2026-07-03

### Added

- Web experience service unit test.
- End to End test for /experiences/ endpoint.
- Basic test database for backend tests with Docker Test Containers.
- Scripts for web and shared folder's tests execution from frontend folder.

### Updated

- Backend dependencies, adding Rest Assured for end to end tests and JaCoCo for code coverage of tests.
- Integration backend tests, implementing new basic test database.
- Frontend dependencies, adding v8 for web and shared folder.
- Shared folder, implementing Vitest.

### Notes

- Tests coverage has been generated correctly.
- New test database on server makes easier to create new integration or E2E tests.
- Shared and web folder in client need to remain separated, it makes testing simpler.

## 0.0.2 - 2026-07-02

### Updated

- Experience service integration test to fix problem with test context.

### Deleted

- Backend application test file

### Notes

-  All test, unit and integration, of backend have been passed and can be executed with:
		`mvn test` or `mvn clean test

## 0.0.2 - 2026-07-01

### Added

- Get all experiences integration test with production API REST.
- Experience service type for injection in components.

### Updated

- Experiences page component for service injection
- Experiences page unit test because of component update

### Notes

- All unit tests have been implemented and passed
- Website unit tests can be run with the following command:
	`npx vitest
## 0.0.2 - 2026-06-30

### Added

- ExperienceService integration test
- Tests tags for backend
- Docker TestContainers as database for integration tests in backend

### Deletion

- H2 Database for backend tests

### Notes

- Backend integration and unit tests have been implemented
- Integration tests in server use docker instead of H2.

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
- Website unit tests can be run with the following command.

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