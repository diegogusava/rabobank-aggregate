## Start the Application

**If you have docker installed**:
- Go to the project's root directory and type: ```docker-compose up```

It will spin up a nginx and the spring application. The port 80 and 443 are open.

**If you don't have docker**:

```./mvnw spring-boot:run```

The port you can use in this case is 8080.

## Log into the application

### How to log in:

```curl -v --insecure -X POST -F 'username=super' -F 'password=super' https://localhost/poa/api/login```

The response will provide the header authorization. It contains the token
that can be used for other requests.

### Users:

| user          | password      |  name                 |
|:------------- |:------------- |:--------------------- |
| super         | super         | Super duper employee  |
| frodo         | frodo         |   Frodo Basggins      |
| aragorn       | aragorn       |    Aragorn            |
| boromir       | boromir       |    Boromir            |

# APIs

## User's accounts API

The Accounts API will return all user's accounts. You need to provide an authorization token.

```curl --insecure -H 'Accept: application/json' -H "Authorization: Bearer ${token}" https://localhost/poa/api/accounts```

## Account details API

It will return account details and all cards that belong to the account.
You need to provide an authorization token.

```curl --insecure -H 'Accept: application/json' -H "Authorization: Bearer ${token}" https://localhost/poa/api/accounts/123123123```

# Functional Requirements

Create an endpoint that aggregate account data

- List all accounts that belong to the user logged in
- Display account overview

# Business assumptions:

## Account
- Account belongs to one account holder
- Account can be managed by the holder and by the Agent (Power Of Attorney)
- Account holder can be a company or a person
- Agent can be a company, group of people or a person.

## Power Of Attorney
- A person, group of people or a company can be an agent
- If it gives VIEW permission, the agent can view account overview, it
doesn't take into account the direction (given, received)
- If it doesn't give VIEW permission, the agent can't have access to accounts
information.

## Operator
- An operator can have access to more than one account (View)
- An operator can be a person, company or a group of people.

## Card 
- A card can only have access to one account

## General
- Account information rarely changes
- Account balance frequently change, but for the sake of the demo, it
will be added to the Account class.

# Architecture

Based on the information above I have identified 4 services.
- account
- card
- operator
- portfolio

All the services could be separate applications containing their own data.

### Account Service
- Contains the account data.
- It doesn't change often.
- It triggers other services when an account is created or power of attorney.
For instance: When an account is created, one operator is also created, when
a power of attorney is created, cards and operators are created.

### Card Service
- It contains the account cards.
- Information can be accessed frequently, so you may have a different
requirement for scale.

### Operator Service
- It controls who can have access to the account.
- Every time an account is created, one operator is also created.
- Every time a power of attorney is created, one or more operators are created.
- The relationship among operator and account is done via AccountOperator class.
- The relationship among operator and a group is done via OperatorGroup class.

### Portfolio Service
- It is the "API Gateway".
- It is responsible to aggregate information from other services.
- It doesn't do, but could make async calls to all services.

## Framework / Libraries / Tools / Database

Because it is a demo, I tried to keep things simple. Things I used:

- In Memory database. All data is created in the class config/MockData.java
- OpenAPI: it creates a well defined interface, generates code (interfaces) and
it can also generate clients (java, javascript).
- Nginx: It is used as a reverse proxy, and provides secure channel with a
self sign certificate.
