# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MyShowList is a Java 25 Spring Boot application for managing a collection of shows (movies and TV series). The project strictly follows **Ports and Adapters (Hexagonal) Architecture** with Data-Oriented Programming (DOP) principles.

## Build and Development Commands

### Prerequisites
Start the PostgreSQL database before running the application:
```sh
docker run --name my-show-list-db -e POSTGRES_PASSWORD=password -p '5432':'5432' -d postgres:18
```

### Maven Commands
```sh
# Build the project
./mvnw clean install

# Run the application (local profile)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ShowServiceTest

# Run a single test method
./mvnw test -Dtest=ShowServiceTest#should_fetch_shows
```

## Architecture

### Ports and Adapters Flow
The architecture enforces this strict flow:
```
adapter/in -> port/in -> domain/service -> port/out -> adapter/out
```

**Package Structure:**
- `adapter/in/` - Inbound adapters (REST controllers)
  - `auth/` - Authentication endpoints (register, login) with JWT security
  - `show/` - Show listing endpoints
  - `usershow/` - User's personal show collection endpoints
  - `review/` - Review endpoints (in progress)
- `adapter/out/` - Outbound adapters (JPA repositories, entities, mappers)
  - `show/` - Show persistence (entities, repository, loader)
  - Root package - User, UserShow, and authentication adapters
- `application/port/in/` - Inbound port interfaces (application API)
- `application/port/out/` - Outbound port interfaces (infrastructure contracts)
- `application/domain/model/` - Domain models (entities, value objects)
- `application/domain/service/` - Business logic services
- `config/` - Spring configuration (DomainConfig for service beans)

### Data-Oriented Programming (DOP)

The project uses sealed types with pattern matching instead of exceptions or nullable fields:

**Sealed Interfaces for Results:**
```java
public sealed interface FetchShowsResult permits Success {
    record Success(List<Show> shows) implements FetchShowsResult {}
}
```

**Pattern Matching in Services:**
```java
return switch (result) {
    case FetchShowsResult.Success success -> success.shows();
};
```

**Sealed Domain Hierarchy:**
- `Show` (sealed abstract class) permits `Movie` and `TvSeries`
- Pattern matching is used throughout for type-safe handling

### Domain Model Conventions

**Value Objects:**
All domain value objects are records with validation in compact constructors:
```java
public record Title(String name) {
    public Title {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        // ... validation
    }
}
```

**Key Value Objects:**
- Show-related: `ShowId(UUID)`, `Title(String)`, `Description(String)`, `EpisodeCount(Integer)`, `DateRange(LocalDate, LocalDate)`, `Genre`
- User-related: `UserId(Integer)`, `Username(String)`, `Password(String)`, `HashedPassword(String)`, `Token(String)`
- UserShow-related: `Progress(Integer)`, `Status(enum)`, `Score(Integer)`, `AverageScore(Double)`

**Domain Models:**
- `Show` (sealed abstract class)
  - `Movie` (final) - has `LocalDate releaseDate`
  - `TvSeries` (final) - has `EpisodeCount` and `DateRange airingPeriod`
- `User` (record) - has `Username` and `HashedPassword`
- `UserShow` (record) - combines `Show`, `Progress`, `Status`, and `Score`

### Persistence Layer

**JPA Inheritance Strategy:**
Uses JOINED inheritance with separate tables:
- `show` table (base table with uuidv7() IDs)
- `movie` table (references show.id)
- `tv_series` table (references show.id)

**Entity Mapping:**
- `ShowEntity` (abstract) -> `MovieEntity`, `TvSeriesEntity`
- `ShowEntityMapper` converts entities to domain models using pattern matching
- Domain models are framework-agnostic (no JPA annotations)

**Repository:**
- `ShowRepository extends JpaRepository<ShowEntity, UUID>`

## Testing Conventions

Tests follow **given-when-then** structure with Mockito and AssertJ:

```java
@Test
void should_do_something() {
    // given
    ForFetchingShows forLoadingShows = mock(ForFetchingShows.class);
    when(forLoadingShows.fetch()).thenReturn(...);

    // when
    var result = service.method();

    // then
    assertThat(result).isNotNull();
}
```

**Test Naming:**
- Use snake_case: `should_fetch_shows`, `should_map_movie_entity_to_movie`
- Start with `should_` to describe expected behavior

