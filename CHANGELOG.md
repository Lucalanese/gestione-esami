# Changelog

## v1.0.4 - Sicurezza Kubernetes

### Aggiunto
- Secret Kubernetes per RabbitMQ (rabbitmq-secret.yml)
- Credenziali RabbitMQ gestite tramite Secret in rabbitmq-deployment.yml e app-deployment.yml

## v1.0.3 - Fix Typo

### Corretto
- Typo jdbc:postgreql → jdbc:postgresql in application.properties
- Typo .mwn/ → .mvn/ in .dockerignore

## v1.0.2 - Test Aggiuntivi

### Aggiunto
- Test per EnrollmentConverter, GradeConverter ed ExamEntity (31 test totali)
- Validazione lode in GradeService (lode solo con voto 30)

## v1.0.1 - Fix e Qualità

### Aggiunto
- spotbugs-exclude.xml per escludere falsi positivi Spring Boot
- Pipeline CD per release automatiche con tag

### Corretto
- Bug variabile non usata in GradeService.deleteGrade()

## v1.0.0 - Prima Release Stabile

### Aggiunto
- Gestione esami con CRUD completo
- Sistema di iscrizioni agli esami
- Registrazione e gestione voti
- Pipeline CI con GitHub Actions
- Analisi statica (Checkstyle, PMD, SpotBugs, JaCoCo)
- Dockerfile con multi-stage build
- docker-compose con PostgreSQL e RabbitMQ
- Manifest Kubernetes
- 19 test unitari

## v1.0.5 Fix Dockerfile

### Corretto 
- Typo eclipse-temutin in Dockerfile → eclipse-temurin
- Verificato con docker build completato con successo