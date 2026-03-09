# 🏦 Banking Transfers

Sistema de transferencias bancarias construido con **Spring Boot**, **Apache Camel** y **Oracle Database**, siguiendo arquitectura **Hexagonal** y principios de **Domain Driven Design (DDD)**.

---

## 📋 Índice

- [Descripción](#descripción)
- [Stack tecnológico](#stack-tecnológico)
- [Arquitectura](#arquitectura)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Cómo arrancar](#cómo-arrancar)
- [API REST](#api-rest)
- [Flujo interno](#flujo-interno)
- [Mejoras futuras](#mejoras-futuras)

---

## 📖 Descripción

Banking Transfers es un microservicio bancario que gestiona transferencias entre cuentas. Recibe una orden de transferencia via REST, la orquesta a través de Apache Camel, aplica la lógica de negocio en el dominio y persiste el resultado en Oracle Database.

Este proyecto simula cómo funciona internamente un microservicio real dentro de un sistema bancario, donde Camel actúa como capa de integración entre sistemas.

---

## 🛠️ Stack tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 25 | Lenguaje |
| Spring Boot | 4.0.3 | Framework principal |
| Apache Camel | 4.8.0 | Orquestación de flujos |
| Oracle XE | 21c | Base de datos |
| Docker | - | Oracle en local |
| Hibernate | 7.2.4 | ORM |
| Maven | - | Gestión de dependencias |

---

## 🏗️ Arquitectura

El proyecto sigue la **arquitectura hexagonal (Ports & Adapters)**. El dominio está completamente aislado de la infraestructura y no conoce ningún detalle técnico de Spring, JPA ni Camel.
```
┌─────────────────────────────────────────────────────┐
│                   INFRAESTRUCTURA                   │
│                                                     │
│  REST API ──► Camel Route ──► Puerto de entrada     │
│                                    │                │
│              ┌─────────────────────▼─────────────┐  │
│              │           DOMINIO                 │  │
│              │                                   │  │
│              │   Account  Transfer  Money        │  │
│              │                                   │  │
│              │   TransferUseCase (puerto)        │  │
│              └─────────────────────┬─────────────┘  │
│                                    │                │
│              Puerto de salida ◄────┘                │
│                    │                                │
│              JPA Adapter ──► Oracle DB              │
└─────────────────────────────────────────────────────┘
```

### Principios aplicados

**Arquitectura Hexagonal** — El dominio define interfaces (puertos) que la infraestructura implementa. Si mañana cambias Oracle por PostgreSQL, el dominio no se toca.

**DDD** — Las reglas de negocio viven en el dominio. `Account` sabe hacer `debit` y `credit`. `Money` valida que el importe no sea negativo. El dominio protege sus propios invariantes.

**Apache Camel** — Actúa como capa de integración. La ruta `direct:transfer` recibe la petición, la orquesta y gestiona los errores de forma centralizada.

---

## 📁 Estructura del proyecto
```
com/banking/
├── domain/
│   ├── model/
│   │   ├── Account.java           # Entidad cuenta bancaria
│   │   ├── Transfer.java          # Entidad transferencia
│   │   ├── Money.java             # Value object importe
│   │   └── TransferStatus.java    # Estados: PENDIENTE, COMPLETADA, FALLIDA
│   ├── port/
│   │   ├── in/
│   │   │   └── TransferUseCase.java       # Puerto de entrada
│   │   └── out/
│   │       ├── AccountRepository.java     # Puerto de salida
│   │       └── TransferRepository.java    # Puerto de salida
│   └── exception/
│       ├── AccountNotFoundException.java
│       └── InsufficientFundsException.java
├── application/
│   └── usecase/
│       └── TransferUseCaseImpl.java   # Implementación del caso de uso
└── infrastructure/
    ├── adapter/
    │   ├── in/
    │   │   └── camel/
    │   │       ├── TransferController.java  # Endpoint REST
    │   │       ├── TransferRoute.java       # Ruta Camel
    │   │       └── TransferRequest.java     # DTO de entrada
    │   └── out/
    │       └── persistence/
    │           ├── AccountEntity.java              # Entidad JPA
    │           ├── TransferEntity.java             # Entidad JPA
    │           ├── AccountJpaRepository.java       # Spring Data
    │           ├── TransferJpaRepository.java      # Spring Data
    │           ├── AccountRepositoryAdapter.java   # Adaptador dominio-JPA
    │           └── TransferRepositoryAdapter.java  # Adaptador dominio-JPA
    └── config/
        └── DataInitializer.java   # Carga datos de prueba al arrancar
```

---

## 🚀 Cómo arrancar

### Requisitos previos

- Java 25
- Maven
- Docker Desktop

### 1. Levantar Oracle con Docker
```powershell
docker run -d --name oracle-xe -p 1521:1521 -e ORACLE_PASSWORD=Password123 gvenzl/oracle-xe:21-slim
```

Espera hasta ver en los logs:
```powershell
docker logs -f oracle-xe
# Espera: DATABASE IS READY TO USE!
```

### 2. Configuración de base de datos

El archivo `src/main/resources/application.properties` ya viene configurado para conectar con Oracle Docker:
```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=system
spring.datasource.password=Password123
```

### 3. Arrancar la aplicación

Desde IntelliJ ejecuta `BankingTransfersApplication.java` o desde terminal:
```powershell
mvn spring-boot:run
```

Al arrancar verás en los logs:
```
Hibernate: create table accounts
Hibernate: create table transfers
HikariPool-1 - Added connection oracle.jdbc.driver.T4CConnection
Started BankingTransfersApplication
```

### 4. Datos de prueba

Al arrancar se crean automáticamente dos cuentas de prueba:

| ID | Titular | Saldo | Moneda |
|---|---|---|---|
| acc-001 | Carlos García | 5000.00 | EUR |
| acc-002 | María López | 3000.00 | EUR |

---

## 🔌 API REST

### POST /api/transfers

Ejecuta una transferencia entre dos cuentas.

**Request:**
```json
{
    "sourceAccountId": "acc-001",
    "targetAccountId": "acc-002",
    "amount": 100.00,
    "currency": "EUR"
}
```

**Response 200:**
```json
{
    "id": "0934bb8c-8e29-4d3f-b8fa-e343519c9670",
    "sourceAccountId": "acc-001",
    "targetAccountId": "acc-002",
    "amount": {
        "amount": 100.00,
        "currency": "EUR"
    },
    "status": "COMPLETADA",
    "createdAt": "2026-03-09T13:36:14"
}
```

**Probar desde PowerShell:**
```powershell
$body = '{"sourceAccountId":"acc-001","targetAccountId":"acc-002","amount":100.00,"currency":"EUR"}'
Invoke-WebRequest -Uri "http://localhost:8080/api/transfers" -Method POST -ContentType "application/json" -Body $body -UseBasicParsing
```

---

## 🔄 Flujo interno

Cuando llega una petición POST esto es lo que ocurre internamente:
```
1. TransferController     Recibe la petición HTTP y la envía a Camel
2. TransferRoute          Camel orquesta el flujo y gestiona errores
3. TransferUseCaseImpl    Busca las cuentas y aplica la lógica
4. Account (dominio)      Ejecuta debit() en origen y credit() en destino
5. AccountRepository      Persiste los nuevos saldos en Oracle
6. TransferRepository     Guarda la transferencia con estado COMPLETADA
7. TransferController     Devuelve la respuesta HTTP 200
```

---

## 🔮 Mejoras futuras

- **Seguridad** — Autenticación con JWT/OAuth2
- **Transacciones** — `@Transactional` para garantizar atomicidad
- **Validaciones** — Verificar cuentas, importes y monedas
- **Tests** — Unitarios de dominio e integración de rutas Camel
- **Cola de mensajes** — Integrar Kafka o ActiveMQ como entrada en lugar de REST
- **Logs de auditoría** — Registro estructurado para cumplimiento regulatorio
- **Integración SWIFT/SEPA** — Conexión con redes de pagos internacionales
