# 🐾 PetStore API Tests

This project provides automated API tests for the PetStore API (`https://petstore.swagger.io/v2`).
The structure follows best practices in clean code, OOP, environment management, and data-driven testing.

---

## 📦 Tech Stack

- Java 11+
- Maven
- RestAssured
- TestNG
- Lombok
- Jackson (for JSON mapping)
- Java Faker (for random test data)

---

## 🌍 Environment Configuration

Environment-specific settings (such as base URLs) are defined in:

- `src/test/resources/config/dev.properties`
- `src/test/resources/config/qa.properties`

The active environment is set via the `env` parameter in `testng.xml`:

At runtime, the framework will dynamically load the correct baseUrl based on the environment.
```xml
<parameter name="env" value="qa" />

