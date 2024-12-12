# Real-Time Ticketing System - Backend

## Overview

The **Real-Time Ticketing System** is a Spring Boot application designed to handle ticket sales for an event-based platform. It supports ticket pool management, vendor and customer interactions, and real-time updates via WebSocket. The application is backed by MongoDB for data storage and exposes various REST APIs to manage system configurations.

## Features

- **Ticket Pool Management:** Vendors can release tickets into a pool at a specified rate.
- **Real-Time Updates:** The system sends ticket status updates to connected clients via WebSocket.
- **System Configuration:** Allows dynamic management of the system's configuration, such as start, stop, reset, and system status checking.
- **Transaction Logging:** Tracks and logs ticket transactions for auditing purposes.

## Prerequisites

Before running this application, ensure the following:

- Java 11 or higher is installed on your machine.
- MongoDB instance or MongoDB Atlas setup is required to store system configurations and other data. The connection is provided via `spring.data.mongodb.uri` in the `application.properties`.

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/ticketsystem.git
cd ticketsystem
```

### 2. Configure Application Properties

Update your `application.properties` to ensure proper configuration:

```properties
# Spring Boot application name
spring.application.name=ticketsystem

# MongoDB URI for connecting to MongoDB Atlas
spring.data.mongodb.uri=mongodb+srv://<your-username>:<your-password>@real-time-ticketing-sys.cjpux.mongodb.net/RealTimeTicketingSystem?retryWrites=true&w=majority&appName=real-time-ticketing-system

# Application Port
server.port=8070
```

Make sure to replace `<your-username>` and `<your-password>` with your actual MongoDB credentials.

### 3. Build the Application

Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application

Start the application using the following command:

```bash
mvn spring-boot:run
```

Alternatively, you can run the application by executing the generated `.jar` file:

```bash
java -jar target/ticketsystem-0.0.1-SNAPSHOT.jar
```

The application will be accessible at `http://localhost:8070`.

## API Documentation

### 1. **GET /api/config**
Fetches the current system configuration.

**Response:**
```json
{
  "success": true,
  "data": { ... }
}
```

### 2. **POST /api/config**
Creates a new system configuration.

**Request Body:**
```json
{
  "key": "value"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Configuration created successfully"
}
```

### 3. **PUT /api/config**
Updates the existing system configuration.

**Request Body:**
```json
{
  "key": "newValue"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Configuration updated successfully"
}
```

### 4. **POST /api/config/start**
Starts the system with the provided configuration.

**Request Body:**
```json
{
  "startTime": "2024-12-12T08:00:00",
  "ticketReleaseRate": 5
}
```

**Response:**
```json
{
  "success": true,
  "message": "System started successfully"
}
```

### 5. **POST /api/config/stop**
Stops the system.

**Response:**
```json
{
  "success": true,
  "message": "System stopped successfully"
}
```

### 6. **POST /api/config/reset**
Resets the system and clears the ticket pool.

**Response:**
```json
{
  "success": true,
  "message": "System reset successfully"
}
```

### 7. **GET /api/config/status**
Gets the current status of the system (running or stopped).

**Response:**
```json
{
  "success": true,
  "data": true
}
```

## WebSocket for Real-Time Updates

You can connect to the WebSocket endpoint to receive real-time ticket status updates:

- WebSocket URL: `ws://localhost:8070/ticket-status`

This endpoint provides real-time ticket status updates for ticket pool management, vendor releases, and customer purchases.

### WebSocket Messages
Each message sent over the WebSocket connection will contain information about the ticket pool status, vendor ticket releases, customer purchases, and any additional status messages.

Example Message:
```json
{
  "remainingTickets": 50,
  "vendorTicketsReleased": {
    "vendor1": 20
  },
  "customerTicketsBought": {
    "customer1": 5
  },
  "additionalMessage": "Tickets released by vendor1"
}
```

## Troubleshooting

### Common Issues

#### 1. **MongoDB Connection Failure**
If you're unable to connect to MongoDB, check the following:

- Ensure your MongoDB URI is correct. Double-check your credentials and connection string.
- Verify that your MongoDB instance is accessible and running (if self-hosted).
- If using MongoDB Atlas, ensure your IP is whitelisted and the database is accessible.

#### 2. **Port Conflict**
If the application fails to start due to a port conflict, change the server port in the `application.properties` file:

```properties
server.port=8070
```

#### 3. **WebSocket Connection Issues**
If you're unable to connect to the WebSocket endpoint, ensure that:

- The WebSocket URL is correct: `ws://localhost:8070/ticket-status`.
- The server is running and the WebSocket endpoint is correctly configured.

#### 4. **Transaction Logging Issues**
If transaction logs are not appearing:

- Check if `TicketTransactionService` is configured correctly and able to log transactions.
- Ensure the logging level in `application.properties` allows logging at the appropriate level (e.g., `INFO`, `DEBUG`).

#### 5. **Ticket Release Delays**
If ticket releases by vendors are slower than expected:

- Ensure the `ticketReleaseRate` is correctly set and not too low.
- Review the thread management and concurrency handling for possible bottlenecks.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For support or further inquiries, please contact [gevinnanayakkara@gmail.com].

### Key Sections:
- **Overview:** Summarizes the purpose and features of the system.
- **Setup:** Provides installation and configuration instructions.
- **API Documentation:** Detailed API endpoints and usage examples.
- **WebSocket Integration:** Describes how to use WebSocket for real-time updates.
- **Troubleshooting:** Provides solutions for common issues with MongoDB, ports, WebSocket connections, and more.
