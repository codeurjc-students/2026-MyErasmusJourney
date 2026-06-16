# Methodology

This document describes the development methodology followed in the project *MyErasmusJourney*. The development process is structured in multiple phases following an iterative and incremental approach, aligned with agile principles and DevOps practices.

---

## 🧭 Development Approach

The project follows an **iterative and incremental development model**, where functionality is added progressively in different versions of the application.

Each iteration improves the system by adding new features, refining architecture, and increasing system quality through testing and automation.

---

## 🧱 Phase 1 – Functional Definition and Design

- Definition of all system functionalities
- Study of similar applications (state of the art)
- Definition of system entities and relationships
- Design of user roles and permissions
- Design of UI wireframes (mockups)
- Initial documentation setup (README and docs structure)

---

## ⚙️ Phase 2 – Repository, CI and Basic Setup

This phase establishes the technical foundation of the project.

### Main tasks:
- GitHub repository setup
- Backend project initialization (Spring Boot or equivalent)
- Frontend project initialization (React or equivalent)
- Basic integration between frontend and backend
- Implementation of a minimal functional endpoint
- First unit tests implementation
- CI pipeline setup using GitHub Actions

### Minimal functionality:
- Display of sample data from the main entity (City / Experience)
- No authentication system
- No images or advanced UI
- Simple plain HTML display
- No entity relationships

---

## 🔁 Phase 3 – MVP (Version 0.1)

- Implementation of core functionalities
- User authentication system
- Basic CRUD operations for main entities
- Image handling stored in database
- REST API fully defined
- Docker containerization
- Initial deployment version (0.1.0)
- First full CI/CD pipeline integration

---

## 🚀 Phase 4 – Intermediate Features (Version 0.2)

- Advanced filtering and search features
- Maps integration for cities and experiences
- User interaction improvements
- Deployment in external environment
- HTTPS configuration
- Continuous delivery pipeline improvements

---

## 🌟 Phase 5 – Advanced Features (Version 1.0)

- Advanced recommendation/ranking algorithms
- Trending destinations system
- Database migrations (Flyway or Liquibase)
- Dependency updates and maintenance
- Final production-ready version

---

## 📦 Phase 6 – Documentation (Thesis)

- Writing of the final TFG report
- Integration of system documentation
- Inclusion of architecture diagrams
- API documentation (OpenAPI)
- Final project analysis

---

## 🎓 Phase 7 – Defense Preparation

- Preparation of presentation slides
- Video demonstration of the system
- Final review of documentation
- Submission and defense of the project

---

## 🔄 Development Practices

### 🔧 Version Control
- Git used as version control system
- Feature-based branching strategy
- Pull requests for integration into main branch

### ⚙️ Continuous Integration
- GitHub Actions used for CI
- Automatic execution of:
  - Backend tests
  - Frontend tests
  - Build verification

### 📊 Quality Assurance
- Unit tests
- Integration tests
- End-to-end tests (future phases)
- Code coverage monitoring (minimum 70%)

---

## 📌 Summary

The development process of *MyErasmusJourney* follows a structured and incremental methodology, starting from system design and progressing towards a fully functional production-ready web application with CI/CD, testing, and deployment pipelines.