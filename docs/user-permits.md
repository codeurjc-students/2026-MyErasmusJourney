# User Permits

This section defines the different types of users in the system and their permissions. The application implements a role-based access control system (RBAC) with three user roles: Anonymous User, Authenticated User, and Administrator.

---

## 👤 Anonymous User

Anonymous users are visitors who have not authenticated into the system. They have read-only access to public content and limited interaction capabilities.

### Permissions:
- Filter experiences by city, date, title, and category.
- Explore city information, including descriptions and associated experiences.
- View experiences published by users.
- Access multimedia content attached to experiences.
- Register a new account.
- Log in to the application.

---

## 🔵 Authenticated User

Authenticated users are registered users who have logged into the system. They have full interaction capabilities within the platform, including content creation and management of their own data.

### Permissions:
- View and manage their profile information (display name, email, profile image).
- View their own published experiences and comments.
- Update their profile information.
- Delete their own account.
- Create new experiences.
- Edit or delete their own experiences.
- Create, edit, or delete their own comments.
- Upload multimedia content (images/videos) to their experiences.

### Ownership rules:
- Users can only modify or delete content that they have created.
- Ownership is enforced at backend level.

---

## 🔴 Administrator User

The administrator is the highest privilege role and has full control over the system content and users.

### Permissions:
- Create, update, and delete any city.
- Delete any experience or comment, regardless of the author.
- View and manage any user profile and their associated content.
- Update or delete any user account.
- Moderate platform content to ensure quality and correctness.

---

## 🔐 Access Control Summary

| Action             | Anonymous | Authenticated | Admin |
| ------------------ | --------- | ------------- | ----- |
| View experiences   | ✔️        | ✔️            | ✔️    |
| Create experience  | ❌         | ✔️            | ✔️    |
| Delete own content | ❌         | ✔️            | ✔️    |
| Delete any content | ❌         | ❌             | ✔️    |
| Manage cities      | ❌         | ❌             | ✔️    |
| Manage users       | ❌         | ❌             | ✔️    |

---
🏠 [Home](../README.md) | 📚 Documentation
---