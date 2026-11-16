# Worksnetz

A small job and freelance network. Companies and individuals post openings, registered users apply with a short message, and owners see the applications they received.

**Stack:** Java 17+, Spring Boot 4.1, Spring MVC + Thymeleaf, Spring Security (form login, BCrypt), Spring Data JPA, H2 (default) or PostgreSQL.

## Features
- Registration and login with validation and hashed passwords
- Job board with search by title or company
- Post, view and delete jobs (only the owner can delete)
- Apply to jobs (one application per user and job, not to your own posting)
- Personal dashboard: own postings with application counts, own applications
- CSRF protection, dark mode aware layout

## Run it
```bash
./mvnw spring-boot:run          # Windows: .\mvnw spring-boot:run
```
Open http://localhost:8080 and log in with the demo account `demo` / `demo1234`.
The default profile uses an in-memory H2 database, so nothing needs to be installed. Data resets on every restart.

### PostgreSQL
```bash
set DB_URL=jdbc:postgresql://localhost:5432/worksnetz
set DB_USER=worksnetz
set DB_PASSWORD=your-password
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

## Project layout
```
com.worksnet
├── model      JPA entities (AppUser, Job, JobApplication, JobType)
├── repo       Spring Data repositories
├── security   SecurityConfig (filter chain, BCrypt, UserDetailsService)
└── web        Controllers and form objects
```
