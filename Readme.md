# 🥗 NutriAI — AI-Powered Nutrition & Meal Planning Backend

> A **Spring Boot REST API** for intelligent meal planning, recipe management, and personalized nutrition tracking. Built with Java 21, Spring Security (JWT), and MySQL.

---

## 📋 Table of Contents

- [About the Project](#-about-the-project)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [API Endpoints](#-api-endpoints)
  - [Authentication](#-authentication-apauth)
  - [Recipe Management](#-recipe-management-apirecipe)
  - [Meal Plan Management](#-meal-plan-management-apimeal-plan)
- [Security](#-security)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Setup & Run](#setup--run)
- [Configuration](#-configuration)
- [Future Roadmap](#-future-roadmap)

---

## 🌟 About the Project

**NutriAI** is a backend REST API that powers a nutrition-focused application. Users can:

- Register and log in securely using **JWT-based authentication**
- Manage a **recipe library** with calorie, protein, and carb details
- Create **weekly meal plans** and assign recipes to specific days and meal types (Breakfast, Lunch, Dinner, Snack)
- Store personal health profile data including height, weight, age, dietary restrictions, and fitness goals

The project is designed for integration with a frontend or mobile client and follows a clean **layered architecture** (Controller → Service → Repository → Entity).

---

## 🛠️ Tech Stack

| Layer        | Technology                                      |
|--------------|-------------------------------------------------|
| Language     | Java 21                                         |
| Framework    | Spring Boot 4.0.1                               |
| Security     | Spring Security + JWT (`jjwt` 0.13.0)           |
| Persistence  | Spring Data JPA (Hibernate)                     |
| Database     | MySQL 8+                                        |
| Build Tool   | Apache Maven                                    |
| Utilities    | Lombok, Jackson (JSR310 for Java Date/Time)     |
| Server Port  | `1003`                                          |

---

## 📁 Project Structure

```
Nutriai/
├── src/
│   ├── main/
│   │   ├── java/com/code/Nutriai/
│   │   │   ├── Filters/
│   │   │   │   └── JwtFilter.java            # JWT request filter
│   │   │   ├── Principal/                    # Security principal
│   │   │   ├── connfig/
│   │   │   │   └── SecurityConfig.java       # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── AIController.java         # AI suggestion endpoints (WIP)
│   │   │   │   ├── AuthController.java       # Register & Login
│   │   │   │   ├── MealPlanController.java   # Meal plan CRUD
│   │   │   │   └── RecipeController.java     # Recipe CRUD
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java         # Login payload DTO
│   │   │   │   └── MealPlanRequest.java      # Meal plan creation DTO
│   │   │   ├── exception/                    # Custom exception handlers (WIP)
│   │   │   ├── model/
│   │   │   │   ├── AiSuggestion.java         # AI suggestion entity
│   │   │   │   ├── Ingredient.java           # Ingredient entity
│   │   │   │   ├── MealPlan.java             # Weekly meal plan entity
│   │   │   │   ├── MealPlanEntry.java        # Individual meal entry (day + type + recipe)
│   │   │   │   ├── Recipe.java               # Recipe entity
│   │   │   │   ├── User.java                 # User entity with health profile
│   │   │   │   └── UserPreference.java       # User preference entity
│   │   │   ├── repository/
│   │   │   │   ├── MealPlanEntryRepository.java
│   │   │   │   ├── MealPlanRepository.java
│   │   │   │   ├── RecipeRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AIService.java            # AI service (WIP)
│   │   │   │   ├── AuthService.java          # Registration & JWT login
│   │   │   │   ├── JwtService.java           # Token generation & validation
│   │   │   │   ├── MealPlanService.java      # Meal plan business logic
│   │   │   │   ├── MyUserDetailsService.java # Spring Security UserDetails
│   │   │   │   ├── NutritionService.java     # Nutrition logic (WIP)
│   │   │   │   └── RecipeService.java        # Recipe business logic
│   │   │   └── NutriaiApplication.java       # Spring Boot entry point
│   │   └── resources/
│   │       ├── application.properties        # App configuration
│   │       └── banner.txt                    # Custom startup banner
│   └── test/
├── pom.xml
└── Readme.md
```

---

## 🗄️ Database Schema

The application uses **5 core tables** (auto-created by Hibernate with `ddl-auto=update`):

| Table               | Description                                             |
|---------------------|---------------------------------------------------------|
| `users`             | User accounts with health profile (height, weight, age, goal, dietary restrictions) |
| `user_preferences`  | One-to-one extended preferences per user                |
| `recipes`           | Recipe library (name, description, image URL, calories, protein, carbs) |
| `meal_plans`        | Weekly meal plan linked to a user and a `weekStartDate` |
| `meal_plan_entries` | Individual meal slots — each entry links a meal plan to a recipe for a specific day + meal type |

**Meal Types (Enum):** `BREAKFAST`, `LUNCH`, `DINNER`, `SNACK`

---

## 🔌 API Endpoints

> **Base URL:** `http://localhost:1003`
>
> All endpoints **except `/api/auth/**`** require a valid **JWT Bearer token** in the `Authorization` header.

---

### 🔐 Authentication (`/api/auth`)

| Method | Endpoint         | Description              | Auth Required |
|--------|------------------|--------------------------|---------------|
| POST   | `/api/auth/reg`  | Register a new user      | ❌ No         |
| POST   | `/api/auth/login`| Login and receive JWT    | ❌ No         |

#### Register — `POST /api/auth/reg`
```json
{
  "name": "Rupesh",
  "email": "rupesh@example.com",
  "password": "securepassword",
  "goal": "Weight Loss",
  "dietaryRestrictions": "Vegan",
  "height": 175.0,
  "weight": 70.0,
  "age": 22
}
```

#### Login — `POST /api/auth/login`
```json
{
  "email": "rupesh@example.com",
  "password": "securepassword"
}
```
**Response:** Returns a JWT token string.

---

### 🍽️ Recipe Management (`/api/recipe`)

| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| POST   | `/api/recipe/add`         | Add a new recipe         |
| GET    | `/api/recipe/getall`      | Get all recipes          |
| GET    | `/api/recipe/get/{id}`    | Get recipe by ID         |
| PUT    | `/api/recipe/update/{id}` | Update a recipe          |
| DELETE | `/api/recipe/delete/{id}` | Delete a recipe          |

#### Add Recipe — `POST /api/recipe/add`
```json
{
  "name": "Grilled Chicken Bowl",
  "description": "High protein bowl with veggies",
  "imageUrl": "https://example.com/image.jpg",
  "calories": 450,
  "protein": 40.0,
  "carbs": 35.0
}
```

---

### 📅 Meal Plan Management (`/api/meal-plan`)

| Method | Endpoint                              | Description                          |
|--------|---------------------------------------|--------------------------------------|
| POST   | `/api/meal-plan/create`               | Create a weekly meal plan            |
| GET    | `/api/meal-plan/{id}`                 | Get meal plan by ID                  |
| GET    | `/api/meal-plan/{mealPlanId}/entries` | Get all entries for a meal plan      |
| POST   | `/api/meal-plan/{mealPlanId}/entry`   | Add or update a meal entry           |
| DELETE | `/api/meal-plan/{mealPlanId}/entry`   | Delete a specific meal entry         |
| DELETE | `/api/meal-plan/{id}`                 | Delete an entire meal plan           |

#### Create Meal Plan — `POST /api/meal-plan/create`
```json
{
  "userId": 1,
  "weekStartDate": "2025-05-05"
}
```

#### Add/Update Entry — `POST /api/meal-plan/{mealPlanId}/entry`
```
Query Params:
  day       = "Monday"
  mealType  = "LUNCH"          (BREAKFAST | LUNCH | DINNER | SNACK)
  recipeId  = 3
```

---

## 🔒 Security

- **Spring Security** is configured to permit only `/api/auth/**` publicly.
- All other routes require a **JWT Bearer token**.
- Passwords are hashed using **BCrypt**.
- A custom `JwtFilter` intercepts every request, validates the token, and sets the `SecurityContext`.
- Token generation and validation is handled by `JwtService` using the `jjwt` library.

```
Authorization: Bearer <your_jwt_token>
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.8+**
- **MySQL 8+** running locally

### Setup & Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/Rupeswarbhainsa777/Nutriai.git
   cd Nutriai
   ```

2. **Create the MySQL database**
   ```sql
   CREATE DATABASE nutriai;
   ```

3. **Configure credentials** in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nutriai
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

4. **Build and run**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

5. **API is live at:** `http://localhost:1003`

---

## ⚙️ Configuration

Key properties in `application.properties`:

| Property                                          | Value / Description                     |
|---------------------------------------------------|-----------------------------------------|
| `spring.application.name`                         | `Nutriai`                               |
| `server.port`                                     | `1003`                                  |
| `spring.datasource.url`                           | `jdbc:mysql://localhost:3306/nutriai`   |
| `spring.jpa.hibernate.ddl-auto`                   | `update` — auto-creates/updates tables  |
| `spring.jackson.deserialization.fail-on-null-for-primitives` | `false`                    |

> ⚠️ **Never commit real credentials to version control.** Use environment variables or Spring profiles for production.

---

## 🗺️ Future Roadmap

- [ ] **AI Meal Suggestions** — Integrate an AI/ML service to recommend personalized meals based on user goals and dietary restrictions
- [ ] **Nutrition Tracking** — Complete `NutritionService` for daily/weekly calorie & macro aggregation
- [ ] **Ingredient Management** — Full CRUD for `Ingredient` model
- [ ] **User Preferences** — Expand `UserPreference` to include cuisine types, allergies, and meal frequency
- [ ] **Exception Handling** — Implement a global `@ControllerAdvice` for consistent error responses
- [ ] **Swagger / OpenAPI Docs** — Add auto-generated API documentation
- [ ] **Docker Support** — Containerize the application with `docker-compose` for MySQL + App
- [ ] **Frontend Integration** — Connect with a React/Next.js frontend

---

## 👨‍💻 Author

**Rupesh** — [GitHub: Rupeswarbhainsa777](https://github.com/Rupeswarbhainsa777)

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
