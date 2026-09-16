# Detailed Functionalities

This document displays all the functionalities already implemented.

## 🛠️ Basic Functionalities

The following sections describe the main functionalities implemented in Version 0.1. Each functionality is illustrated with screenshots showing its implementation in the application.

### 1. Create an account

Unauthenticated users can create a new account by providing the required registration information, including their email, nickname and password. Once the registration form is successfully completed, the account is created and the user can access the application by logging in. Only one account per email is allowed, if an email is already registered the app will reject the sign up petition and notify the user.

![image](/docs/images/basic_functionalities/signUp.png)

---

### 2. Log in

Unauthenticated users can authenticate by providing their email and password. After successful authentication, the application stores the authentication token in cookies and the user can access the functionalities available to authenticated users.

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

Users can delete experiences they have created from the account page, by clicking the bin button. Administrators can also delete experiences published by other users.

![image](/docs/images/basic_functionalities/deleteExperience.png)


---

### 7. Comment post

Authenticated users can comment on any published experience. This allows users to interact with the experiences shared by other members of the application.

![image](/docs/images/basic_functionalities/comment.png)

---

### 8. Delete comment

Users can delete comments they have created, this functionality works by pressing the bin button next to the comment in the account page. Administrators can also delete comments made by other users.

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

### 📋 Functionality Summary

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
