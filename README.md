# 📝 To-Do List API

A secure and scalable RESTful To-Do List backend built using **Spring Boot**, **Spring Security (JWT)**, and **MySQL**. This project is more than just a simple task manager—each user has access to private task lists and can manage individual tasks securely with JWT-based authentication.

Unlike typical to-do list apps that are either single-user or rely on basic CRUD, this app features:

* Role-based access through Spring Security
* Token-based session handling with JWTs
* Layered architecture with DTOs, Services, Repositories, and Entity mapping
* Strict validation with Jakarta Validation API

---

## 🚀 Features

* ✅ User Registration & JWT Login
* 🔐 Secure access to private user resources
* 🗂️ Manage multiple task lists
* 📌 Add, update, delete, and retrieve tasks
* ⚙️ MySQL support with Spring Data JPA
* 📦 Built with modular and maintainable code structure

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot 3.4.5
* Spring Security (JWT)
* MySQL
* Maven
* Lombok
* JPA/Hibernate

---

## 📬 API Endpoints

### 👤 User

* `POST /user/add` — Register a new user
* `POST /user/login` — Authenticate and receive JWT token
* `GET /user/find` — Get user info (auth required)
* `PUT /user/update` — Update user profile
* `DELETE /user/delete` — Remove user account

### 🧾 List (auth required)

* `POST /user/list/add` — Create new list
* `GET /user/list/find/{listId}` — Get list details
* `GET /user/list/all` — Retrieve all lists
* `PUT /user/list/update/{listId}` — Edit list
* `DELETE /user/list/delete/{listId}` — Remove list

### ✅ Task (auth required)

* `POST /user/lists/{listId}/tasks/add` — Add task to list
* `GET /user/lists/{listId}/tasks/find/{taskId}` — Get task details
* `GET /user/lists/{listId}/tasks/find/all` — Get all tasks in list
* `PUT /user/lists/{listId}/tasks/update/{taskId}` — Edit task
* `DELETE /user/lists/{listId}/tasks/delete/{taskId}` — Delete task

---

## 📂 Project Structure

```
src/main/java/users/rishik/toDoList/
│
├── Controllers/           # REST API controllers
├── Dtos/                  # Request/response data transfer objects
├── entities/              # JPA entity models
├── enums/                 # Enum types for status and priority
├── Exceptions/            # Custom exception classes
├── filters/               # JWT authentication filter
├── mappers/               # DTO to Entity mapping
├── projections/           # Interfaces for partial projections
├── Repositories/          # Spring Data JPA interfaces
├── Security/              # JWT and Spring Security configuration
├── Services/              # Business logic
├── annotations/           # Custom validation annotations
├── validators/            # Custom validation logic
└── ToDoListApplication.java  # Main Spring Boot application class
```

---

## ⚙️ Configuration

Update `src/main/resources/application.properties` with your configuration:

```
spring.datasource.url=jdbc:mysql://localhost:3306/<your_db>
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
jwt.secret=<256_char_secret>
jwt.expiration=86400000
```

---

## ▶️ Running the App

```bash
./mvnw spring-boot:run
```

---

## 📄 License

This project is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
