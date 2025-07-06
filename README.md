# FurSense Backend

A comprehensive digital pet care platform built with microservice architecture to address pet care challenges in Sri Lanka.

## 🐾 Overview

FurSense is a digital pet care ecosystem that replaces traditional paper-based pet records and creates a community-driven platform for pet owners, veterinarians, and pet care services. The platform features digital health records, lost pet alerts, AI-powered assistance, and emergency veterinary access.

## 🏗️ Architecture

The backend follows a **microservice architecture** with the following services:

### Services
- **Auth Service** (`authService/`) - User authentication and authorization
- **Pet Service** (`petService/`) - Pet management, medical history, and lost pet alerts
- **User Service** (`userService/`) - User profile management
- **Gateway** (`gateway/`) - API Gateway for service routing and security
- **Notifications** (`notifications/`) - Push notifications and alert broadcasting

## 🚀 Features

### Core Functionality
- **Digital Pet Records**: Secure storage and management of pet profiles
- **Medical History Management**: Digital vaccination records and health tracking
- **Lost Pet Alert System**: Community-driven pet recovery network with location-based search
- **AI Chatbot Integration**: Instant pet care guidance and assistance
- **Multi-Pet Support**: Manage multiple pets under single account
- **Emergency Veterinary Access**: Quick connection to available veterinarians

### Advanced Features
- **Location-Based Services**: Geographic search for lost pets and nearby services
- **Real-Time Notifications**: Instant alerts for lost pets, vaccinations, and updates
- **Reward System**: Community incentives for pet recovery assistance
- **Status Tracking**: Comprehensive pet status management (SAFE, LOST, FOUND)

## 🛠️ Tech Stack

- **Backend Framework**: Spring Boot 3.4.5
- **Database**: PostgreSQL
- **Language**: Java 21
- **Build Tool**: Maven
- **Architecture**: Microservices
- **API Gateway**: Spring Cloud Gateway
- **ORM**: Spring Data JPA
- **Documentation**: OpenAPI/Swagger

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/CS3940-Group01/FurSense-Backend.git
cd FurSense-Backend
```


### 2. Configure Database Connections
Update `application.properties` files in each service with your database credentials:

**Example for Pet Service** (`petService/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fursense_pet
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 🏃‍♂️ Running the Application

### Option 1: Run All Services (Recommended)
```bash
# Make scripts executable (Linux/Mac)
chmod +x runAll.bat

# Run all services
./runAll.bat
```

### Option 2: Run Individual Services
```bash
# Auth Service
cd authService
./mvnw spring-boot:run

# Pet Service  
cd petService
./mvnw spring-boot:run

# User Service
cd userService
./mvnw spring-boot:run

# Gateway
cd gateway
./mvnw spring-boot:run

# Notifications
cd notifications
./mvnw spring-boot:run
```

### Option 3: Using Compiled JARs
```bash
# Build all services first
mvn clean package -DskipTests

# Run services
java -jar authService/target/authService-0.0.1-SNAPSHOT.jar
java -jar petService/target/petService-0.0.1-SNAPSHOT.jar
java -jar userService/target/userService-0.0.1-SNAPSHOT.jar
java -jar gateway/target/gateway-0.0.1-SNAPSHOT.jar
java -jar notifications/target/notifications-0.0.1-SNAPSHOT.jar
```

## 🌐 Service Endpoints

### API Gateway
- **Base URL**: `http://localhost:8080`
- Routes requests to appropriate microservices

### Pet Service
- **Port**: 8081
- **Health Check**: `GET /actuator/health`
- **Key Endpoints**:
  - `GET /pet/getPetsByOwnerId` - Get user's pets
  - `POST /pet/addPet` - Register new pet
  - `GET /medicalHistory/getMedicalHistoryByPetId` - Get medical history
  - `POST /lost-pet-alerts/create` - Create lost pet alert

### Auth Service
- **Port**: 8082
- Authentication and user management

### User Service  
- **Port**: 8083
- User profile management

### Notifications Service
- **Port**: 8084
- Push notifications and alerts

## 📖 API Documentation

Detailed API documentation is available:
- **Lost Pet Alerts API**: See `petService/LOST_PET_ALERTS_API.md`
- **Swagger UI**: Available at `http://localhost:8080/swagger-ui.html` (when gateway is running)

## 🗄️ Database Schema

### Key Tables (Pet Service)
- `pets` - Pet profiles and basic information
- `medical_history` - Vaccination records and health data
- `lost_pet_alerts` - Lost pet alert information
- `vaccines` - Available vaccines by species
- `vaccine_schedules` - Pet vaccination schedules


## 📱 Mobile App Integration

The backend is designed to work with a React Native mobile application. The API Gateway provides a unified interface for mobile client consumption.


### Development
Use the provided batch scripts for local development:
- `runAll.bat` - Start all services
- `stopAll.bat` - Stop all services

## 👥 Team

- **210194H** - Gunasekara S.L.
- **210302P** - Kulasekara K.M.S.N.
- **210306G** - Kulathunga K.A.J.T.
- **210314E** - Kumarasekara G.K.
- **210434V** - Niroshan G.


**Built with ❤️ for pet owners in Sri Lanka**