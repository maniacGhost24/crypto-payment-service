# Crypto Payment Service

A production-inspired cryptocurrency payment gateway built with **Spring Boot** that enables merchants to accept **USDC payments on the Base blockchain**.

The system generates unique payment wallets for every payment request, monitors on-chain USDC transfers, automatically reconciles incoming payments, and notifies merchants through webhooks.

---

## Features

### Merchant Authentication

- JWT-based merchant authentication
- Secure merchant registration and login
- API Key generation and management

### Payment Intents

- Create payment intents
- Assign dedicated wallets
- Generate QR codes for wallet payments
- Retrieve payment status

### Wallet Management

- Wallet pool provisioning
- Automatic wallet assignment
- Wallet lifecycle management

### Blockchain Integration

- Base blockchain integration via Alchemy
- USDC Transfer event monitoring
- Blockchain cursor tracking
- Configurable block scanning

### Payment Processing

- Automatic payment matching
- Payment reconciliation
- Payment state transitions
- Transaction persistence

### Merchant Webhooks

- Configurable webhook endpoints
- Automatic payment notifications
- Structured webhook payloads

### Developer Experience

- Swagger/OpenAPI documentation
- GitHub Actions CI
- Dockerized PostgreSQL
- Layered Spring Boot architecture

---

# Architecture

```

```
                   Merchant
                      │
                      ▼
              Spring Boot API
                      │
      ┌───────────────┼────────────────┐
      │               │                │
      ▼               ▼                ▼
 Authentication   Payment Service   API Keys
      │               │
      │               ▼
      │       Wallet Assignment
      │               │
      │               ▼
      │        Payment Intent
      │               │
      └───────────────┼────────────────────┐
                      ▼                    │
             Blockchain Observer           │
                      │                    │
                      ▼                    │
             USDC Transfer Events          │
                      │                    │
                      ▼                    │
              Payment Matching             │
                      │                    │
                      ▼                    │
             Payment State Machine         │
                      │                    │
                      ▼                    │
              Merchant Webhook ◄───────────┘

                      │
                      ▼
                 PostgreSQL
```

---

# Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security + JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA |
| Blockchain | Web3j |
| Network | Base |
| Token | USDC |
| QR Codes | ZXing |
| Documentation | Swagger/OpenAPI |
| Build | Maven |
| CI | GitHub Actions |
| Container | Docker |

---

# API Overview

## Authentication

```
POST /api/auth/signup
POST /api/auth/login
```

## API Keys

```
POST   /api/api-keys
GET    /api/api-keys
DELETE /api/api-keys/{id}
```

## Payment Intents

```
POST /api/payment-intents
GET  /api/payment-intents
GET  /api/payment-intents/{id}
GET  /api/payment-intents/{id}/qr
```

## Merchant

```
PATCH /api/merchant/webhook
```

## Blockchain

```
GET /api/blockchain/latest-block
GET /api/blockchain/cursor
```

---

# Payment Flow

```

Merchant

↓

Create Payment Intent

↓

Wallet Assigned

↓

Customer Sends USDC

↓

Blockchain Observer Detects Transfer

↓

Payment Matching

↓

Payment State Updated

↓

Merchant Webhook Triggered

```

---

# Running Locally

## Clone

```bash
git clone https://github.com/<username>/crypto-payment-service.git
```

## Start PostgreSQL

```bash
docker compose up -d
```

## Configure Environment

```properties
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

JWT_SECRET=

BASE_RPC_URL=

USDC_CONTRACT=
```

## Run

```bash
mvn spring-boot:run
```

---

# Swagger

Swagger UI is available at

```
http://localhost:8080/swagger-ui/index.html
```

---

# Project Structure

```
backend/
    payment-core/

frontend/
    (Reserved for Merchant Dashboard)

infra/
    (Reserved for Deployment)

.github/
    GitHub Actions
```

---

# Future Improvements

- Retryable webhook delivery
- Webhook signature verification
- Multi-chain support
- ERC20 token configuration
- Merchant dashboard
- Admin portal
- Kubernetes deployment
- Event-driven architecture

---

# CI

Every Pull Request automatically runs

- Maven Build
- Unit Tests

using GitHub Actions.

---

# Author

**Rishabh**

Backend Engineer | Spring Boot | Distributed Systems | Blockchain