# 🛠️ Development Guide

## 📑 Index

1. [🏗️ Introduction](#️-introduction)
2. [🖥️ Technologies](#️-technologies)
3. [🧰 Tools](#-tools)
4. [🏛️ Architecture](#️-architecture)
5. [🛡️ Quality Assurance](#️-quality-assurance)
6. [🌐 Deployment](#-deployment)
7. [🔄 Development Process](#-development-process)
8. [🚀 Application Execution](#-application-execution)
9.  [🎮 Code Edition](#-code-edition)

---

## 🏗️ Introduction

*MyErasmusJourney* is a web application based on a **Single Page Application (SPA)** architecture. Unlike traditional multi-page websites, a SPA loads the application only once and dynamically updates its content as users navigate between pages. This approach considerably improves the user experience by providing smoother navigation, reducing loading times and avoiding full page refreshes.

The application follows a classic **client-server architecture** composed of three independent parts:

### 🎨 Frontend

The client is developed using **React** and is responsible for rendering the user interface, handling navigation and communicating with the backend through a REST API.

### ⚙️ Backend

The server is implemented with **Spring Boot** and contains the business logic, validation, authentication and communication with the database.

### 🗄️ Database

A **MySQL** relational database stores all persistent information, including users, cities, experiences, comments and multimedia files.

---

Communication between the client and server is performed through a **REST API**, while the backend accesses the database using **Spring Data JPA** repositories.

The project follows an **iterative and incremental development process**, where each new functionality is accompanied by automated tests and Continuous Integration workflows to ensure software quality throughout development.

---

### 📋 Technical Overview

| Component        | Type                | Main Technologies                        | Development Tools              | Quality Assurance                                     | Deployment                         | Development Process                                  |
| ---------------- | ------------------- | ---------------------------------------- | ------------------------------ | ----------------------------------------------------- | ---------------------------------- | ---------------------------------------------------- |
| 🎨 **Frontend**  | Web SPA             | React, TypeScript, Vite                  | Visual Studio Code             | Vitest, Testing Library, v8 Coverage                  | Local development server           | Iterative & Incremental using Git and GitHub Actions |
| ⚙️ **Backend**   | REST API            | Spring Boot, Java, Spring OpenAPI, Maven | IntelliJ IDEA                  | JUnit, Testcontainers, Rest Assured, Selenium, JaCoCo | Standalone Spring Boot application | Iterative & Incremental using Git and GitHub Actions |
| 🗄️ **Database** | Relational Database | MySQL                                    | Docker Desktop / Docker Engine | Integration tests using Testcontainers                | Docker Container                   | Version controlled together with the application     |

---

At the current stage of the project, the technical foundations have already been established, including:

- ✅ Frontend and backend projects.
- ✅ REST API documentation using OpenAPI.
- ✅ Automated testing infrastructure.
- ✅ Code coverage reports for both frontend and backend.
- ✅ Continuous Integration workflows with GitHub Actions.

These elements provide a solid foundation for implementing the remaining functionalities during the following development phases.

---

## 🖥️ Technologies

This section describes the main technologies used to build and execute the application. For each technology, a brief explanation of its role within the project is provided together with its official website.

---

### ⚛️ React

React is the frontend library used to develop the Single Page Application (SPA). It allows the user interface to be divided into reusable components that are dynamically rendered without requiring a full page reload.

Using React improves the user experience by updating only the necessary parts of the interface while keeping common elements, such as the navigation bar and footer, permanently loaded. The project uses React together with TypeScript to improve code maintainability and type safety.

**Official website:** https://react.dev/

---

### ⚡ Vite

Vite is the frontend build tool used during development. It provides a fast development server with Hot Module Replacement (HMR) and generates optimized production builds.

Its lightweight architecture considerably reduces compilation times compared to traditional bundlers, making the development process faster and more efficient.

**Official website:** https://vite.dev/

---

### ☕ Java

Java is the main programming language used to implement the backend of the application. It provides strong object-oriented programming features, platform independence through the Java Virtual Machine (JVM), and a mature ecosystem widely adopted in enterprise software development.

**Official website:** https://www.java.com/

---

### 🍃 Spring Boot

Spring Boot is the framework used to implement the backend REST API. It simplifies application configuration through dependency injection and annotation-based programming, allowing developers to focus on business logic rather than infrastructure.

Within this project it is responsible for exposing REST endpoints, implementing the business logic, accessing the database through Spring Data JPA, and managing the application's lifecycle.

**Official website:** https://spring.io/projects/spring-boot

---

### 🗄️ MySQL

MySQL is the relational database management system used to persist the application's information. It stores users, experiences, comments, cities and their relationships while ensuring data consistency through ACID transactions and referential integrity.

A relational database was selected because the project contains multiple entities with well-defined relationships and requires transactional consistency.

**Official website:** https://www.mysql.com/

---

### 📖 OpenAPI

OpenAPI is the specification used to describe and document the REST API exposed by the backend. It provides a standardized description of every endpoint, request and response, making the API easier to understand, test and integrate.

The generated documentation is published together with the project documentation and can be viewed directly from a web browser.

**Official website:** https://www.openapis.org/

---

### 📦 Maven

Apache Maven is the build automation and dependency management system used for the backend project. It automates compilation, dependency resolution, test execution, code coverage generation and packaging of the application.

Maven also provides a standardized project structure that improves maintainability and facilitates integration with GitHub Actions.

**Official website:** https://maven.apache.org/

---

## 🧰 Tools

### IntelliJ IDEA

The primary Integrated Development Environment (IDE) used for backend development. It provides advanced support for Java, Spring Boot, Maven, debugging, code analysis and automated testing.

Official website: https://www.jetbrains.com/idea/

---

### Visual Studio Code

The IDE used for frontend development. Its lightweight architecture and extensive extension ecosystem make it particularly suitable for React, TypeScript and Vite projects.

Official website: https://code.visualstudio.com/

---

### Git

Distributed version control system used to track source code changes, manage branches and maintain the project's development history.

Official website: https://git-scm.com/

---

### 🐈‍⬛ GitHub

Cloud platform used to host the repository, manage project planning through GitHub Projects, perform code reviews and automate quality checks using GitHub Actions.

Official website: https://github.com/

---

### 📦 Maven

Build automation and dependency management tool used for the backend. It handles project compilation, dependency resolution, test execution and packaging.

Official website: https://maven.apache.org/

---

### Vite

Frontend build tool used to create and serve the React application. It provides a fast development server, Hot Module Replacement (HMR) and optimized production builds.

Official website: https://vite.dev/

---

### pnpm

Package manager used for the frontend workspace. It efficiently manages dependencies through a content-addressable storage system while supporting monorepo structures.

Official website: https://pnpm.io/

---

### Docker Engine

Containerization platform used to run the MySQL database during development and to execute isolated integration tests using Testcontainers.

Official website: https://www.docker.com/products/docker-desktop/

---

## 🏛️ Architecture

### Overall Architecture

![image](./diagrams/architecture_diagram.png)

The application follows a three-layer architecture composed of a client, a REST API and a database.

The client communicates with the backend through HTTP REST requests, exchanging information in JSON format. The REST API is responsible for processing requests, applying the business logic and interacting with the persistence layer. Finally, the backend communicates with the MySQL database through TCP/IP to store and retrieve the application data.

This separation of responsibilities improves maintainability, scalability and allows each layer to evolve independently.

---

### Database Architecture

![image](./diagrams/database_diagram.png)

The database is formed by four entities City, Comment, Experience and User. As can be seen in the diagram all relationships between the entities are bidirectional. All relationships are 0:N, a city can be related to numerous experiences, just like the experiences can have N comments. Lastly an user can post as many experiences and comments as wanted.

---

### Client Architecture

![image](./diagrams/client_architecture_diagram.png)

The client is divided into two main modules: **Web** and **Shared**.

The **Web** module contains the React components responsible for rendering the user interface, handling navigation and managing the application's pages. The execution starts in the `Main` component, which initializes the `RouterProvider`. The router loads the route configuration and renders the `App` component, where common elements such as the header remain persistent while the `Outlet` dynamically displays the page associated with the current URL.

The **Shared** module contains reusable components that are independent of the user interface, including DTOs, service interfaces, stores, API communication utilities and configuration constants. This architecture reduces coupling between the presentation layer and the communication layer while allowing the same business logic to be reused by future client applications, such as the planned mobile application.

Whenever a page needs to communicate with the backend, it does so through the corresponding service, which internally uses the `ApiClient` abstraction to perform the HTTP requests.

---

### Server Architecture

![image|700](./diagrams/backend_architecture_diagram.png)

The backend follows the layered architecture recommended by Spring Boot, separating presentation, business logic and persistence responsibilities.

The execution flow begins when an HTTP request reaches one of the REST controllers. The controller validates the incoming request and delegates the operation to the corresponding service.

The service contains the application's business logic. It performs the required operations and interacts with the persistence layer through the repository interfaces. Whenever information must be transferred between the API and the domain model, MapStruct mappers are used to convert between domain entities and Data Transfer Objects (DTOs), keeping both models independent.

Repositories provide the abstraction over the database, allowing the services to retrieve or persist information without directly interacting with SQL queries.

Once the requested operation has been completed, the resulting DTO is returned to the controller, which generates the corresponding HTTP response and sends it back to the client.

The current REST endpoints and the DTOs exchanged by the API can be consulted in the generated OpenAPI documentation:

**API Documentation:**  
https://raw.githack.com/codeurjc-students/2026-MyErasmusJourney/main/docs/api/api-docs.html

---

## 🛡️ Quality Assurance

Software quality has been considered a fundamental aspect of the project since the beginning of the implementation phase. Every new functionality is accompanied by automated tests and validated through a Continuous Integration (CI) pipeline before being integrated into the main branch.

The quality assurance strategy combines automated testing, code coverage analysis and continuous integration, allowing regressions to be detected early and ensuring that every software increment remains stable throughout the iterative development process.

---

### 🎯 Quality Objectives

The quality assurance process pursues the following objectives:

- Verify that every implemented functionality behaves as expected.
- Detect regressions automatically after every modification.
- Ensure communication between all application layers.
- Measure the percentage of source code executed by automated tests.
- Guarantee that every contribution passes the same validation process through GitHub Actions.

---

### 🧪 Automated Testing

The project includes automated tests covering every layer of the application.

| Layer | Test Type | Tool |
|--------|-----------|------|
| Backend | Unit Tests | JUnit + Mockito |
| Backend | Integration Tests | JUnit + Testcontainers |
| Backend | REST API Tests | Rest Assured |
| Frontend | Unit Tests | Vitest |
| Frontend | Integration Tests | Vitest + Testing Library |
| Frontend | System Tests | Selenium WebDriver |

---

### 🔹 Backend Tests

#### Unit Tests

Backend unit tests verify the behaviour of the service layer in isolation by mocking repository dependencies.

These tests validate:

- Business logic.
- Mapper behaviour.
- Exception handling.
- Returned DTOs.
- Repository interaction.

**Implemented test classes**

- ExperienceMapperTest
- ExperienceRestControllerTest
- ExperienceServiceTest
- ExperienceSimpleDTOTest
- ExperienceTest

---

#### Integration Tests

Integration tests verify the interaction between the application and the database.

A temporary MySQL container is automatically created using Testcontainers, ensuring that every test starts from a clean database state.

The integration tests validate:

- CRUD operations.
- Entity persistence.
- Repository behaviour.
- Mapper integration.
- Transaction consistency.

---

### REST API Tests

REST API tests validate the public endpoints exposed by the application.

These tests verify:

- HTTP status codes.
- Returned JSON structure.
- Request validation.
- Endpoint behaviour.
- Error responses.

---

### 🎨 Frontend Tests

#### Unit Tests

Frontend unit tests validate isolated React components and utility functions.

The current tests verify:

- Component rendering.
- Conditional rendering.
- User interaction.
- Routing behaviour.
- Service logic.

---

#### Integration Tests

Frontend integration tests verify the interaction between multiple components.

Examples include:

- Navigation between pages.
- Data loading.
- Communication with mocked services.
- Page rendering after API responses.

---

#### System Tests

System tests validate the complete application from the user's perspective.

The backend and frontend are automatically started before executing the Selenium tests.

These tests verify complete user workflows such as:

- Opening the application.
- Navigating through pages.
- Loading experiences.
- Displaying information returned by the REST API.

---

### 📋 Functional Traceability

The following table shows the relationship between the implemented automated tests and the functional requirements defined during the analysis phase.

| Functional Requirement   | Tested | Test Type   | Test Class                   | Layer  |
| ------------------------ | :----: | ----------- | ---------------------------- | ------ |
| Showing Experiences      |   ✅    | Unit        | ExperienceMapperTest         | Server |
| Showing Experiences      |   ✅    | Unit        | ExperienceRestControllerTest | Server |
| Showing Experiences      |   ✅    | Unit        | ExperienceServiceTest        | Server |
| Showing Experiences      |   ✅    | Unit        | ExperienceSimpleDTOTest      | Server |
| Showing Experiences      |   ✅    | Unit        | ExperienceTest               | Server |
| Showing Experiences      |   ✅    | Integration | ExperienceServiceTest        | Server |
| Showing Experiences      |   ✅    | E2E         | ExperiencesTest              | Server |
| Showing Experiences      |   ✅    | System      | ExperiencesPageTest          | Server |
| Showing Experiences      |   ✅    | Unit        | Experience.service.test      | Client |
| Showing Experiences      |   ✅    | Unit        | Experience.test              | Client |
| Showing Experiences      |   ✅    | Unit        | ExperiencesPage.test         | Client |
| Showing Experiences      |   ✅    | Integration | Experiences.test             | Client |
| Post Experience          |   ✅    | Unit        | ExperienceMapperTest         | Server |
| Post Experience          |   ✅    | Unit        | ExperienceServiceTest        | Server |
| Post Experience          |   ✅    | Unit        | ExperienceDTOTest            | Server |
| Post Experience          |   ✅    | Unit        | ExperienceTest               | Server |
| Post Experience          |   ✅    | Integration | ExperienceServiceTest        | Server |
| Post Experience          |   ✅    | E2E         | ExperiencesTest              | Server |
| Post Experience          |   ✅    | System      | ExperienceFormPageTest       | Server |
| Post Experience          |   ✅    | Unit        | Experience.service.test      | Client |
| Post Experience          |   ✅    | Unit        | ExperienceFormPage.test      | Client |
| Post Experience          |   ✅    | Integration | ExperiencesForm.test         | Client |
| Post Comment             |   ✅    | Unit        | CommentMapperTest            | Server |
| Post Comment             |   ✅    | Unit        | ExperienceServiceTest        | Server |
| Post Comment             |   ✅    | Unit        | CommentDTOTest               | Server |
| Post Comment             |   ✅    | Unit        | CommentTest                  | Server |
| Post Comment             |   ✅    | Unit        | ExperienceTest               | Server |
| Post Comment             |   ✅    | Integration | ExperienceServiceTest        | Server |
| Post Comment             |   ✅    | E2E         | ExperiencesTest              | Server |
| Post Comment             |   ✅    | System      | ExperiencesTest              | Server |
| Post Comment             |   ✅    | Unit        | Comment.service.test         | Client |
| Post Comment             |   ✅    | Unit        | Comments.test                | Client |
| Post Comment             |   ✅    | Integration | Comments.test                | Client |
| View detailed Experience |   ✅    | Unit        | ExperienceMapperTest         | Server |
| View detailed Experience |   ✅    | Unit        | ExperienceServiceTest        | Server |
| View detailed Experience |   ✅    | Unit        | ExperienceDTOTest            | Server |
| View detailed Experience |   ✅    | Unit        | ExperienceTest               | Server |
| View detailed Experience |   ✅    | Integration | ExperienceServiceTest        | Server |
| View detailed Experience |   ✅    | E2E         | ExperiencesTest              | Server |
| View detailed Experience |   ✅    | System      | ExperiencesTest              | Server |
| View detailed Experience |   ✅    | Unit        | Experience.service.test      | Client |
| View detailed Experience |   ✅    | Unit        | DetailedExperiencePage.test  | Client |
| View detailed Experience |   ✅    | Integration | DetailedExperience.test      | Client |
| SignUp                   |   ✅    | Unit        | UserMapperTest               | Server |
| SignUp                   |   ✅    | Unit        | UserServiceTest              | Server |
| SignUp                   |   ✅    | Unit        | UserDTOTest                  | Server |
| SignUp                   |   ✅    | Unit        | UserTest                     | Server |
| SignUp                   |   ✅    | Integration | UserServiceTest              | Server |
| SignUp                   |   ✅    | E2E         | UsersTest                    | Server |
| SignUp                   |   ✅    | System      | UsersTest                    | Server |
| SignUp                   |   ✅    | Unit        | User.service.test            | Client |
| SignUp                   |   ✅    | Unit        | UserFormPage.test            | Client |
| SignUp                   |   ✅    | Integration | SignUp.test                  | Client |
| Edit User                |   ✅    | Unit        | UserMapperTest               | Server |
| Edit User                |   ✅    | Unit        | UserServiceTest              | Server |
| Edit User                |   ✅    | Unit        | UserDTOTest                  | Server |
| Edit User                |   ✅    | Unit        | UserTest                     | Server |
| Edit User                |   ✅    | Integration | UserServiceTest              | Server |
| Edit User                |   ✅    | E2E         | UsersTest                    | Server |
| Edit User                |   ✅    | System      | UsersTest                    | Server |
| Edit User                |   ✅    | Unit        | User.service.test            | Client |
| Edit User                |   ✅    | Unit        | UserFormPage.test            | Client |
| Edit User                |   ✅    | Integration | UserForm.test                | Client |
| Log In                   |   ✅    | Unit        | Auth.service.test            | Client |
| Log In                   |   ✅    | Unit        | LogInPage.test               | Client |
| Log In                   |   ✅    | Integration | LogIn.test                   | Client |
| Log Out                  |   ✅    | Unit        | Auth.service.test            | Client |
| Log Out                  |   ✅    | Unit        | UserPage.test                | Client |
| Log Out                  |   ✅    | Integration | Users.test                   | Client |
| Add City                 |   ✅    | Unit        | CityMapperTest               | Server |
| Add City                 |   ✅    | Unit        | CityServiceTest              | Server |
| Add City                 |   ✅    | Unit        | CityDTOTest                  | Server |
| Add City                 |   ✅    | Unit        | CityTest                     | Server |
| Add City                 |   ✅    | Integration | CityServiceTest              | Server |
| Add City                 |   ✅    | E2E         | CitiesTest                   | Server |
| Add City                 |   ✅    | System      | CitiesTest                   | Server |
| Add City                 |   ✅    | Unit        | City.service.test            | Client |
| Add City                 |   ✅    | Unit        | CityFormPage.test            | Client |
| Add City                 |   ✅    | Integration | CityForm.test                | Client |
| Get Cities               |   ✅    | Unit        | CityMapperTest               | Server |
| Get Cities               |   ✅    | Unit        | CityServiceTest              | Server |
| Get Cities               |   ✅    | Unit        | CitySimpleDTOTest            | Server |
| Get Cities               |   ✅    | Unit        | CityTest                     | Server |
| Get Cities               |   ✅    | Integration | CityServiceTest              | Server |
| Get Cities               |   ✅    | E2E         | CitiesTest                   | Server |
| Get Cities               |   ✅    | System      | CitiesTest                   | Server |
| Get Cities               |   ✅    | Unit        | City.service.test            | Client |
| Get Cities               |   ✅    | Unit        | ExperienceFormPage.test      | Client |
| Get Cities               |   ✅    | Integration | ExperienceForm.test          | Client |
| Show user's experiences  |   ✅    | Unit        | ExperienceMapperTest         | Server |
| Show user's experiences  |   ✅    | Unit        | UserServiceTest              | Server |
| Show user's experiences  |   ✅    | Unit        | ExperienceSimpleDTOTest      | Server |
| Show user's experiences  |   ✅    | Unit        | UserTest                     | Server |
| Show user's experiences  |   ✅    | Unit        | ExperienceTest               | Server |
| Show user's experiences  |   ✅    | Integration | UserServiceTest              | Server |
| Show user's experiences  |   ✅    | E2E         | UsersTest                    | Server |
| Show user's experiences  |   ✅    | System      | UsersTest                    | Server |
| Show user's experiences  |   ✅    | Unit        | User.service.test            | Client |
| Show user's experiences  |   ✅    | Unit        | UserPage.test                | Client |
| Show user's experiences  |   ✅    | Integration | User.test                    | Client |
| Show user's comments     |   ✅    | Unit        | CommentMapperTest            | Server |
| Show user's comments     |   ✅    | Unit        | UserServiceTest              | Server |
| Show user's comments     |   ✅    | Unit        | CommentSimpleDTOTest         | Server |
| Show user's comments     |   ✅    | Unit        | UserTest                     | Server |
| Show user's comments     |   ✅    | Unit        | CommentTest                  | Server |
| Show user's comments     |   ✅    | Integration | UserServiceTest              | Server |
| Show user's comments     |   ✅    | E2E         | UsersTest                    | Server |
| Show user's comments     |   ✅    | System      | UsersTest                    | Server |
| Show user's comments     |   ✅    | Unit        | User.service.test            | Client |
| Show user's comments     |   ✅    | Unit        | UserPage.test                | Client |
| Show user's comments     |   ✅    | Integration | User.test                    | Client |
| Delete Experience        |   ✅    | Unit        | ExperienceMapperTest         | Server |
| Delete Experience        |   ✅    | Unit        | ExperienceServiceTest        | Server |
| Delete Experience        |   ✅    | Unit        | ExperienceDTOTest            | Server |
| Delete Experience        |   ✅    | Unit        | ExperienceTest               | Server |
| Delete Experience        |   ✅    | Integration | ExperienceServiceTest        | Server |
| Delete Experience        |   ✅    | E2E         | ExperiencesTest              | Server |
| Delete Experience        |   ✅    | System      | ExperiencesTest              | Server |
| Delete Experience        |   ✅    | Unit        | Experience.service.test      | Client |
| Delete Experience        |   ✅    | Unit        | UserPage.test                | Client |
| Delete Experience        |   ✅    | Integration | User.test                    | Client |
| Delete Comment           |   ✅    | Unit        | CommentMapperTest            | Server |
| Delete Comment           |   ✅    | Unit        | CommentServiceTest           | Server |
| Delete Comment           |   ✅    | Unit        | CommentDTOTest               | Server |
| Delete Comment           |   ✅    | Unit        | CommentTest                  | Server |
| Delete Comment           |   ✅    | Integration | CommentServiceTest           | Server |
| Delete Comment           |   ✅    | E2E         | CommentsTest                 | Server |
| Delete Comment           |   ✅    | System      | CommentsTest                 | Server |
| Delete Comment           |   ✅    | Unit        | Comment.service.test         | Client |
| Delete Comment           |   ✅    | Unit        | UserPage.test                | Client |
| Delete Comment           |   ✅    | Integration | User.test                    | Client |
| Delete User              |   ✅    | Unit        | UserMapperTest               | Server |
| Delete Comment           |   ✅    | Unit        | UserServiceTest              | Server |
| Delete Comment           |   ✅    | Unit        | UserDTOTest                  | Server |
| Delete Comment           |   ✅    | Unit        | UserTest                     | Server |
| Delete Comment           |   ✅    | Integration | UserServiceTest              | Server |
| Delete Comment           |   ✅    | E2E         | UsersTest                    | Server |
| Delete Comment           |   ✅    | System      | UsersTest                    | Server |
| Delete Comment           |   ✅    | Unit        | User.service.test            | Client |
| Delete Comment           |   ✅    | Unit        | UserPage.test                | Client |
| Delete Comment           |   ✅    | Integration | User.test                    | Client |

---

### 📊 Test Statistics

The current automated test suite includes:

| Metric | Backend | Frontend |
|---------|---------|----------|
| Unit Tests | XX | XX |
| Integration Tests | XX | XX |
| System Tests | XX | — |
| REST API Tests | XX | — |
| Total Tests | XX | XX |

---

### 📈 Code Coverage

Code coverage is measured independently for both application layers.

| Layer | Tool |
|--------|------|
| Backend | JaCoCo |
| Frontend | Istanbul |

#### Backend Coverage

![Backend Coverage](./images/backend_coverage.png)

Coverage summary:

- Line coverage: 90 %
- Branch coverage: 72 %
- Classes covered: 49 / 49

---

#### Frontend Coverage

![Frontend Coverage](./images/web_coverage.png)

Coverage summary:

- Statements: 94.48 %
- Branches: 85.78 %
- Functions: 92 %
- Lines: 95.4 %

![Frontend Coverage](./images/shared_coverage.png)

Coverage summary:

- Statements: 100 %
- Branches: 92 %
- Functions: 100 %
- Lines: 100 %

---

### 📏 Code Metrics

The current size of the project is summarised below.

| Technology | Files | Classes / Components | Lines of Code |
| ---------- | ----: | -------------------: | ------------: |
| Java       |    18 |                   18 |           644 |
| TypeScript |    20 |                   18 |           405 |
| Markdown   |    17 |                    0 |           896 |
| YAML       |     6 |                    0 |          4226 |
| HTML       |     2 |                    0 |           321 |
| CSS        |     2 |                    0 |           258 |
| Javascript |     1 |                    0 |            21 |

---

### ✅ Quality Summary

The combination of automated testing, code coverage analysis and continuous integration provides a robust quality assurance process that detects defects early, reduces regressions and guarantees that every iteration of the project maintains a stable and deployable state.

---


## 🌐 Deployment

The application is packaged and distributed using Docker and Docker Compose. The backend and frontend are packaged together into a single Docker image, while Docker Compose is used to coordinate the application container and its MySQL database.

The Docker Compose configuration defines the services required to run the application, including:

- **Application:** contains both the React frontend and Spring Boot backend and exposes the application through port `8443`.
- **MySQL database:** provides the persistent database required by the backend.
- **Persistent volume:** stores the MySQL data so that the database contents are preserved when the application containers are restarted.

The resulting Docker image is published to Docker Hub and is used by the Docker Compose artifact for distributing the application. Different versions of the application can be identified through their corresponding Docker tags.

The application Docker image and its published artifacts are available at:

	https://hub.docker.com/r/granlobo2004/myerasmusjourney-app

Docker Compose is therefore the main mechanism used to package the complete application environment and facilitate its distribution and deployment on machines with Docker installed.

## 🔄 Development Process

The project follows an **Iterative and Incremental Development** methodology.

Instead of implementing the entire application at once, development is divided into small iterations where each functionality is designed, implemented, tested and integrated before moving to the next one.

The development workflow follows these steps:

1. A new task is created in the GitHub Project board.
2. A dedicated feature branch is created following the GitHub Flow strategy.
3. The functionality is implemented together with its corresponding automated tests.
4. Local quality checks are executed.
5. GitHub Actions automatically validates the changes.
6. A new Docker Compose is published with the **"dev"** tag.
7. The branch is merged into the main branch after all quality checks have passed.

This workflow ensures that every increment of the application remains functional and that software quality is maintained throughout the project.

### 🌿 Git Strategy

The project follows the **GitHub Flow** branching model.

- `main` always contains the latest stable version.
- New functionalities are implemented in feature branches.
- Bug fixes are implemented in dedicated fix branches.
- Changes are merged through Pull Requests after passing all automated quality controls.

### 📋 Project Management

Project planning and task tracking are performed using **GitHub Projects** following a Kanban workflow.

Tasks are organised according to their current state:

- Backlog
- To Do
- In Progress
- Review
- Done

This allows the development progress to be monitored throughout every phase of the project.

### 🚀 Continuous Integration

Every commit pushed to the repository is automatically validated through GitHub Actions.

The CI workflow performs the following checks:

- Backend compilation.
- Frontend compilation.
- Backend automated tests.
- Frontend automated tests.
- Selenium system tests.
- Code coverage generation.

Only after all quality controls have passed can the changes be merged into the main branch.

### 🗞️ Continuous Deployment

The application uses GitHub Actions to automate the publication of Docker Compose artifacts to Docker Hub. Three different workflows are available, each intended for a different stage of the development and release process. 
#### 📦 Release Deployment 

The release deployment workflow is triggered automatically whenever a new GitHub Release is created. The workflow: 
1. Obtains the version from the GitHub Release tag. 
2. Builds the application Docker image. 
3. Publishes the Docker Compose artifact to Docker Hub using the same version as its tag.

For example, creating a release with the tag `0.1.0` results in the following Docker Compose artifact being published: 

```text granlobo2004/myerasmusjourney:0.1.0```

This allows each released version to be published with a stable and identifiable tag.

#### 🔧 Development Deployment

The development deployment workflow is triggered automatically whenever a Pull Request targeting the `main` branch is created or updated.

The workflow builds and publishes the current state of the Pull Request as a Docker Compose artifact using the `dev` tag:

```
granlobo2004/myerasmusjourney:dev
```

The `dev` tag is overwritten whenever a new deployment is generated, so it always represents the most recently published development version.

This deployment can therefore be used to test the changes proposed in Pull Requests before they are incorporated into the `main` branch.

#### 🧪 Manual Deployment

A third workflow can be triggered manually by a developer whenever a temporary or specific version of the application needs to be published.

The generated Docker Compose artifact uses a tag containing:

- The branch name.
- The hash of the latest commit.
- The date and time of the deployment.

The resulting tag follows this format:

```
<branch>-<last-commit>-<date-time>
```

For example:

```
feature-new-profile-a1b2c3d-20260920-155200
```

This makes each manually generated deployment uniquely identifiable and allows developers to distinguish different deployments without overwriting previously published artifacts.

#### 🔄 Deployment Workflow Summary

| Workflow               | Trigger                           | Docker Compose Tag              | Purpose                                             |
| ---------------------- | --------------------------------- | ------------------------------- | --------------------------------------------------- |
| Release Deployment     | GitHub Release                    | Release version                 | Publish a stable version                            |
| Development Deployment | Pull Request targeting `main`     | `dev`                           | Publish the latest Pull Request version             |
| Manual Deployment      | Manually triggered by a developer | `<branch>-<commit>-<date-time>` | Publish a uniquely identifiable development version |
#### CI Pipeline

![GitHub Actions](./images/github_actions.png)

---

## 🚀 Application Execution 

### Requirements

To run the published version of _MyErasmusJourney_, Docker must be installed and running on the host machine.

#### Windows and macOS

On Windows and macOS, **Docker Desktop** is required. Docker Desktop includes Docker Engine, Docker CLI and Docker Compose, so no additional Docker Compose installation is necessary.

- [Docker Desktop for Windows](https://docs.docker.com/desktop/setup/install/windows-install/)
    
- [Docker Desktop for Mac](https://docs.docker.com/desktop/setup/install/mac-install/)
    

#### Linux

On Linux, **Docker Engine** and the **Docker Compose plugin** are required.

- [Docker Engine installation](https://docs.docker.com/engine/install/)
    
- [Docker Compose installation](https://docs.docker.com/compose/install/linux/)
    

The Docker Compose OCI artifact functionality requires Docker Compose 2.34.0 or later.

The installation can be verified with:

```bash
docker --version
docker compose version
```

---

### 📦 Published Docker Compose Application

The application is distributed using **Docker Compose as an OCI artifact** published on Docker Hub. This allows the complete Compose configuration to be downloaded directly from the container registry without requiring the user to clone the source repository.

The published application is available in the following Docker Hub repository:

[MyErasmusJourney on Docker Hub](https://hub.docker.com/repository/docker/granlobo2004/myerasmusjourney-app)

Docker Compose supports OCI artifacts through the `oci://` prefix, allowing a published Compose application to be used directly with the `docker compose` command.

---

### ▶️ Running the Latest Available Version

The latest published version can be executed directly from the Docker Hub OCI artifact using:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest up
```

This command downloads the published Compose configuration and starts the application and MySQL database containers.

The default configuration is used automatically, so no additional environment variables are required for a standard execution.

The application will be available at:

```text
https://localhost:8443
```

Because Version 0.1 uses a self-signed certificate for HTTPS, the browser may display a security warning when accessing the application locally. This is expected for the development/demo certificate.

To run the containers in the background instead, use:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest up -d
```

To stop the application:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest down
```

---

### 🏷️ Running a Specific Version

A specific published version can be executed by replacing the `latest` tag with the desired version.

For example, to run Version 0.1.0:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:0.1.0 up
```

Or, to run it in the background:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:0.1.0 up -d
```

Using a version-specific tag makes it possible to reproduce the exact published version of the application instead of using whichever version is currently associated with `latest`.

---

### ⚙️ Default Configuration

The published Compose application provides default values for the configuration variables, allowing the application to be started without creating an additional `.env` file.

The main default values are:

|Variable|Default value|
|:--|:--|
|`DB_URL`|`jdbc:mysql://db:3306/myerasmusjourney`|
|`DB_USERNAME`|`root`|
|`DB_PASSWORD`|`password`|
|`DB_MODE`|`update`|
|`PORT`|`8443`|
|`SSL_ENABLED`|`true`|
|`KEY_STORE`|`example.jks`|
|`KEY_PASSWORD`|`password`|
|`ADMIN_EMAIL`|`admin@example.com`|
|`ADMIN_PASSWORD`|`password`|
|`SPRING_PROFILES_ACTIVE`|`default`|

The application container is exposed through port `8443` and the MySQL container uses a persistent Docker volume named `db_data`.

The Compose configuration also includes a MySQL health check. The application container waits for the database service to become healthy before starting.

---

### 👤 Accessing the Application

Once the containers have started, open the following URL in a web browser:

```text
https://localhost:8443
```

The default administrator account is:

|Field|Value|
|:--|:--|
|Email|`admin@example.com`|
|Password|`password`|

This account can be used to access the administrator functionality of Version 0.1.

For security reasons, these credentials are intended only for the local/demo execution of the application and should be changed before using the application in a real deployment.

---


### 🧪 Example Data

The application includes a set of example data that can be loaded to facilitate the demonstration and testing of the main functionalities of Version 0.1.

To activate the example data, the Spring profile must be set to `test` through the `SPRING_PROFILES_ACTIVE` environment variable.

For example:

``bash SPRING_PROFILES_ACTIVE=test \ docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest up -d

#### 👤 Example Users

|Email|Nickname|Full name|Destination|Password|
|---|---|---|---|---|
|`test@email.com`|`test`|`testUser`|—|`password`|
|`exampleuser1@email.com`|`Daniel`|`Daniel Grimm`|Paris, France|`password`|
|`exampleuser2@email.com`|`Maria`|`Maria Garcia`|Rome, Italy|`password`|
|`exampleuser3@email.com`|`Max`|`Max Helmut`|Copenhagen, Denmark|`password`|

The `test@email.com` account is an additional test user without a destination. The other three users represent students associated with the example destinations.

#### 🌍 Example Cities

The following cities are included in the example data:

|City|Description|Country|
|---|---|---|
|Copenhagen|Capital of Denmark|Denmark|
|Paris|Capital of France|France|
|Rome|Capital of Italy|Italy|

#### 📝 Example Experiences

Three example experiences are created, one for each example city:

|Title|Rating|Categories|City|
|---|---|---|---|
|Example experience 1|5.4|Studies, Documentation|Copenhagen|
|Example experience 2|9.0|Studies, Documentation|Paris|
|Example experience 3|3.1|Studies, Documentation|Rome|

Each experience contains a sample description:

> Long description about an amazing adventure or small story

The dates of the example experiences are generated using the current date when the example data is initialized.

#### 💬 Example Comments

Three example comments are also created. Each comment contains the following text:

> My opinion or point of view regarding the experience

The comments are associated with the example experiences as follows:

|Comment author|Experience|
|---|---|
|Max Helmut|Example experience 1|
|Maria Garcia|Example experience 3|
|Daniel Grimm|Example experience 2|

#### 🔄 Running with Example Data

The `test` Spring profile must be explicitly enabled when the example data is required. For example:

```
SPRING_PROFILES_ACTIVE=test \
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:0.1.0 up -d
```

If the profile is not set to `test`, the example data is not loaded by this initialization process.

The administrator account is independent from these example users and is created using the administrator configuration described in the Accessing the Application section.

---

### 💾 Persistent Database

The MySQL database is configured with a named Docker volume:

```text
db_data
```

This volume persists the database contents when the application containers are stopped or recreated.

Therefore, running:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest down
```

does not remove the database volume or its stored data.

The data can be preserved and reused by starting the application again:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest up -d
```

This is the recommended procedure when the existing database contents must be preserved.

---

### 🆕 Starting with a Fresh Database

To execute the application again using a clean database and therefore recreate the initial example data, the existing Docker volume must be removed.

First, stop the application:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest down
```

Then remove the database volume:

```bash
docker volume rm <project>_db_data
```

Alternatively, the volumes associated with the Compose application can be removed directly with:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest down -v
```

The application can then be started again:

```bash
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest up -d
```

This creates a new MySQL database using the default configuration.

> **Warning:** Removing the volume permanently deletes the data stored in the Docker database volume. This option should only be used when a fresh database is desired.

---

### 🔧 Custom Configuration

Although the application can be executed using the default configuration, the main configuration values can be overridden through environment variables.

For example:

```bash
DB_PASSWORD=mysecurepassword \
ADMIN_EMAIL=myadmin@example.com \
ADMIN_PASSWORD=myadminpassword \
docker compose -f oci://docker.io/granlobo2004/myerasmusjourney:latest up -d
```

The same mechanism can be used to configure the database connection, HTTPS settings, application port and other environment-dependent values defined in the Compose file.

This allows the published application to be reused with different configurations without modifying the published OCI artifact.

---

### ☁️ Deployment on an External Server or Cloud Provider

Version 0.1 provides the application as a Docker Compose OCI artifact so that it can be executed on any machine with a compatible Docker installation.

A deployment on an external server or cloud provider is planned for a later phase of the project. According to the project development plan, the external deployment will be carried out as part of **Version 0.2 — Intermediate Functionality and Deployment**.

Therefore, Version 0.1 does not include a production deployment on an external server or cloud provider.

When the external deployment is implemented, this section will be updated with:

- The selected hosting or cloud provider.
    
- Server or platform configuration.
    
- Network and firewall configuration.
    
- Application deployment procedure.
    
- Database persistence configuration.
    
- HTTPS certificate configuration.
    
- Access URL.
    
- Any additional deployment or Continuous Delivery configuration.

##  🎮 Code Edition

This section explains how to prepare the development environment, clone the repository and execute the complete application locally.

The process consists of four steps:

1. Install the required software.
2. Clone the repository.
3. Configure the environment variables.
4. Start the database, backend and frontend.

### 📋 Requirements

In order to clone, execute and download the repository, the following technologies and tools must be downloaded.
- Tools:
	- Intellij IDEA
	- Maven
	- Git
	- Pnpm
	- Visual Studio Code
	- Docker Engine
- Technologies:
	- Java
	- Node js

---

### 📥 Cloning the repository

After all the requirements have been installed you may access the repository URL and obtain the URL to clone it by pressing the green button with the word "Code". After that you will have to copy the URL you have obtained and run it on a terminal after the command `git clone`, the following line is an example of how the command should look.

`git clone https://github.com/codeurjc-students/2026-MyErasmusJourney.git` 
	or 
`git clone git@github.com:codeurjc-students/2026-MyErasmusJourney.git`
	if you are using SSH

The terminal should display a text very similar to the one in the image.

![image](./images/cloning_repository.png)

---

### 🗄️ Starting the database

First of all you need to start the database in a Docker container. If it is the first time, you will have to run the following command and fill the name of the database and root password.

`docker run -e MYSQL_ROOT_PASSWORD=Password -e MYSQL_DATABASE=DatabaseName -p 3306:3306 -d mysql:9.3`

In case it's not the first time you will have to run `docker start containerName` the container name is unique in every computer so you will have to know your container's name.

---

### ⚙️ Running the backend

Once the database is operational you will have to open either Intellij IDEA or the terminal in the backend folder.

- With Intellij IDEA:
	First of all, you need to install the .env files plugin
	
	![image](./images/plugin.png)
	
	Click the open button on the top of the window and open the backend folder of the project. After opening the project wait for the IDE complete indexation and create a file name `.env` in the root of the backend folder with the following information. The database name and password must be exactly the same as you filled them in the database command when you first runned it, the port can be changed to any port that is free in your computer.
	
	![image](./images/env.png)
	
	After filling the environment variables you will have to create a run configuration by clicking the three dots and the edit option on the top menu.
	
	![image](./images/create_configuration.png)
	
	Add a new Springboot configuration with the add option on the top-left corner of the  window.
	![image](./images/add_configuration.png)  
	
	Afterwards, select the `Modify options` and select the EnvFile option to enable the environment variables file. 
	
	
	![image](./images/configuration_options.png)
	
	Lastly, you'll have to select the BackendApplication file as the main class of the run configuration and java 21 and add the env file to the table of environment variables. The image below shows the configuration after all these steps.
	
	![image](./images/configuration.png)
	
	Finally, hit apply and run with the database running on the background.

- Without  IDE:
	Open the terminal and export all the environment variables present on the env file image with the following command:
	
		export variable=value
	
	Afterwards you just need to run the command:
	
		`mvn spring-boot:run`

---

### 🎨 Running the frontend

After the server is working you may begin to execute the client side. Begin by opening a terminal on the frontend folder and installing the dependencies with `pnpm install`. 

![image](./images/pnpm_installation.png)

Just like with the server you will have to create a .env file with the URL of the API as an environment variable. It is quite important to set the right port otherwise no data will be shown. In the example image the port is 8080.

![image](./images/env_frontend.png)

Only after all installation is done you may run the command `pnpm --filter web run dev`, shortly after the website will be running. You may access the website from your computer by opening any web browser and entering the link that appears on the terminal.

![image](./images/web_url.png)

---

### 🧰 Tools and API Interaction

In order to test the API or try any of the example requests you need to install Postman. Preferably in Visual Studio Code.

![image](./images/postman_plugin.png)

Once installed you'll need to create an account or login and import the collection located in the API folder of this repository inside the documentation folder. Finally for the requests to work you need to start the database and the server, create an environment and set a variable called APIURL, the value must be almost identical to the one below, changing the port 8080 to whatever port you used to start up the server.

![image](./images/env_postman.png)

With the environment set, you may choose any of the requests and click the send button to test the API and check both the request's body and API's response.

![image](./images/request_example.png)

---

### 🧪 Executing the automated tests

For the backend tests, docker needs to be running. After checking docker is running the command `mvn test` or `mvn clean test` needs to be executed on a terminal opened in the backend folder.

As for the frontend tests you'll need to be executing the database and backend before starting the tests. Afterwards you'll need to install the dependencies, only if it is first time, with the command `pnpm install`on a terminal in the frontend folder. Lastly you may run all the web tests with the command `pnpm --filter web run test`and `pnpm --filter shared run test`for the shared folder tests.

---

🏠 [Home](../README.md) | 📚 Documentation
---