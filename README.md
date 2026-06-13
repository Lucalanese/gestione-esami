# Microservizio Gestione Esami

## Panoramica

Questo Microservizio è responsabile della gestione completa degli esami all'interno della piattaforma universitaria, fornendo funzionalità per la pianificazione, iscrizione, conduzione e valutazione degli esami:

- **(Amministrativi)** Pianificazione e gestione del calendario esami
- **(Studenti)** Iscrizione agli esami e visualizzazione risultati
- **(Docenti)** Registrazione voti e gestione delle valutazioni
- **(Tutti)** Visualizzazione del calendario esami e consultazione informazioni

## Porta di Default

```
Server: http://localhost:23110
Swagger UI: http://localhost:23110/swagger-ui.html
```

## Tech Stack

- **Framework:** Spring Boot 3.5.x
- **Message Broker:** RabbitMQ
- **Database:** PostgreSQL (Docker)
- **API Documentation:** Swagger/OpenAPI 3.0
- **Sicurezza:** JWT con RSA-2048 (Gestione Utenti e Ruoli)

## Avvio del Progetto

### Prerequisiti
- Java 21
- Docker

### Ordine di avvio

**1. Avvia RabbitMQ con Docker:**
```
docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:management
```

**2. Avvia PostgreSQL con Docker:**
```
docker run -d --name postgres-esami -e POSTGRES_DB=esami_db -e POSTGRES_USER=esami_user -e POSTGRES_PASSWORD=esami_pass -p 5433:5432 postgres:16
```

**3. Avvia Gestione Utenti e Ruoli (Mauro Cavasinni) — porta 23109**

**4. Avvia Gestione Esami — porta 23110**

**5. Avvia unimol-stubs — porta 23200**

### Dashboard RabbitMQ
```
http://localhost:15672
username: guest
password: guest
```

## Stub Microservizi

I seguenti microservizi non erano disponibili al momento dello sviluppo e sono stati sostituiti con stub che pubblicano dati finti su RabbitMQ:

| Microservizio | Responsabile | Stato |
|---|---|---|
| Gestione Corsi | Marco Gianfagna | ❌ Stubbato |
| Pianificazione Orari e Aule | Simone Filippone | ❌ Stubbato |

