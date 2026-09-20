# Changelog

All notable changes to this project will be documented in this file.

## 0.0.3 - 2026-09-20

### Added

-  Backend architecture diagram, displaying all the different components of the backend and their relationships.

### Updated

- Development guide documentation, adding the new environment variables to the documentation and updated diagrams
- API documentation, adding all the existing endpoints to html file.

### Removed

- Backend class diagram

## 0.0.3 - 2026-09-19

### Added

-  Frontend architecture diagram, this diagram shows only the frontend side of the app with all its components, interfaces and services.

### Updated

- General architecture diagram, changing the components and classes for the respective folders.

### Removed

- Frontend class diagram

## 0.0.3 - 2026-09-18

### Added

- Database architecture diagram

### Updated

- Architecture documentation, adding database diagram and explaining relationships between entities.

## 0.0.3 - 2026-09-17

### Added

- Video displaying basic functionalities

## 0.0.3 - 2026-09-16

### Added

- App execution documentation, explaining how to run the app in local

### Updated

- Docker compose publish workflow
- Docker compose file

### Notes

- Docker compose now publishes with environment variables instead of fixed values.

## 0.0.3 - 2026-09-14

### Added

- Environment variables to Docker Compose publishing commands.
- Example environment variables in Docker Compose.
- Port environment variable in the workflow.

### Updated

- Docker workflows and Compose publishing configuration.
- Docker Compose ports to use port 8443.
- Environment variable validation in workflows.
- Docker Compose port configuration and environment variable handling.
- Docker Compose tag configuration.
- Compose publishing workflow.
- Docker workflow working directory.
- Manual Docker workflow configuration.

### Removed

- Manual trigger from the Docker workflows.

### Notes

- Detected bug in API working with database in update mode, data initializer doesn't take into account if data already exists on database.

## 0.0.3 - 2026-09-13

### Added

- Dockerfile for the application.
- Docker Compose configuration.
- Docker Compose publishing workflow.
- Developer Compose publishing workflow.
- Release publishing workflow for Docker.
- Manual Compose publishing workflow.

### Updated

- Maven and frontend package versions.
- Experience mapper unit tests.
- Integration tests and user form page.
- Frontend service interfaces.
- `ApiError` handling.
- Integration tests with completed mock services.
- Unit tests and mock services.
- Docker workflows.
- Docker Compose configuration.
- Docker Compose tag and port configuration.

### Notes

- Docker compose is not publishing with environment variables but resolved values.

## 0.0.3 - 2026-09-12

### Added

- Test profile for example data.
- Environment variables for the administrator user.
- Workflow configuration for administrator environment variables.

### Updated

- User page integration tests.
- User comments Selenium tests.
- User form and related tests.

## 0.0.3 - 2026-09-11

### Added

- User form integration tests.
- User form Selenium tests.
- User form unit tests.

### Updated

- User page and related unit tests.
- User form page redirection.
- User service unit tests for updating users.
- User page Selenium tests.
- User page route.

### Notes

- 

## 0.0.3 - 2026-09-10

### Added

- Update user endpoint and related user service method.
- Server unit and integration tests for updating users.
- User form page based on the existing sign up page.

### Updated

- Security configuration for the update user endpoint.
- User form page unit tests.
- Postman collection with the update user request.
- User page Selenium tests.

### Notes

- 

## 0.0.3 - 2026-09-09

### Added

- Delete comment endpoint and related backend logic.
- Unit, integration and end-to-end tests for deleting comments.
- Frontend comment service.
- Comment service unit tests.
- Selenium test for deleting comments.

### Updated

- Security configuration for the delete comment endpoint.
- User comments component.
- Postman collection.
- User page unit and integration tests for deleting comments.
- Error endpoint E2E tests.
  
### Notes

- 

## 0.0.3 - 2026-09-08

### Added

- Error page unit, integration and Selenium tests.

### Updated

- Error page styles.
- Test `application.properties`.
- Frontend service unit tests.
- E2E authentication base test.
- Workflow environment variables.
  
### Notes

- 
## 0.0.3 - 2026-09-07

### Added

- Integration tests for internal server errors.

### Updated

- Console logs replaced with console errors.
  
### Notes

- 

## 0.0.3 - 2026-09-06

### Added

- Internal server error tests.

### Updated

- Frontend services, components and pages to handle `ApiError`.
- Unit tests for pages and components.
- Tests covering internal server errors.
  
### Notes

- 

## 0.0.3 - 2026-09-04

### Updated

- Unit tests to use `ApiError` and cover API error handling.

### Notes

- 

## 0.0.3 - 2026-09-03

### Added

- `ApiError` class.
- Error page and error page route.
- Error controller tests.

### Updated

- Services to use `ApiError`.
- Components and pages to handle API errors.
- Routes to include the error page.
  
### Notes

- 

## 0.0.3 - 2026-09-01

### Added

