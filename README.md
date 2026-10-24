# MyErasmusJourney: A web application for sharing Erasmus experiences

![image](/logo.png)

## What is **MyErasmusJourney**?

**MyErasmusJourney** is a web application where students can share their Erasmus experiences, describing problems they faced, useful documentation, and memorable social events during their stay abroad.

It also serves as a discovery platform for future Erasmus students, allowing them to explore destinations, read real experiences, and make informed decisions about their future mobility program.

In **Version 0.2.0**, the application builds upon the basic functionality introduced in Version 0.1 by adding new features to improve the discovery, sharing and exploration of Erasmus experiences. Users can filter experiences by city, category, rating range and date, attach multimedia content to their experiences, update their profile pictures, and share experiences through external social networks such as WhatsApp and X. The application also provides dedicated city information, including related experiences and their average rating, as well as a trending cities algorithm to help users discover popular destinations.

Authenticated users can manage their accounts, publish experiences and interact with posts through comments, while administrators can manage users, experiences, comments and cities. The application is also packaged and published using Docker and Docker Compose.

---

## 📸 Version 0.2

The following screenshots show some of the main functionalities available in Version 0.2.

![image](/docs/images/intermediate_functionalities/city_page.png)

![image](/docs/images/intermediate_functionalities/experience_multimedia.png)

![image](/docs/images/intermediate_functionalities/experience_multimedia_upload.png)

![image](/docs/images/intermediate_functionalities/filter.png)

![image](/docs/images/intermediate_functionalities/profile_image_upload.png)

![image](/docs/images/intermediate_functionalities/share_experience.png)

![image](/docs/images/intermediate_functionalities/trending_cities.png)


This project is still in development, more functionalities will be added and existing ones may be subjected to changes in order to improve the application.

---

## 🎥 Version 0.2.0 Demo

The following video presents the main functionalities implemented in Version 0.2.0. The demonstration is divided according to the different types of users and includes a voice-over explaining the functionality shown.

![video](./docs/videos/functionalities2.mp4)

Future versions of **MyErasmusJourney** will continue to expand the application's functionality, focusing on improving user interaction and destination discovery. Planned features include comment replies, email verification, interactive maps for cities and experiences, and a destination ranking based on the ratings and reliability of available experiences. These additions will progressively turn the application into a more complete platform for discovering, comparing and evaluating Erasmus destinations.

---

## 🚧 Current Status

The application is currently in **Version 0.2.0 — Intermediate Functionality and Deployment**.

Building upon the basic functionality introduced in Version 0.1, the application now includes additional features to improve experience discovery and sharing. These include a trending cities algorithm, dedicated city information pages with related experiences and average ratings, advanced experience filtering, multimedia uploads, profile picture updates, and social media sharing.

The application also includes automated tests and is packaged using Docker and Docker Compose, with a continuous delivery process for building and publishing application versions.

Development of **MyErasmusJourney** is still ongoing. Future development phases will focus on expanding the application's functionality, improving the user experience, and preparing new versions for deployment.

The current progress and task tracking of the project can be consulted in the following GitHub Projects board:

👉 [MyErasmusJourney Project Board](https://github.com/orgs/codeurjc-students/projects/42)

This board is used to manage tasks, track development progress, and monitor the current state of each feature (e.g., To Do, In Progress, Done).

---

## 🛠️ Version 0.2.0 Functionalities

Version 0.2.0 builds upon the basic functionality introduced in Version 0.1, adding new features to improve the discovery, exploration and sharing of Erasmus experiences.

### 👤 Unauthenticated Users

- **Create an account:** Users can create an account using their email, nickname and password.
- **Log in:** Users can authenticate using their email and password. Authentication is managed through a token stored in cookies.
- **Explore experiences:** Users can browse published experiences and filter them by city, category, minimum and maximum rating, and date.
- **Explore trending cities:** Users can discover popular destinations identified by the application's trending cities algorithm.
- **View city information:** Users can access information about a specific city, including related experiences and their average rating.
- **Share experiences:** Users can share experiences through external social networks and applications, such as WhatsApp and X.
- **Filter experiences:** Users can filter experiences by city, category, minimum and maximum rating, and date.

### 🧑‍💻 Authenticated Users

Authenticated users have access to the features available to unauthenticated users, as well as the following account and content management capabilities:

- **Update user:** Users can update their own account information.
- **Update profile picture:** Users can upload or update their profile picture.
- **Delete user:** Users can delete their own account.
- **Post experience:** Users can create and publish experiences containing the information provided through the experience form.
- **Upload multimedia:** Users can attach multimedia files to their experiences.
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

For the complete and detailed list of functionalities, including their implementation status, see the [Functionalities](./docs/detailed_functionalities.md) documentation.

---

## 🔮 Future Versions

The development of **MyErasmusJourney** will continue beyond Version 0.2.0, focusing on implementing the advanced functionalities planned during the design phase.

The next versions will introduce new features to improve user interaction and provide more comprehensive tools for discovering and comparing Erasmus destinations:

- **Comment replies:** Authenticated users will be able to reply to comments on experiences, enabling more detailed discussions and interactions between users.
- **Email verification:** New users will receive a verification code by email after completing the registration form. They will need to verify their email address to activate their account.
- **Interactive city maps:** City pages will include a map displaying the locations associated with experiences. Each marker will provide access to the related experience.
- **European cities map:** The cities page will feature a map of Europe highlighting the cities available in the application, making it easier to explore destinations geographically.
- **Destination ranking:** A dedicated destinations page will rank cities according to their average experience ratings and reliability. Reliability will take into account the number of experiences available for each city.

These improvements will progressively transform **MyErasmusJourney** into a more comprehensive platform for discovering, exploring and comparing Erasmus destinations through shared experiences and community feedback.

The documentation will be updated as these features are implemented and new versions are released.

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