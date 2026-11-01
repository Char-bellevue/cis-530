## Swagger UI / OpenAPI Documentation

The Student Services API is documented with springdoc-openapi, which generates the
OpenAPI description and the interactive Swagger UI page automatically.

### How to open Swagger UI

1. Start MySQL, then start the application from the `cis530-week8` folder:
   `.\mvnw spring-boot:run`
2. Open the Swagger UI page in a browser: http://localhost:8080/swagger-ui.html
3. Open the raw OpenAPI JSON document: http://localhost:8080/v3/api-docs

Both documentation URLs are allowed through Spring Security, so no login is needed to read them.

### Trying an endpoint from Swagger UI

The Student API endpoints under `/api/students` still require a login. To use the
**Try it out** button, first sign in at http://localhost:8080/login in the same browser
with an existing member account. GET requests work for USER and ADMIN members, and
POST and DELETE requests need an ADMIN member.

### Documented endpoints

| Method | Path | Success response |
|--------|------|------------------|
| POST | `/api/students` | 201 Created |
| GET | `/api/students` | 200 OK |
| GET | `/api/students/{id}` | 200 OK |
| DELETE | `/api/students/{id}` | 204 No Content |
