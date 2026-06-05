# Budget Manager API

REST API for managing personal finances: accounts, transactions and budget summary.

# Features

- Accounts management (CRUD)
- INCOME & EXPENSE transactions
- Automatic account balance updates
- Transaction filtering by date and category
- Budget summary
- CSV export of transactions
- OpenAPI documentation
- PostgreSQL database
- Docker + Docker Compose


# Tech Stack

- Java 21
- Spring Boot
- PostgreSQL
- Maven
- Docker & Docker Compose
- springdoc-openapi (Swagger)

---

# How to run (Docker - recommended)

## 1. Build and start everything

```bash
docker compose up --build
```

This will start:
- PostgreSQL
- SpringBoot application

## 2. Stop application

```bash
docker compose down
```

To remove database data

```bash
docker compose down -v
```
# API Access

## Swagger UI

http://localhost:8080/swagger-ui/index.html

