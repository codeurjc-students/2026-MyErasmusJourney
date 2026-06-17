# Entities

This section describes the main entities of the system and the relationships between them. These entities represent the core data model of the application and will be implemented in the relational database.

---

## 👤 User

The User entity stores the information of each registered user in the platform.

### Attributes:
- id
- fullName
- displayName
- email
- password
- profileImage
- role (USER / ADMIN)

### Relationships:
- A User can create multiple Experiences
- A User can write multiple Comments
- A User can add Multimedia content to its experiences
- A User owns their own content (used for permission control)

---

## 🌍 City

The City entity represents a destination where Erasmus students can share experiences.

### Attributes:
- id
- name
- description
- averageRating (calculated field)
- country

### Relationships:
- A City can have multiple Experiences associated with it
- A City is used as a reference for grouping user content

---

## ✈️ Experience

The Experience entity represents a post created by a user describing their Erasmus journey.

### Attributes:
- id
- title
- description
- category (e.g., gastronomy, studies, social events)
- date
- location (optional detailed location)
- rating (optional or derived value)

### Relationships:
- An Experience belongs to one User (author)
- An Experience belongs to one City
- An Experience can have multiple Comments
- An Experience can include multiple Multimedia files

---

## 💬 Comment

The Comment entity represents user feedback on an Experience.

### Attributes:
- id
- content
- date

### Relationships:
- A Comment belongs to one User (author)
- A Comment belongs to one Experience

---

## 🎥 Multimedia

The Multimedia entity represents files uploaded to the system (images or videos).

### Attributes:
- id
- fileName
- fileType (image/video)
- contentType (MIME type)
- binaryData (stored file content or reference path)

### Relationships:
- A Multimedia item belongs to one Experience
- A Multimedia item is uploaded by one User
analysis
---

## 🔗 Entity Relationships Summary

- User → Experience (1:N)
- User → Comment (1:N)
- User → Multimedia (1:N)
- City → Experience (1:N)
- Experience → Comment (1:N)
- Experience → Multimedia (1:N)

---
🏠 [Home](../README.md) | 📚 Documentation
---