# 📊 SurveyApp — Enterprise Survey & Participant Management System

[![Java Version](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6.2-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

SurveyApp is a secure, optimized, and robust survey management system built with Spring Boot and PostgreSQL. It allows creators to build dynamic questionnaires, distribute secure tokenized participation links, track respondents, and visualize answers in real time.

---

## 🚀 Key Technical Accomplishments & Optimization

### 1. Database Performance Tuning (N+1 Query Resolution)
*   **The Problem:** Default ORM mapping triggered database query storms (dozens of queries per dashboard load) when fetching surveys and nested questions.
*   **The Solution:**
    *   Switched all `@ManyToOne` associations to explicit `FetchType.LAZY` (`Answer`, `Question`, `Survey`, `SurveyInvitation`).
    *   Implemented **JPQL Fetch Joins** (`JOIN FETCH`) in the repository layer to load surveys and their associated questions in a single atomic SQL database query.
    *   Reduced DB calls **from 20+ queries to exactly 1 query** ($O(1)$ complexity).

### 2. Clean Architecture & DTO Decoupling
*   **Encapsulation:** Used the **Data Transfer Object (DTO)** pattern to prevent leakage of database models to client-facing interfaces.
*   **Clean Packages:** Re-packaged the project structure using enterprise packaging standards. Separated requests/responses and decoupled interfaces from their implementations.
*   **Mapping:** Integrated **ModelMapper** to map between Entity and DTO layers securely, avoiding recursive serialization issues.

### 3. Role-Based Access Control & Security
*   Enforced session-based authentication using **Spring Security**.
*   Implemented strict resource owner validation: Survey creators are restricted to managing or viewing results of their own surveys.

---

## 📂 Project Package Structure

```text
src/main/java/com/example/survey_app/
│
├── config/                 # Spring & Security configuration beans
├── controller/             # REST endpoints (Auth, Survey, Question, Answers)
├── dto/
│   ├── request/            # Request payloads (Login, Survey, Invitation, Answers)
│   └── response/           # Response DTOs (SurveyResponse, QuestionResponse)
├── entity/                 # Database entity models (JPA)
├── exception/              # Custom exception classes and global handlers
├── repository/             # Spring Data JPA repositories
└── service/
    ├── impl/               # Service implementations (Answers, Auth, Survey, Users)
    └── *Service.java       # Service interfaces (decoupled design)
```

---

## 📝 REST API Endpoints

### Authentication
*   `POST /api/auth/login` - User login. Returns session context.

### Survey Management (`/api/v1/surveys`)
*   `GET /api/v1/surveys` - Lists DTO-mapped surveys owned by the logged-in user. *(Join Fetch Optimized)*
*   `POST /api/v1/surveys` - Creates a new survey.
*   `PUT /api/v1/surveys/{surveyId}/update` - Updates an existing survey and its questions.
*   `DELETE /api/v1/surveys/{surveyId}/delete` - Deletes a survey and all associated questions/answers.
*   `GET /api/v1/surveys/{surveyId}/results` - Retrieves invitation results and response rates.
*   `POST /api/v1/surveys/{surveyId}/invite` - Generates invite tokens and dispatches registration links via email.

### Question Management
*   `GET /api/v1/surveys/{surveyId}/questions` - Lists questions of a survey.
*   `POST /api/v1/surveys/{surveyId}/questions` - Appends a question to a survey.

### Participation (`/api/v1/answers`)
*   `GET /api/v1/answers/solve?token={token}` - Retrieves survey questions anonymously using a valid invitation token.
*   `POST /api/v1/answers/submit` - Submits participant response payload.

---

## ⚙️ Local Development Setup

### Prerequisites
*   **JDK 17** (Adoptium OpenJDK recommended)
*   **Maven 3.8+**
*   **PostgreSQL** instance

### 1. Database & SMTP Configuration
Configure your settings in `src/main/resources/application.properties`:
```properties
# Database Connectivity
spring.datasource.url=jdbc:postgresql://localhost:5432/survey_db
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password
spring.jpa.hibernate.ddl-auto=update

# SMTP Settings for Invitation Emails
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_specific_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 2. Build and Start
```bash
# Compile and package
./mvnw clean package

# Start the boot application
./mvnw spring-boot:run
```
The application will launch on `http://localhost:8080`.
