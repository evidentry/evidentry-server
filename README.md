# Evidentry Server

> 🚀 Server-side application for event auditing and traceability

## 📚 Overview

**Evidentry Server** is a reactive Spring Boot application for capturing, storing, and querying audit events across distributed systems.\
It is designed to be lightweight, extensible, and easy to integrate.

## ⚙️ Tech Stack

- Java 17+
- Spring Boot 3 (WebFlux)
- Spring Data R2DBC
- H2 Database (in-memory)
- Reactive REST API
- Maven build system

## 📦 Project Structure

```
evidentry-server/
├── src/main/java/io/evidentry/
│   ├── controller/          # REST Controllers
│   ├── model/               # Database Entities (R2DBC)
│   ├── repository/          # Reactive Repositories
│   │   └── entity/          # Entities
│   ├── service/             # Business Services
│   └── EvidentryApplication.java
├── src/main/resources/
│   └── application.yml      # App configuration
├── pom.xml
└── README.md
```

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/evidentry/evidentry-server.git
   ```
2. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Access H2 Console:
    - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `r2dbc:h2:mem:///evidentrydb`
    - Username: `sa`, no password

## 🛠️ Planned Features

- Audit event recording via REST API
- Query and filter stored audit events
- Event streaming via WebSocket (future)
- Pluggable storage options (PostgreSQL, Elasticsearch)

## 📄 License

Licensed under the Apache License 2.0.\
© 2025 [justedlev](https://github.com/justedlev)