- Delete experience endpoint and related backend logic.
- Delete experience button in the frontend.
- Unit, integration and end-to-end tests for deleting experiences.
- User comments component in the user page.
- Unit, integration and Selenium tests for user comments.

### Updated

- Security configuration for the delete experience endpoint.
- Web experience service tests for deleting experiences.
- User page unit tests.
- Comment simple DTO, user DTO and frontend user service.
- User mapper unit tests.
- Postman collection.
  
### Notes

- 

## 0.0.3 - 2026-08-31

### Added

- Get user comments endpoint and related backend logic.
- Unit, integration and end-to-end tests for getting user comments.

### Updated

- Comment simple DTO to include the experience ID.
- DTOs and related tests for user comments.
- Security configuration for the user comments endpoint.
- Data initializer.
- Postman collection.
- Functionalities documentation.
  
### Notes

- 

## 0.0.3 - 2026-08-30

### Added

- Get user experiences endpoint and related backend logic.
- User experiences component in user page.
- Unit, integration and end-to-end tests for getting user experiences.
- Selenium test for user experiences.

### Updated

- User DTO and frontend user service to include experiences.
- Security configuration for the new endpoint.
- User service unit and integration tests.
- Authentication cookie handling in integration tests.
- Experiences ordering to show the most recent experiences first.
- Experience form integration test to wait for cities to load.
- Postman collection.

### Fixed

- Endless requests issue in the frontend.
  
### Notes

- 

## 0.0.3 - 2026-08-29

### Updated

- Error handling for lazy fetching.

### Notes

- 

## 0.0.3 - 2026-08-28

### Updated

- Comments component styling with scrolling and a maximum height.
- Comments component rendering to handle errors correctly.
- Selenium test for posting comments.
- Selenium test naming.

### Notes

- 

## 0.0.3 - 2026-08-27

### Added

- Comment entity, repository, service and DTOs.
- Unit and integration tests for posting and getting comments.
- End-to-end tests for posting and getting comments.
- Comment DTOs and frontend methods for posting and getting comments.
- Comments component with unit and integration tests.
- Selenium tests for comments.

### Updated

- User and experience entities to support comments.
- Experience tests to include comments.
- DTOs and mappers, simplifying their structure.
- Security configuration with the new comment endpoint.
- Postman collection with the post comment request.
- Comment endpoint to return HTTP 201 when creating a comment.
- Selenium tests related to the new functionality.

### Notes

- 

## 0.0.3 - 2026-08-26

### Added

- Detailed experience page with unit and integration tests.
- Data initializer for the API.
- Unit and integration tests for the detailed experience page.

### Updated

- Experience form Selenium and page tests.
- Tests affected by the new data initializer and pagination.
- Postman collection.
- City service `addCity` method and tests.
- User service `createUser` method.
- Experience service unit tests.
- Experience form tests to handle the new redirection.
- Detailed experience page to fix a bug.
- Pagination handling to prevent undefined pagination issues.

### Notes

- 

## 0.0.3 - 2026-08-25

### Added

- Detailed Experience page.
- Route for the Detailed Experience page.
- Frontend Experience DTO.
- Experience service method to retrieve an experience by ID.
- Unit, integration and E2E tests for retrieving an experience by ID.

### Updated

- Experience component.
- Experiences page styles.
- Experiences page and component tests.
- Frontend pagination DTO and Experience service tests.

### Notes

- 

## 0.0.3 - 2026-08-24

### Added

- Pagination support for retrieving experiences.

### Updated

- Experience retrieval tests to support pagination.
- Frontend DTO for paginated experience responses.
- Frontend Experience service and related tests.

### Notes

- 

## 0.0.3 - 2026-08-23

### Added

- Updated Experience Simple DTO and mapper to support the current experience data.

### Updated

- Experience unit and integration tests.
- Frontend DTOs and Experience service tests.
- Functionalities documentation.

### Notes

- 

## 0.0.3 - 2026-08-21

### Added

- Delete user endpoint.
- User account deletion functionality.
- Delete user by ID unit and integration tests.
- User page delete account button.
- E2E tests for deleting a user by ID.
- Selenium tests for the delete account button.

### Updated

- Security configuration to support user deletion.
- Client API types to support DELETE requests.
- User service with the delete user by ID method.
- Postman collection with the delete user request.
- Authenticated Selenium test setup with a dedicated user for deletion.
- Experience Form Selenium tests.

### Notes

- 

## 0.0.3 - 2026-08-20

### Added

- Experience Form page.
- Unit and integration tests for the Experience Form page.
- DTOs for Experience and City required by the form.
- Service methods required by the Experience Form.

### Updated

- Experience entity constructor.
- Experience tests to support multiple categories, limited to three categories.
- Postman collection.
- Backend unit tests.
- Service unit tests.
- Routes for the Experience Form page.

### Notes

- 

## 0.0.3 - 2026-08-18

### Added

- Experience DTO and Experience Form DTO.
- Endpoint and service method to create new experiences.
- Integration tests for creating experiences.
- E2E tests for posting experiences.

