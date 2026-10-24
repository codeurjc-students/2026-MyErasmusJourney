# Functionalities

This document describes the functionalities implemented in **MyErasmusJourney Version 0.1 and Version 0.2.0**, organized into basic and intermediate functionalities.

## 🛠️ Basic Functionalities

The following sections describe the main functionalities implemented in Version 0.1. Each functionality is illustrated with screenshots showing its implementation in the application.

### 1. Create an account

Unauthenticated users can create a new account by providing the required registration information, including their email, nickname and password. Once the registration form is successfully completed, the account is created and the user can access the application by logging in. Only one account per email is allowed. If an email is already registered, the application rejects the sign-up request and notifies the user.

![image](/docs/images/basic_functionalities/signUp.png)

---

### 2. Log in

Unauthenticated users can authenticate by providing their email and password. After successful authentication, the application stores the authentication token in cookies, allowing the user to access the functionalities available to authenticated users.

![image](/docs/images/basic_functionalities/logIn.png)

---

### 3. Update user

Authenticated users can update their own account information through their profile. Administrators can also update user information when managing other users.

![image](/docs/images/basic_functionalities/editProfile.png)

---

### 4. Delete user

Authenticated users can delete their own account. Administrators can additionally delete other users from the application.

![image](/docs/images/basic_functionalities/deleteAccount.png)

---

### 5. Post experience

Authenticated users can create and publish an Erasmus experience by completing the experience form with the information they want to share and relating the experience to a city. Published experiences can then be accessed through the application.

![image](/docs/images/basic_functionalities/addExperience.png)

---

### 6. Delete experience

Users can delete experiences they have created from the account page by clicking the bin button. Administrators can also delete experiences published by other users.

![image](/docs/images/basic_functionalities/deleteExperience.png)

---

### 7. Comment post

Authenticated users can comment on any published experience. This allows users to interact with the experiences shared by other members of the application.

![image](/docs/images/basic_functionalities/comment.png)

---

### 8. Delete comment

Users can delete comments they have created. This functionality is available through the bin button next to the comment on the account page. Administrators can also delete comments made by other users.

![image](/docs/images/basic_functionalities/deleteComment.png)

---

### 9. Add city

Administrators can add new cities to the application. Once a city has been added, authenticated users can associate their experiences with that destination.

![image](/docs/images/basic_functionalities/addCity.png)

---

### 10. Show user's posts

Authenticated users can access their profile and see the experiences they have published. Administrators can also access the posts belonging to other users.

![image](/docs/images/basic_functionalities/viewExperiencesandComments.png)

---

### 11. Show user's comments

Authenticated users can access their profile and see the comments they have published. Administrators can also access the comments belonging to other users.

![image](/docs/images/basic_functionalities/viewExperiencesandComments.png)

---

### 📋 Basic Functionality Summary

The following table summarizes the basic functionalities implemented in Version 0.1 and the users who can access them.

| # | Functionality | User access |
| :-- | :--- | :--- |
| 1 | Create an account | Unauthenticated user |
| 2 | Log in | Unauthenticated user |
| 3 | Update user | Authenticated user / Administrator |
| 4 | Delete user | Authenticated user / Administrator |
| 5 | Post experience | Authenticated user |
| 6 | Delete experience | Authenticated user / Administrator |
| 7 | Comment post | Authenticated user |
| 8 | Delete comment | Authenticated user / Administrator |
| 9 | Add city | Administrator |
| 10 | Show user's posts | Authenticated user / Administrator |
| 11 | Show user's comments | Authenticated user / Administrator |

---

## 🚀 Intermediate Functionalities

Version 0.2.0 introduces additional functionality focused on helping users discover Erasmus destinations, explore experiences in more detail and share content with others.

### 1. Trending cities

The application includes an algorithm to identify trending cities. This functionality helps users discover destinations that are popular within the application.

![image](/docs/images/intermediate_functionalities/trending_cities.png)

---

### 2. View city information

Users can access a dedicated page for a specific city. This page displays information about the selected destination, including related experiences and the average rating of those experiences.

![image](/docs/images/intermediate_functionalities/city_page.png)

---

### 3. Filter experiences

Users can filter experiences using several criteria to find content relevant to their interests and destination preferences. The available filters include:

- **City:** Display experiences associated with a selected city.
- **Category:** Display experiences belonging to selected categories.
- **Minimum and maximum rating:** Narrow the results according to the desired rating range.
- **Date:** Filter experiences according to their date.

These filters make it easier to explore published experiences and compare information about different destinations.

![image](/docs/images/intermediate_functionalities/filter.png)

---

### 4. Upload multimedia to experiences

Users can attach multimedia files to their experiences, enriching their posts with additional content beyond the information provided in the experience form.

![image](/docs/images/intermediate_functionalities/experience_multimedia_upload.png)

---

### 5. Update profile picture

Users can upload or update their profile picture, allowing them to personalize their accounts.

![image](/docs/images/intermediate_functionalities/profile_image_upload.png)

---

### 6. Share experiences through social networks

Users can share experiences through external applications and social networks, including WhatsApp and X (formerly Twitter). This functionality makes it easier to share experiences with other students outside the application.

![image](/docs/images/intermediate_functionalities/share_experience.png)

---

### 📋 Intermediate Functionality Summary

The following table summarizes the intermediate functionalities introduced in Version 0.2.0.

| # | Functionality | User access |
| :-- | :--- | :--- |
| 1 | Trending cities | Unauthenticated user |
| 2 | View city information | Unauthenticated user |
| 3 | Filter experiences | Unauthenticated user |
| 4 | Upload multimedia to experiences | Authenticated user |
| 5 | Update profile picture | Authenticated user |
| 6 | Share experiences through social networks | Unauthenticated user |
