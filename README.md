# Distributed E-Commerce Microservices Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-brightgreen?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Netflix Eureka](https://img.shields.io/badge/Eureka-red?style=for-the-badge&logo=netflix&logoColor=white)
![Resilience4j](https://img.shields.io/badge/Resilience4j-orange?style=for-the-badge)
![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapiinitiative&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

A production-ready, cloud-native e-commerce backend built with Spring Boot microservices architecture, featuring service discovery, API gateway, event-driven communication, and comprehensive observability.

## 🏗️ Architecture Overview

This project implements a microservices-based e-commerce platform with the following key components:

![Architecture Diagram](https://github.com/ismail-gits/distributed-ecommerce-microservices-backend/blob/main/architecture-diagram.png)

> **Note:** Replace the image URL above with your actual Eraser.io architecture diagram link

## 🚀 Services

### 1. **API Gateway** (Port: 8083)
- Single entry point for all client requests
- Route management and load balancing
- Circuit breaker pattern with fallback responses
- Request/response filtering and transformation
- Security configuration with Keycloak

### 2. **Discovery Service** (Port: 8761)
- Netflix Eureka Server for service registration and discovery
- Dynamic service instance management
- Health monitoring and heartbeat mechanism
- Service metadata management

### 3. **Product Service** (Port: 8080)
- Product catalog management (CRUD operations)
- MongoDB for flexible document storage
- RESTful API with OpenAPI documentation
- Global exception handling
- Distributed tracing support

### 4. **Order Service** (Port: 8081)
- Order lifecycle management
- Inventory availability validation via Feign Client
- Event publishing to Kafka on order placement
- PostgreSQL database with Flyway migrations
- Resilience patterns with circuit breakers

### 5. **Inventory Service** (Port: 8082)
- Real-time stock availability checking
- SKU-based inventory tracking
- PostgreSQL database with pre-populated test data
- Flyway database version control

### 6. **Notification Service** (Port: 8084)
- Asynchronous event-driven notifications
- Kafka consumer for order placement events
- Email notification integration
- Avro schema for event serialization
- Robust error handling

## 🛠️ Technology Stack

### Core Framework
- **Spring Boot 3.x** - Main application framework
- **Spring Cloud** - Microservices infrastructure
- **Java 17+** - Programming language

### Service Communication
- **Netflix Eureka** - Service discovery
- **Spring Cloud Gateway** - API gateway and routing
- **OpenFeign** - Declarative REST client
- **Apache Kafka** - Event streaming platform
- **Avro** - Data serialization

### Databases
- **MongoDB** - Product catalog (NoSQL)
- **PostgreSQL** - Orders and inventory (Relational)
- **Flyway** - Database migration tool

### Resilience & Monitoring
- **Resilience4j** - Circuit breaker, rate limiter, retry
- **Spring Boot Actuator** - Health checks and metrics
- **Micrometer** - Application metrics
- **Distributed Tracing** - Request tracking across services

### Security
- **Spring Security** - Authentication and authorization
- **OAuth 2.0 / JWT** - Token-based security

### API Documentation
- **Springdoc OpenAPI** - API documentation and Swagger UI

### Testing
- **JUnit 5** - Unit testing
- **WireMock** - Integration testing with mocked services
- **Testcontainers** - Container-based testing

### Build & Deployment
- **Maven** - Dependency management and build tool
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration

## 📋 Prerequisites

- **JDK 17** or higher
- **Maven 3.8+**
- **Docker** and **Docker Compose**
- **Git**

## 🚦 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ismail-gits/distributed-ecommerce-microservices-backend.git
cd distributed-ecommerce-microservices-backend
```

### 2. Start Infrastructure Services

Each service has its own `docker-compose.yml` for dependencies. Start them in order:

```bash
# Start MongoDB for Product Service
cd product-service
docker-compose up -d
cd ..

# Start PostgreSQL for Order Service
cd order-service
docker-compose up -d
cd ..

# Start PostgreSQL for Inventory Service
cd inventory-service
docker-compose up -d
cd ..

# Start Kafka and Zookeeper (if configured separately)
# Or use the API Gateway docker-compose
cd api-gateway
docker-compose up -d
cd ..
```

### 3. Build All Services

```bash
# Build all services
mvn clean install -DskipTests
```

### 4. Start Services in Order

**Start services in the following sequence:**

```bash
# 1. Discovery Service (must start first)
cd discovery-service
mvn spring-boot:run
# Wait for it to be fully up (check http://localhost:8761)

# 2. Configuration Service (if applicable)
# Start other infrastructure services

# 3. Business Services (can start in parallel)
cd ../product-service
mvn spring-boot:run &

cd ../inventory-service
mvn spring-boot:run &

cd ../order-service
mvn spring-boot:run &

cd ../notification-service
mvn spring-boot:run &

# 4. API Gateway (start last)
cd ../api-gateway
mvn spring-boot:run
```

### 5. Verify Services

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8083
- **Product Service Swagger**: http://localhost:8083/api/product/swagger-ui.html
- **Order Service Swagger**: http://localhost:8083/api/order/swagger-ui.html
- **Inventory Service Swagger**: http://localhost:8083/api/inventory/swagger-ui.html

## 📡 API Endpoints

### Product Service

```http
POST   /api/product              # Create product
GET    /api/product              # Get all products
GET    /api/product/{id}         # Get product by ID
PUT    /api/product/{id}         # Update product
DELETE /api/product/{id}         # Delete product
```

### Order Service

```http
POST   /api/order                # Place order
GET    /api/order                # Get all orders
GET    /api/order/{id}           # Get order by ID
PUT    /api/order/{id}           # Update order
DELETE /api/order/{id}           # Delete order
```

### Inventory Service

```http
GET    /api/inventory            # Check inventory
GET    /api/inventory/{skuCode}  # Check specific SKU
```

## 🔧 Configuration

### Application Profiles

Each service supports multiple profiles:

- `default` - Local development
- `docker` - Docker container deployment
- `prod` - Production environment

## 🧪 Testing

### Run Unit Tests

```bash
mvn test
```

### Run Integration Tests

```bash
mvn verify
```

### Run Tests for Specific Service

```bash
cd order-service
mvn test
```

## 📦 Docker Deployment

### Run with Docker Compose

```bash
docker-compose up -d
```

> **Note:** Dockerfiles for individual services will be added in future releases.

## 🔍 Key Features

### 1. **Service Discovery**
- Automatic service registration and discovery
- Dynamic load balancing
- Health checking and failover

### 2. **API Gateway**
- Centralized routing and load balancing
- Circuit breaker for fault tolerance
- Request/response transformation
- Security and authentication

### 3. **Event-Driven Architecture**
- Asynchronous communication via Kafka
- Order placement events
- Email notifications
- Schema registry with Avro

### 4. **Database Management**
- Polyglot persistence (MongoDB + PostgreSQL)
- Automated migrations with Flyway
- JPA/Hibernate for ORM
- Connection pooling and optimization

### 5. **Resilience Patterns**
- Circuit breaker pattern
- Retry mechanism
- Timeout handling
- Fallback responses

### 6. **Observability**
- Distributed tracing
- Metrics collection
- Health endpoints
- Logging and monitoring

## 🏛️ Design Patterns

- **API Gateway Pattern** - Single entry point
- **Service Discovery Pattern** - Dynamic service location
- **Circuit Breaker Pattern** - Fault tolerance
- **Event Sourcing** - Kafka event streaming
- **Database per Service** - Data isolation
- **Saga Pattern** - Distributed transactions
- **Strangler Fig Pattern** - Gradual migration

## 🔒 Security

- Spring Security integration
- OAuth 2.0 / JWT support
- CORS configuration
- Request validation
- Secure communication between services

## 📊 Monitoring & Observability

- Spring Boot Actuator endpoints
- Prometheus metrics
- Health indicators
- Application performance monitoring
- Distributed tracing with Micrometer

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 👤 Author

**Ismail**
- GitHub: [@ismail-gits](https://github.com/ismail-gits)

## 🙏 Acknowledgments

- Spring Boot team for excellent documentation
- Netflix OSS for Eureka
- Apache Kafka for event streaming
- The open-source community

## 📞 Support

For support, email your-email@example.com or open an issue in the GitHub repository.

---

⭐ **Star this repository if you find it helpful!**
