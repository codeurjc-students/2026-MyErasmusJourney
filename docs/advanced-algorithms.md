
# 🧠 Advanced Algorithms / Data Processing

The application will implement data-driven algorithms to analyze user-generated content and provide insights about Erasmus destinations.

---

## 📊 1. Destination Ranking Algorithm (Reliability + Rating)

Cities and destinations will be ranked using a combined scoring system based on:

- Number of published experiences (posts)
- Average rating of the destination (based on user interactions or post ratings)
- Reliability factor based on the amount of available data

### 📌 Reliability System

To ensure fair comparison between destinations, a reliability coefficient will be applied based on the number of published posts:

- A destination with **less than 20 posts** will have low reliability
-  At **20 posts**, the destination is considered **60% reliable**
- At **30 posts**, the destination is considered **80% reliable**
- As the number of posts increases, reliability approaches **100%**

This ensures that destinations with very little data do not appear artificially high in rankings.

#### 📊 Final Ranking Score

The final score will be calculated by combining:

- Weighted average rating
- Reliability coefficient
- Engagement level (comments/interactions)

This ranking will be used to generate:
- “Top Erasmus Destinations” page
- Highlighted cities on the homepage

---

## 📈 2. Trending Destinations Algorithm

A second algorithm will be implemented to detect **trending cities over time**.

This system will analyze:

- Number of posts in the last 7–14 days
- Growth rate of new publications compared to previous periods
- Recent user activity per destination

### 📌 Output

This algorithm will be used to display a **“Trending Cities” section**, where destinations are ordered based on their recent growth in activity.

Examples:
- Barcelona ↑ (high recent growth)
- Berlin ↑↑ (rapid growth trend)
- Amsterdam ↓ (declining activity)