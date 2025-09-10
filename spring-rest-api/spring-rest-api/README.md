# Spring REST API

This project is a simple Spring Boot application that demonstrates how to create a RESTful API that interacts with an external API using an authorization header.

## Project Structure

```
spring-rest-api
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── springrestapi
│   │   │               ├── SpringRestApiApplication.java
│   │   │               ├── controller
│   │   │               │   └── ApiController.java
│   │   │               └── service
│   │   │                   └── ApiService.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── static
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── springrestapi
│                       └── SpringRestApiApplicationTests.java
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd spring-rest-api
   ```

2. **Build the project**:
   ```
   mvn clean install
   ```

3. **Run the application**:
   ```
   mvn spring-boot:run
   ```

4. **Access the API**:
   The API endpoint can be accessed at `http://localhost:8080/api/external-data`. Make sure to include the necessary authorization header in your request.

## Usage Example

To call the external API, you can use tools like `curl` or Postman. Here’s an example using `curl`:

```
curl -H "Authorization: Bearer <your-token>" http://localhost:8080/api/external-data
```

Replace `<your-token>` with your actual authorization token.

## Dependencies

This project uses the following dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Test
- Spring Web

