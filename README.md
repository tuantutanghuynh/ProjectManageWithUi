# ProjectManagerApp

A desktop project management application (Bug/Feature tracking) built with **Java 21 + JavaFX**, connected to **SQL Server** via JDBC. Supports User/Admin roles, login/registration, task creation/editing/deletion, and statistics by priority/status.

> Learning project — applying OOP, Design Patterns (Singleton, Factory), Generics, Exception Handling, Multithreading, JDBC.

## Tech Stack

| Component | Version |
|---|---|
| Java (JDK) | 21 |
| JavaFX | 21.0.10 |
| Maven | javafx-maven-plugin |
| Database | SQL Server (JDBC driver `mssql-jdbc` 13.4.0.jre11) |

## Project Structure

```
src/main/java/com/projectmanager/
├── MainApp.java                # JavaFX entry point
├── config/DatabaseConfig.java  # SQL Server connection
├── exceptions/AppException.java
├── factory/TaskFactory.java    # Factory Pattern: creates Bug/Feature
├── models/                     # Task, Bug, Feature + interfaces (ITask, IAssignable, ISeverityRatable, IPersistable, IProjectAnalytics)
│   ├── dto/LoginRequest.java
│   └── entity/User.java
├── repository/                 # TaskRepository, UserRepository — DB operations
├── service/                    # AuthService (login/register), ProjectService (Singleton + Generic)
├── session/UserSession.java    # Holds login state (Singleton)
├── ui/
│   ├── SceneSwitcher.java      # FXML scene navigation
│   └── controllers/            # Controller for each FXML screen
└── utils/                      # PasswordHasher (SHA-256), Validator
```

## Requirements

- JDK 21 (recommended via Homebrew: `brew install openjdk@21`)
- Maven 3.9+
- SQL Server running at `localhost:1433`, database named `ProjectManagerDB`

Set `JAVA_HOME` to JDK 21 before building (project pins `maven.compiler.release=21`):

```bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
```

## Setup & Run

```bash
mvn compile          # download dependencies + build
mvn javafx:run       # run the application
```

Database connection details are declared in [`DatabaseConfig.java`](src/main/java/com/projectmanager/config/DatabaseConfig.java) — update `URL`/`USER`/`PASSWORD` to match your environment before running.

## Progress

| Step | Description | Status |
|---|---|---|
| 00 | Project setup (Maven + JavaFX) | ✅ |
| 01 | Core Models (Task, Bug, Feature, interfaces) | ✅ |
| 02 | Data Layer (Repository, DatabaseConfig) | ✅ |
| 03 | Service (TaskFactory, AuthService, ProjectService) | ✅ |
| 04 | Utils / Session / Exception | ✅ |
| 05 | Controllers | 🚧 In progress — SceneSwitcher, LoginController done; Register/Dashboard/AddTask/TaskList/UserList remaining |
| 06 | FXML + CSS (UI) | ⏳ Not started |

## Planned Features

- Login / Registration with SHA-256 password hashing
- User / Admin role separation (Admin manages users, deletes tasks)
- Add Bug / Feature via Factory Pattern
- Task list: filter by status/type, update status, delete (admin only)
- Dashboard statistics: total effort, count by priority/status
- Asynchronous data loading (background thread) to keep UI responsive
