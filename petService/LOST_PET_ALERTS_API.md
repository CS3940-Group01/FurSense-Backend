# Lost Pet Alerts API Documentation

## Overview

The Lost Pet Alerts feature allows pet owners to create, manage, and track alerts for their lost pets. The system also enables community members to search for lost pets in their area and help reunite pets with their owners.

## Base URL

```
http://localhost:8080/pet-service
```

## Authentication

All endpoints require a `userId` header containing the owner's ID.

## Endpoints

### 1. Create Lost Pet Alert

**POST** `/lost-pet-alerts/create`

Creates a new alert for a lost pet.

**Headers:**

- `userId`: String (required) - The owner's user ID

**Request Body:**

```json
{
  "petId": 1,
  "lastSeenLocation": "Central Park, New York",
  "description": "Friendly golden retriever wearing a red collar. Responds to 'Max'. Very friendly with children.",
  "contactInfo": "Call John at 555-0123 or email john@example.com",
  "rewardAmount": 500.0
}
```

**Response:**

```json
{
  "id": 1,
  "petId": 1,
  "ownerId": 123,
  "petName": "Max",
  "petType": "Dog",
  "lastSeenLocation": "Central Park, New York",
  "description": "Friendly golden retriever wearing a red collar...",
  "contactInfo": "Call John at 555-0123 or email john@example.com",
  "rewardAmount": 500.0,
  "alertCreated": "2025-06-12T10:30:00",
  "alertUpdated": "2025-06-12T10:30:00",
  "status": "ACTIVE",
  "foundLocation": null,
  "foundBy": null,
  "foundDate": null
}
```

### 2. Update Lost Pet Alert

**PUT** `/lost-pet-alerts/{alertId}`

Updates an existing active alert.

**Headers:**

- `userId`: String (required) - The owner's user ID

**Request Body:**

```json
{
  "lastSeenLocation": "Washington Square Park, New York",
  "description": "Updated description with new sighting information",
  "contactInfo": "Call John at 555-0123 or text 555-0456",
  "rewardAmount": 750.0
}
```

### 3. Mark Pet as Found

**POST** `/lost-pet-alerts/{alertId}/mark-found`

Marks a lost pet as found and resolves the alert.

**Headers:**

- `userId`: String (required) - The owner's user ID

**Request Body:**

```json
{
  "foundLocation": "Brooklyn Bridge Park",
  "foundBy": "Sarah Johnson - Good Samaritan"
}
```

### 4. Cancel Alert

**DELETE** `/lost-pet-alerts/{alertId}/cancel`

Cancels an active alert (sets status to CANCELLED).

**Headers:**

- `userId`: String (required) - The owner's user ID

**Response:**

```json
"Alert cancelled successfully"
```

### 5. Get All Active Alerts

**GET** `/lost-pet-alerts/active`

Retrieves all currently active lost pet alerts.

**Response:**

```json
[
  {
    "id": 1,
    "petId": 1,
    "ownerId": 123,
    "petName": "Max",
    "petType": "Dog",
    "lastSeenLocation": "Central Park, New York",
    "description": "Friendly golden retriever...",
    "contactInfo": "Call John at 555-0123",
    "rewardAmount": 500.0,
    "alertCreated": "2025-06-12T10:30:00",
    "alertUpdated": "2025-06-12T10:30:00",
    "status": "ACTIVE",
    "foundLocation": null,
    "foundBy": null,
    "foundDate": null
  }
]
```

### 6. Get My Alerts

**GET** `/lost-pet-alerts/my-alerts`

Retrieves all alerts created by the authenticated user.

**Headers:**

- `userId`: String (required) - The owner's user ID

### 7. Search Alerts by Location

**GET** `/lost-pet-alerts/search?location={location}`

Searches for active alerts near a specific location.

**Query Parameters:**

- `location`: String (required) - Location to search for (case-insensitive partial match)

**Example:**

```
GET /lost-pet-alerts/search?location=central park
```

### 8. Get Recent Active Alerts

**GET** `/lost-pet-alerts/recent`

Retrieves active alerts from the last 30 days, ordered by creation date.

## Pet Management Endpoints

### 9. Update Pet Status

**PUT** `/pet/{petId}/status?status={status}`

Updates the status of a pet.

**Headers:**

- `userId`: String (required) - The owner's user ID

**Query Parameters:**

- `status`: String (required) - One of: SAFE, LOST, FOUND

### 10. Get Lost Pets

**GET** `/pet/lost`

Retrieves all pets currently marked as lost.

### 11. Get My Pets

**GET** `/pet/my-pets`

Retrieves all pets belonging to the authenticated user.

**Headers:**

- `userId`: String (required) - The owner's user ID

## Data Models

### Pet Status Enum

- `SAFE` - Pet is safe with owner
- `LOST` - Pet is currently lost
- `FOUND` - Pet has been found but may not be back with owner yet

### Alert Status Enum

- `ACTIVE` - Alert is currently active and pet is still lost
- `RESOLVED` - Pet has been found and alert is resolved
- `CANCELLED` - Alert was cancelled by owner

## Error Responses

### 400 Bad Request

```json
{
  "error": "Invalid request data"
}
```

### 403 Forbidden

```json
{
  "error": "You don't have permission to access this resource"
}
```

### 404 Not Found

```json
{
  "error": "Resource not found"
}
```

### 500 Internal Server Error

```json
{
  "error": "An internal server error occurred"
}
```

## Usage Examples

### Creating an Alert Workflow

1. Pet goes missing
2. Owner calls `POST /lost-pet-alerts/create` with pet details
3. System automatically sets pet status to LOST
4. Alert is broadcast to the community
5. Community members can search alerts by location
6. When pet is found, owner calls `POST /lost-pet-alerts/{alertId}/mark-found`
7. System sets alert status to RESOLVED and pet status to FOUND

### Community Helper Workflow

1. Community member finds a lost pet
2. They search active alerts using `GET /lost-pet-alerts/search?location=area`
3. They contact the owner using the contact information in the alert
4. Owner marks pet as found through the API

## Integration with Notifications Service

The Lost Pet Alerts system integrates with the notifications microservice to:

- Send push notifications to users in the area when a new alert is created
- Email alerts to registered community members
- Update social media platforms with lost pet information
- Notify local animal shelters and veterinarians
- Send celebration notifications when pets are found

## Database Schema

### lost_pet_alerts Table

```sql
CREATE TABLE lost_pet_alerts (
    id SERIAL PRIMARY KEY,
    pet_id INTEGER NOT NULL,
    owner_id INTEGER NOT NULL,
    last_seen_location VARCHAR(255) NOT NULL,
    description TEXT,
    contact_info VARCHAR(255),
    reward_amount DECIMAL(10,2),
    alert_created TIMESTAMP NOT NULL,
    alert_updated TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    found_location VARCHAR(255),
    found_by VARCHAR(255),
    found_date TIMESTAMP
);
```

### pets Table (Updated)

```sql
ALTER TABLE pets ADD COLUMN status VARCHAR(20) DEFAULT 'SAFE';
```