Lo stub è disponibile nella repo [unimol-stubs](https://github.com/Lucalanese/unimol-stubs).

## Credenziali di Test

Per testare il microservizio sono necessari utenti creati su Gestione Utenti e Ruoli:

| Ruolo | Username | Password |
|---|---|---|
| Super Admin | admin | password123 |
| Studente | studente1 | password123 |
| Docente | professore1 | password123 |

**Inizializzazione:**
```
POST http://localhost:23109/api/v1/users/init/superadmin
POST http://localhost:23109/api/v1/auth/login
```

## JWT Authentication

I token JWT vengono generati da Gestione Utenti e Ruoli e validati tramite chiave pubblica RSA-2048.

- **Scadenza:** 3600 secondi (1 ora)
- **Algoritmo:** RS256

### Payload del Token

| Campo | Descrizione |
|---|---|
| sub | Matricola utente |
| role | Ruolo (`sadmin`, `admin`, `teach`, `student`) |
| username | Nome utente |
| exp | Scadenza (Unix timestamp) |

## Modello Dati

### Entità principali JPA

#### Exam (Esame)
- `id` - ID esame
- `name` - Nome dell'esame
- `date` - Data dell'esame
- `time` - Orario dell'esame
- `course_id` - ID corso (riferimento esterno)
- `professor_id` - ID professore (riferimento esterno)
- `classroom_id` - ID dell'aula
- `max_students` - Numero massimo studenti ammessi
- `enrollment_deadline` - Data limite per le iscrizioni
- `status` - Stato (enum: SCHEDULED, ONGOING, COMPLETED, CANCELLED)
- `notes` - Note aggiuntive sull'esame
- Relazione one-to-many con `exam_enrollments`

#### ExamEnrollment (Iscrizione Esame)
- `id` - ID iscrizione
- `exam_id` - ID esame (FK)
- `student_id` - ID studente (riferimento esterno)
- `enrollment_date` - Data iscrizione
- `status` - Stato (enum: ENROLLED, WITHDREW, REJECTED, COMPLETED)
- `notes` - Note studente
- Relazione many-to-one con `exams`
- Relazione one-to-one con `exam_grades`

#### ExamGrade (Voto Esame)
- `id` - ID voto
- `enrollment_id` - ID iscrizione (FK)
- `grade` - Voto (0-30)
- `honors` - Con lode (boolean)
- `status` - Stato del voto
- `evaluation_date` - Data valutazione
- `professor_id` - ID del professore che ha registrato il voto
- `feedback` - Feedback dettagliato
- `published_date` - Data pubblicazione
- Relazione one-to-one con `exam_enrollments`

## API REST

### Exams Endpoint

#### Gestione Esami (per Amministrativi)

```
#############################################
# Crea nuovo esame
# @func: createExam()
# @param: CreateExamRequest examRequest
# @return: ResponseEntity<ExamDTO>
#############################################
POST    /api/v1/exams

#############################################
# Lista tutti gli esami
# @func: getAllExams()
# @return: ResponseEntity<List<ExamDTO>>
#############################################
GET     /api/v1/exams

#############################################
# Dettaglio singolo esame
# @func: getExamById()
# @param: Long id
# @return: ResponseEntity<ExamDTO>
#############################################
GET     /api/v1/exams/{id}

#############################################
# Aggiorna esame
# @func: updateExam()
# @param: Long id, UpdateExamRequest examRequest
# @return: ResponseEntity<ExamDTO>
#############################################
PUT     /api/v1/exams/{id}

#############################################
# Elimina esame
# @func: deleteExam()
# @param: Long id
# @return: ResponseEntity<Void>
#############################################
DELETE  /api/v1/exams/{id}

#############################################
# Esami per corso
# @func: getExamsByCourse()
# @param: Long courseId
# @return: ResponseEntity<List<ExamDTO>>
#############################################
GET     /api/v1/exams/course/{courseId}

#############################################
# Esami per professore
# @func: getExamsByProfessor()
# @param: Long professorId
# @return: ResponseEntity<List<ExamDTO>>
#############################################
GET     /api/v1/exams/professor/{professorId}
```

#### Visualizzazione Calendario (per Tutti)

```
#############################################
# Calendario esami pubblico
# @func: getExamCalendar()
# @return: ResponseEntity<List<ExamCalendarDTO>>
#############################################
GET     /api/v1/exams/calendar

#############################################
# Esami disponibili per iscrizione
# @func: getAvailableExams()
# @return: ResponseEntity<List<ExamDTO>>
#############################################
GET     /api/v1/exams/available
```

### Enrollments Endpoint

#### Gestione Iscrizioni (per Studenti)

```
#############################################
# Iscrizione a esame
# @func: enrollToExam()
# @param: Long examId, EnrollmentRequest request
# @return: ResponseEntity<EnrollmentDTO>
#############################################
POST    /api/v1/enrollments/{examId}/enroll

#############################################
# Le mie iscrizioni
# @func: getMyEnrollments()
# @return: ResponseEntity<List<EnrollmentDTO>>
#############################################
GET     /api/v1/enrollments/my

#############################################
# Dettaglio iscrizione
# @func: getEnrollmentById()
# @param: Long id
# @return: ResponseEntity<EnrollmentDTO>
#############################################
GET     /api/v1/enrollments/{id}

#############################################
# Cancella iscrizione
# @func: cancelEnrollment()
# @param: Long id
# @return: ResponseEntity<Void>
#############################################
DELETE  /api/v1/enrollments/{id}
```

#### Gestione Iscrizioni (per Amministrativi)

```
#############################################
# Iscrizioni per esame
# @func: getExamEnrollments()
# @param: Long examId
# @return: ResponseEntity<List<EnrollmentDTO>>
#############################################
GET     /api/v1/exams/{examId}/enrollments

#############################################
# Aggiorna stato iscrizione
# @func: updateEnrollmentStatus()
# @param: Long enrollmentId, UpdateStatusRequest request
# @return: ResponseEntity<EnrollmentDTO>
#############################################
PUT     /api/v1/enrollments/{enrollmentId}/status

#############################################
# Tutte le iscrizioni
# @func: getAllEnrollments()
# @return: ResponseEntity<List<EnrollmentDTO>>
#############################################
GET     /api/v1/enrollments

#############################################
# Iscrizioni per studente
# @func: getStudentEnrollments()
# @param: Long studentId
# @return: ResponseEntity<List<EnrollmentDTO>>
#############################################
GET     /api/v1/enrollments/student/{studentId}
```

### Grades Endpoint

#### Gestione Voti (per Docenti)

```
#############################################
# Registra voto
# @func: recordGrade()
# @param: Long examId, CreateGradeRequest request
# @return: ResponseEntity<GradeDTO>
#############################################
POST    /api/v1/exams/{examId}/grades

#############################################
# Voti per esame
# @func: getExamGrades()
# @param: Long examId
# @return: ResponseEntity<List<GradeDTO>>
#############################################
GET     /api/v1/exams/{examId}/grades

#############################################
# Dettaglio voto
# @func: getGradeById()
# @param: Long id
# @return: ResponseEntity<GradeDTO>
#############################################
GET     /api/v1/grades/{id}

#############################################
# Aggiorna voto
# @func: updateGrade()
# @param: Long id, CreateGradeRequest request
# @return: ResponseEntity<GradeDTO>
#############################################
PUT     /api/v1/grades/{id}

#############################################
# Elimina voto
# @func: deleteGrade()
# @param: Long id
# @return: ResponseEntity<Void>
#############################################
DELETE  /api/v1/grades/{id}
```

#### Visualizzazione Voti (per Studenti)

```
#############################################
# I miei voti
# @func: getMyGrades()
# @return: ResponseEntity<List<GradeDTO>>
#############################################
GET     /api/v1/grades/my

#############################################
# Voti per studente (admin/docente)
# @func: getStudentGrades()
# @param: Long studentId
# @return: ResponseEntity<List<GradeDTO>>
#############################################
GET     /api/v1/grades/student/{studentId}
```

## Comunicazione RabbitMQ

### Published Events
- `exam.created` - Quando viene creato un nuovo esame
- `exam.updated` - Quando un esame viene modificato
- `exam.deleted` - Quando un esame viene eliminato
- `exam.enrollment.requested` - Quando uno studente si iscrive
- `exam.grade.recorded` - Quando viene registrato un voto

### Consumed Events
- `user.updated` - Aggiorna le informazioni degli utenti (da Mauro)
- `course.updated` - Aggiorna le informazioni dei corsi (stub)
- `course.deleted` - Gestisce la cancellazione di corsi
- `classroom.updated` - Aggiorna le informazioni delle aule (stub)

## Sicurezza e Autorizzazioni

| Ruolo | Permessi |
|---|---|
| ROLE_ADMINISTRATIVE | Pianifica/modifica/elimina esami, gestisce iscrizioni, visualizza tutti i voti |
| ROLE_PROFESSOR | Registra e modifica voti, visualizza iscrizioni dei propri esami |
| ROLE_STUDENT | Si iscrive agli esami, visualizza calendario, consulta proprie iscrizioni e voti |

### Controlli di Sicurezza
- Validazione date esami (non nel passato)
- Controllo iscrizione doppia (409 Conflict)
- Controllo limite massimo iscrizioni
- Validazione voto nel range 0-30
- Blocco eliminazione esame con iscrizioni attive
- ID utente sempre estratto dal token JWT (mai dai parametri)