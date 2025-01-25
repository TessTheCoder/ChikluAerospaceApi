# ChikluAerospace

## Description
ChikluAerospace is a demo project that integrates with NASA's API to fetch and display data. This project is built using Spring Boot and Maven.

## Features
- Fetch data from NASA's API
- Display data using Thymeleaf templates
- Actuator endpoints for monitoring
- OpenAPI documentation with Springdoc

## Requirements
- Java 17
- Maven 3.6.0 or higher

## Getting Started

### Clone the repository
```bash
git clone https://github.com/TessTheCoder/ChikluAerospace.git
cd ChikluAerospace
```

### Build the project
```bash
mvn clean install
```

### Run the application
```bash
mvn spring-boot:run
```

## Configuration
The application can be configured using the `src/main/resources/application.properties` file.

```ini
spring.application.name=ChikluAerospace
nasa.api.base=https://api.nasa.gov/
nasa.api.key=YOUR_API_KEY
logging.level.root=info
```

Replace `YOUR_API_KEY` with your actual NASA API key.

## Endpoints
- **Actuator**: `/actuator`
- **OpenAPI Documentation**: `/swagger-ui.html`

## Dependencies
- Spring Boot Starter Actuator
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Web
- Lombok
- Jackson Databind
- Spring Boot Starter Test
- Springdoc OpenAPI Starter WebMVC UI

## License
This project is licensed under the MIT License.

## Author
- [TessTheCoder](https://github.com/TessTheCoder)