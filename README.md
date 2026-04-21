# Behaviour Bridge Tracks — Spring Boot Backend

## Overview
REST API backend for the Behaviour Bridge Tracks application. Built with Spring Boot, Spring Security (JWT), and MongoDB. Handles authentication, behaviour record management, and role-based access control for both CLIENT (parent) and ADMIN (practitioner) users.

---

## Prerequisites

| Tool | Version | Download |
|---|---|---|
| Java JDK | 17 or higher | https://adoptium.net |
| IntelliJ IDEA | Latest | https://www.jetbrains.com/idea |
| MongoDB | 6.x or higher | https://www.mongodb.com/try/download/community |
| MongoDB Compass | Latest | https://www.mongodb.com/products/compass |
| Postman | Latest | https://www.postman.com/downloads |
| Maven | Bundled with IntelliJ | — |

---

## Project Setup

### Step 1 — Clone or open the project
Open IntelliJ IDEA → File → Open → select the `BehaviourBridgeTracks` folder

### Step 2 — Load Maven dependencies
IntelliJ will show a popup — click **Load Maven Changes**
Wait for all dependencies to download (2-5 minutes first time)

### Step 3 — Start MongoDB
Open MongoDB Compass and connect to:
```
mongodb://localhost:27017
```
Make sure the connection shows green/connected status.

### Step 4 — Run the application
In IntelliJ, find `BehaviourBridgeTracksApplication.java` → right-click → **Run**

Or click the green play button at the top right of IntelliJ.

---

## Verifying the App is Running

When Spring Boot starts successfully you will see in the console:
```
✅ Behaviour types seeded
✅ Triggers seeded
✅ Settings seeded
✅ Responses seeded
Tomcat started on port(s): 8080
Started BehaviourBridgeTracksApplication in 4.5 seconds
```

Test in browser:
```
http://localhost:8080/api/lookups/behaviour-types
```
Should return a JSON list of behaviour types.

---

## Project Structure

```
src/main/java/com/behaviourbridgetracks/
├── config/
│   └── DataSeeder.java           # Seeds lookup data on first run
├── controller/
│   ├── AuthController.java       # Login, register endpoints
│   ├── BehaviourRecordController.java  # CRUD for behaviour records
│   ├── ClientController.java     # Client management
│   └── LookupController.java     # Behaviour types, triggers, settings
├── dto/
│   ├── AuthResponse.java         # JWT login response
│   ├── BehaviourRecordRequest.java
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── model/
│   ├── User.java                 # User document (ADMIN/CLIENT)
│   ├── Client.java               # Child/client document
│   ├── BehaviourRecord.java      # Core behaviour record
│   ├── BehaviourType.java        # Lookup: behaviour types
│   ├── Trigger.java              # Lookup: triggers
│   ├── Setting.java              # Lookup: locations/settings
│   ├── Response.java             # Lookup: strategies
│   ├── RecordBehaviour.java      # Junction table
│   ├── RecordTrigger.java        # Junction table
│   ├── RecordSetting.java        # Junction table
│   └── RecordResponse.java       # Junction table
├── repository/                   # MongoDB repositories
├── security/
│   ├── JwtUtil.java              # JWT generation and validation
│   ├── JwtFilter.java            # JWT request filter
│   └── SecurityConfig.java       # Security configuration
└── BehaviourBridgeTracksApplication.java
```

---

## Configuration

Open `src/main/resources/application.properties`:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/behaviourbridgedb
spring.data.mongodb.database=behaviourbridgedb

# Server
server.port=8080

# JWT
jwt.secret=BehaviourBridgeTracksSecretKey2024SuperLongSecretKeyForSecurity
jwt.expiration=86400000
```

---

## API Endpoints

### Authentication (public — no token needed)
```
POST /api/auth/login              Login — returns JWT token + role
POST /api/auth/register/admin     Register practitioner account
POST /api/auth/register/client    Register parent account
```

### Lookups (authenticated)
```
GET /api/lookups/behaviour-types  All behaviour types
GET /api/lookups/triggers         All triggers
GET /api/lookups/settings         All settings/locations
GET /api/lookups/responses        All strategies/responses
```

### Behaviour Records
```
POST /api/behaviour-records           Save new record (both roles)
GET  /api/behaviour-records/my-records  Parent's child records (CLIENT)
GET  /api/behaviour-records/all         All records (ADMIN only)
GET  /api/behaviour-records/{id}        Single record (both roles)
GET  /api/behaviour-records/client/{id} Records by client (ADMIN only)
```

### Clients
```
GET  /api/clients                     All clients (ADMIN only)
POST /api/clients                     Add client (ADMIN only)
GET  /api/clients/{id}                Single client (ADMIN only)
GET  /api/clients/my-child            Parent's linked child (CLIENT)
PUT  /api/clients/{id}/link/{userId}  Link client to parent (ADMIN only)
```

---

## User Roles

| Role | Description | Access |
|---|---|---|
| ADMIN | Practitioner / Staff | Full access to all endpoints |
| CLIENT | Parent / Caregiver | Own records only |

---

## MongoDB Collections

| Collection | Description |
|---|---|
| `users` | All user accounts (admin + client) |
| `clients` | Children/client profiles |
| `behaviour_records` | Core behaviour log records |
| `behaviour_types` | Lookup: Physical Aggression etc |
| `triggers` | Lookup: Sensory Overload etc |
| `settings` | Lookup: Home, School etc |
| `responses` | Lookup: De-escalation etc |
| `record_behaviours` | Junction: record ↔ behaviour type |
| `record_triggers` | Junction: record ↔ trigger |
| `record_settings` | Junction: record ↔ setting |
| `record_responses` | Junction: record ↔ response |

---

## Test Accounts

These accounts are pre-created for testing:

```
Practitioner (ADMIN):
  Email:    sarah@clinic.com
  Password: password123

Parent (CLIENT):
  Email:    john@gmail.com
  Password: password123
```

---

## Testing with Postman

Import the Postman collection and test in this order:

```
1. POST /api/auth/register/admin  → create practitioner
2. POST /api/auth/register/client → create parent
3. POST /api/auth/login           → get JWT token
4. POST /api/clients              → add child (ADMIN token)
5. PUT  /api/clients/{id}/link/{parentId} → link to parent
6. POST /api/behaviour-records    → log behaviour (ADMIN token)
7. GET  /api/behaviour-records/my-records → view as parent
```

---

## Startup Order

Always start in this order:

```
1. Start MongoDB (verify in Compass)
2. Run Spring Boot in IntelliJ
3. Run Flutter app
```

---

## Troubleshooting

**MongoDB connection failed**
→ Open MongoDB Compass and verify connection to `localhost:27017`
→ Make sure MongoDB service is running

**Port 8080 already in use**
→ Change `server.port=8081` in `application.properties`
→ Update Flutter `api_config.dart` to match

**JWT token errors**
→ Make sure `jwt.secret` is at least 32 characters long

**Maven dependencies not downloading**
→ Check internet connection
→ Delete `C:\Users\{username}\.m2\repository` and reload Maven

**403 Forbidden errors**
→ Check that you are sending the Bearer token in Authorization header
→ Verify the user role matches the endpoint requirements