### Updated

- City, User and Experience entities with additional attributes.
- Database relationships between Experience, City and User.
- Security configuration for experience creation.
- Experience Simple DTO and related tests after the entity changes.
- Postman collection with the new experience functionality.
- Functionalities documentation.
- Enumeration naming.

### Notes

- 

## 0.0.3 - 2026-08-17

### Added

- Categories enumeration and endpoint to retrieve available categories.
- Tests for retrieving categories.
- Integration and unit tests for the Get Cities endpoint.
- E2E test for retrieving cities.
- Selenium test for the City Form with administrator authentication.

### Updated

- Categories unit tests.
- Postman collection with the Get Categories request.
- Postman collection for city-related requests.
- City service and mapper to support retrieving cities.
- City and User DTOs and User page.
- City validation to prevent duplicated cities by name and country.
- City name and country formatting.

### Notes

- 

## 0.0.3 - 2026-08-16

### Added

- City service and City Form page.
- City Form route.
- City Form unit tests.
- Integration tests for city functionality.
- Authentication support for client integration tests.

### Updated

- General application styles.
- Login button and general styles.
- Login tests.
- App and User integration tests.
- Functionalities documentation.

### Notes

- 

## 0.0.3 - 2026-08-15

### Added

- City entity and repository.
- City DTOs and mapper.
- City service and REST controller.
- Unit tests for the City entity and City service.
- Integration tests for city-related functionality.

### Updated

- Security configuration to support the new city functionality.
- User service integration tests to correctly handle the security context.
- User integration tests to check authenticated and administrator users.
- Test configuration to improve compatibility with GitHub Actions.

### Notes

-

## 0.0.3 - 2026-08-14

### Added

- Automatic retrieval of the authenticated user when the application starts.

### Updated

- Authentication flow to restore the authenticated user after a page reload.
- Login redirection to the User page when the user is already authenticated.
- Authentication REST controller and security configuration.
- Log in tests to cover the updated authentication flow.
- App unit and integration tests.
- User service integration tests.
- Password encoder configuration.

### Notes

-

## 0.0.3 - 2026-08-11

### Added

- Available Soon page.
- Available Soon route.
- Functions to redirect to the Available Soon page.

### Updated

- Sign Up page styles and layout.
- Sign Up Selenium tests to reflect the updated page.

### Notes

-

## 0.0.3 - 2026-08-10

### Added

- User Profile integration tests using the real API and authentication cookies.
- Cookie-aware authentication handling in integration tests.
- User Profile integration test covering the retrieval and rendering of user information.

### Updated

- Authentication tests to correctly handle the login and signup integration flows.
- User E2E and integration test setup to wait for asynchronously loaded user information.

### Notes

-

## 0.0.3 - 2026-08-09

### Added

- Authenticated test base for selenium tests, automating user authentication before tests.
- User page selenium tests checking user information renders and logging out

### Notes

-  After looking at the glitch I noticed the tokens are deleted correctly from the browser's storage which makes stranger how the API still recognizes the tokens even when deleted from the browser.

## 0.0.3 - 2026-08-04

### Updated

- Log in tests in client, taking into account redirection to user page
- User service  and user page tests in client

## 0.0.3 - 2026-08-01

### Updated

- Log in unit tests, taking into account redirection to user page after successful log in.
- Authentication service in front-end with unit tests for log out method.

### Notes

-  I don't know why in Vitest integration tests when you try to obtain credentials before the test, they are not shared, therefore when user page is rendered API sends an unauthorized response.

## 0.0.3 - 2026-07-31

### Added

- Log out button

### Updated

- User page and authentication service in web, adding the logout button.

### Notes

- There's a glitch, when an user logs out and then reloads the site, somehow the tokens persist, allowing the site to authenticate the user again without filling the log in form.

## 0.0.3 - 2026-07-30

### Added

- User page in client
- User DTO in web
- Unit and integration tests for user page and user service in client end.

### Updated

- User entity and related DTOs with study location attribute.
- Unit, integration and end to end in API, adapting them for change in user entity and DTOs.
- User service to call API for user information

### Notes

- A new attribute has been given to the user entity which allows to store the city and country the user is studying or will be studying at during the its Erasmus program. It's an optional attribute, in case it has not been filled an alternative message its displayed instead.

## 0.0.3 - 2026-07-29

### Added

- Get user by id endpoint
- Unit, integration and end to end tests for get user by id logic in API
-  No such element controller, for not found exceptions.

### Updated

- User mapper entity and unit tests.
- User service tests adding tests for new functionality
- Security configuration, allowing only authenticated users to get an user's information.

### Notes

- In order to get any user's information a method has been added to user service, this method forbids any user without administrator role to access other user's information, therefore an user can only look up its own information.
## 0.0.3 - 2026-07-28

### Added

- Home page selenium test
- About us page selenium test

### Updated

- Sign up selenium test adding a wait and checking the login page is displayed after successful sign up.

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