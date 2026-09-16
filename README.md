# MyErasmusJourney: A web application for sharing Erasmus experiences

![image](/logo.png)

## What is *MyErasmusJourney*?

*MyErasmusJourney* is a web application where students can share their Erasmus experiences, describing problems they faced, useful documentation, and memorable social events during their stay abroad.

It also serves as a discovery platform for future Erasmus students, allowing them to explore destinations, read real experiences, and make informed decisions about their future mobility program.

In **Version 0.1**, the application provides the basic functionality required for the main user roles. Unauthenticated users can create an account and log in, authenticated users can manage their own account, publish experiences and interact with posts through comments, and administrators can manage users, experiences, comments and cities. The application is also packaged and published using Docker and Docker Compose.

---

## 📸 Version 0.1

The following screenshots show some of the main functionalities available in Version 0.1.

![image](/docs/images/basic_functionalities/experiences.png)

![image](/docs/images/basic_functionalities/signUp.png)

![image](/docs/images/basic_functionalities/logIn.png)

![image](/docs/images/basic_functionalities/user.png)

![image](/docs/images/basic_functionalities/addExperience.png)

![image](/docs/images/basic_functionalities/comment.png)

This project is still in development, more functionalities will be added and existing ones may be subjected to changes in order to improve the application.

---

## 🎥 Version 0.1 Demo

The following video presents the main functionalities implemented in Version 0.1. The demonstration is divided according to the different types of users and includes a voice-over explaining the functionality shown.

![video](./videos/functionalities.webm)

Future versions of *MyErasmusJourney* will extend the current basic functionality with new features focused on improving the discovery, interaction and sharing of Erasmus experiences. Users will be able to filter experiences by criteria such as date, city, rating and category, add profile images and multimedia content to their posts, and share experiences through external applications. City pages will also provide related experiences, average ratings and information about trending destinations. More advanced functionality will include comment replies, email verification, interactive maps for cities and experiences, and a destination ranking based on the rating and reliability of the available experiences. These additions will progressively turn the application into a more complete platform for discovering and comparing Erasmus destinations.

---

## 🚧 Current Status

The application is currently in **Version 0.1 – Basic Functionality and Docker**.

The basic functionality defined for the project has been implemented, together with its corresponding automated tests. The application has also been packaged using Docker and Docker Compose, and a continuous delivery process has been implemented for building and publishing application versions.

Development of *MyErasmusJourney* is still ongoing. The application will continue to evolve in the following phases with additional functionality, improvements and the deployment of new versions.

The current progress and task tracking of the project can be consulted in the following GitHub Projects board:

👉 [MyErasmusJourney Project Board](https://github.com/orgs/codeurjc-students/projects/42)

This board is used to manage tasks, track development progress, and monitor the current state of each feature (e.g., To Do, In Progress, Done).

---

## 🛠️ Version 0.1 Functionalities

Version 0.1 implements the basic functionality of the application for unauthenticated users, authenticated users and administrators.

### 👤 Unauthenticated Users

- **Create an account:** Users can create an account using their email, nickname and password.
- **Log in:** Users can authenticate using their email and password. Authentication is managed through a token stored in cookies.

### 🧑‍💻 Authenticated Users

- **Update user:** Users can update their own account information.
- **Delete user:** Users can delete their own account.
- **Post experience:** Users can create and publish experiences containing the information provided through the experience form.
- **Delete experience:** Users can delete experiences they have created.
- **Comment post:** Users can comment on any experience.
- **Delete comment:** Users can delete comments they have created.
- **Show user's posts:** Users can see their own published experiences from their account page.
- **Show user's comments:** Users can see their own comments from their account page.

### 👑 Administrators

Administrators have the additional permissions required to manage application content and users:

- **Update user:** Administrators can update user account information.
- **Delete user:** Administrators can delete users.
- **Delete experience:** Administrators can delete experiences.
- **Delete comment:** Administrators can delete comments.
- **Add city:** Administrators can add new cities to the application.
- **Show user's posts:** Administrators can access users' published experiences.
- **Show user's comments:** Administrators can access users' comments.

*Insert representative screenshots illustrating the main functionalities described above.*

For the complete and detailed list of functionalities, including their implementation status, see the [Functionalities](detailed_functionalities.md) documentation.

---

## 🔮 Future Versions

The development of *MyErasmusJourney* will continue after Version 0.1.

The following versions will extend the application with the **intermediate and advanced functionalities** defined during the design phase. These versions will introduce additional ways of discovering and interacting with Erasmus destinations and experiences, together with the advanced functionality planned for the final version of the application.

The documentation will be updated as each new functionality is implemented and new versions are released.

---

## ⚙️ Technologies

The application is implemented using the following main technologies:

- **Frontend:** React
- **Backend:** Spring Boot
- **Database:** MySQL
- **Authentication:** JWT-based authentication through cookies
- **API:** REST API
- **Containerization:** Docker and Docker Compose
- **Continuous Integration and Delivery:** GitHub Actions

### 🗺️ Complementary Technology

- **Interactive Maps and Geographical Visualization:** A mapping solution is planned to display Erasmus destinations and geographical information. The specific technology will be evaluated and integrated in a later development phase.

### 🔌 Additional Integrations

External APIs and third-party services will be evaluated and integrated in future versions where required by the planned functionality.

---

## 📚 Documentation

1. [Objectives](./docs/objectives.md)
2. [Methodology](./docs/methodology.md)
3. [Detailed Functionalities](./docs/detailed_functionalities.md)
4. [Functionalities](./docs/functionalities.md)
5. [Advanced Algorithms](./docs/advanced-algorithms.md)
6. [Entities](./docs/entities.md)
7. [User permits](./docs/user-permits.md)
8. [Web interface](./docs/website-prototype.md)
9. [Analysis](./docs/analysis.md)
10. [Changelog](./docs/changelog.md)
11. [AI Usage](./docs/ai_usage.md)
12. [Development Guide](./docs/development_guide.md)

---

## 🤖 Use of AI Tools

Artificial Intelligence tools have been used throughout the development of *MyErasmusJourney* as a support for software development and problem solving.

During **Phase 1**, AI tools were used mainly to support the definition and analysis of the application's functionality, user roles, entities, algorithms, navigation and interface design.

During **Phase 2**, AI tools were used to support the implementation of the project infrastructure, automated tests, frontend and backend architecture, REST API integration, code quality and Continuous Integration.

During **Phase 3**, AI tools were used to support the implementation and testing of the basic functionality, frontend and backend development, authentication, Docker and Docker Compose configuration, and Continuous Delivery and publication workflows.

All AI-generated suggestions and code have been reviewed and validated by the developer, who remains responsible for the final implementation.

Detailed information about the use of AI tools, including the date, objective, tool, configuration and context of each use, is available in [AI_USAGE.md](./AI_USAGE.md).

---

## 👨‍💻 Author

This project is being developed as part of a Final Degree Project (TFG) in the Degree in Computer Engineering at ETSII - URJC.

- **Student:** Jaime Ochoa de Alda Cerdán
- **Supervisor:** Michel Maes Bermejo