**Test Organization:**
- Unit tests for services mock port interfaces
- Unit tests for mappers are pure (no Spring context)
- Domain model tests verify validation logic

## Database Migrations

Flyway migrations are in `src/main/resources/db/migration/`:
- V1 - Creates `show` table (base table with uuidv7() IDs)
- V2 - Creates `movie` table (references show.id)
- V3 - Creates `tv_series` table (references show.id)
- V4 - Creates `genre` table
- V5 - Creates `show_genre` junction table
- V6 - Creates `user` table (serial ID, bcrypt password hash)
- V7 - Creates `user_show` table (tracks user's shows with progress, status, score)

When adding new migrations, use the pattern: `V{number}__{description}.sql`

## Application Configuration

- Main config: `src/main/resources/application.yml`
  - Defines API path structure using Spring property placeholders (e.g., `service.api.show.path`)
  - Configures JWT settings (`service.jwt.base64-secret`, `service.jwt.expiration`)
- Local profile: `src/main/resources/application-local.yml`
  - Database connection to local PostgreSQL
- Run with local profile: `-Dspring-boot.run.profiles=local`
- **Environment Variables Required:**
  - `JWT_SECRET` - Base64-encoded JWT signing secret

## Key Architectural Principles

1. **Dependency Inversion**: Services depend on port interfaces, never on implementations
2. **Immutability**: Domain models use records and final classes
3. **Validation at Boundaries**: Value objects validate in constructors
4. **No Domain Pollution**: Domain models have no framework dependencies
5. **Static Mappers**: Use utility classes with private constructors for mapping
6. **Exhaustive Pattern Matching**: Sealed types ensure compiler-verified completeness

## REST API Structure

The application exposes a RESTful API with path configuration in `application.yml`:

**Base Path:** `/api/v1`

**Endpoints:**
- `POST /api/v1/register` - User registration (public)
- `POST /api/v1/login` - User login (public)
- `GET /api/v1/shows` - List all shows (authenticated)
- `GET /api/v1/user-shows` - Get user's show collection (authenticated)
- `POST /api/v1/user-shows` - Add/update show in user's collection (authenticated)
- `GET /api/v1/shows/{showId}/reviews` - Get reviews for a show (authenticated)
- `POST /api/v1/shows/{showId}/reviews` - Add review (authenticated)

**Controller Conventions:**
- Controllers use `@RequestMapping` with path from properties (e.g., `${service.api.show.path}`)
- Request/Response DTOs are separate from domain models
- Mappers convert between DTOs and domain models
- Controllers inject inbound port interfaces, not services directly

## Security and Authentication

The application uses JWT-based authentication with Spring Security:

**Security Configuration:**
- `SecurityConfig` (adapter/in/auth/) configures the security filter chain
- Public endpoints: `/api/v1/login`, `/api/v1/register`
- All other endpoints require JWT authentication
- `JwtAuthFilter` validates JWT tokens before each request

**Authentication Flow:**
1. User registers via `/api/v1/register` (hashes password with BCrypt)
2. User logs in via `/api/v1/login` (returns JWT token)
3. Client includes JWT in `Authorization: Bearer <token>` header
4. `JwtAuthFilter` validates token and sets Spring Security context
5. Controllers access user via `@AuthenticationPrincipal UserDetails`

**Dependency Injection:**
- `DomainConfig` wires up domain services with their port dependencies
- Services are registered as Spring beans and injected into controllers
- Port interfaces are implemented by adapter classes (e.g., `ShowLoader`, `PasswordHasher`)

## Current Implementation Status

**Implemented:**
- Complete domain model layer (Show, Movie, TvSeries, User, UserShow)
- Service layer with port interfaces (ShowService, AuthService, UserShowService)
- Persistence layer (JPA entities, repositories, mappers)
- Database schema via Flyway (7 migrations)
- REST API layer with controllers for auth, shows, and user-shows
- JWT-based authentication and authorization
- Spring Security configuration
- Dependency injection wiring (DomainConfig)
- Unit tests for core components

**In Progress:**
- Review endpoints (controller exists but not fully implemented)

**Not Yet Implemented:**
- Integration tests
- API documentation (OpenAPI/Swagger)