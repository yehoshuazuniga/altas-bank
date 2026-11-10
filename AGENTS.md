# AGENTS.md

Atlas Bank: backend Spring Boot 4.1.1 / Java 21, hexagonal (ports & adapters) + DDD.
Comments, log lines and exception messages are in Spanish (rioplatense); keep that style.

## Commands

    ./mvnw test                  # full suite (only verification gate: no lint/format/CI config)
    ./mvnw test -Dtest=MoneyDomainTest
    ./mvnw test -Dtest=HexagonalArchitectureTest,NamingConventionTest,NoCyclicDependenciesTest,SecurityIsolationTest
    ./mvnw spring-boot:run       # serves on :8085

- Use JDK 21 (or 25). The machine default `java` is 27 and Lombok 1.18.46 fails at compile
  (`ExceptionInInitializerError: com.sun.tools.javac.tree.EndPosTable`) — export a compatible `JAVA_HOME` first.
- Boot 4 splits starters: use `spring-boot-starter-webmvc`, `spring-boot-h2console`,
  `spring-boot-starter-{data-jpa,validation,webmvc}-test`. Do not add Boot 3 names (`spring-boot-starter-web`, `-test`).
- Only annotation processors generate code (Lombok, MapStruct impls under `target/generated-sources`); no codegen/migration step.

## Architecture (enforced by ArchUnit, not convention)

- `domain` is pure Java (Lombok allowed; no Spring, JPA or Spring Security).
- `application` must not import `infrastructure` or Spring Security; it defines use-case ports, commands,
  `@Service`/`@Transactional` implementations, query read models and facades.
- `infrastructure` holds adapters (`adapter.in.rest|ai|listener`, `adapter.out.persistence|fraud`) and `config`.
- Placement rules: `*Controller` under `infrastructure.adapter.in.*`, `*UseCase` interfaces in
  `application.port.in`, every type in `application.port.out` must be an interface.
- The domain has no JPA: `adapter.out.persistence` keeps its own entities plus its own `Money`/`Email`
  `@Embeddable` copies, converted manually by `*PersistenceMapper`. Watch the import collision with
  `domain.model.shared.Money|Email`. MapStruct is only for REST DTOs.
- Domain objects are not Spring beans; register them in `infrastructure.config.DomainBeanConfig`.
  Fee calculators and transfer validators are injected as ordered `List<...>` via `@Order` — adding a bean changes execution order.
- Writes: controller -> `*UseCase` -> service; events go through `ApplicationEventPublisher` and are handled in
  `adapter.listener`. Reads: `application.query` models via `*QueryService`/`AccountDashboardFacade`.
- Security: JWT authorities come from `realm_access.roles` with no `ROLE_` prefix (prefer `hasAuthority("ADMIN")`;
  `hasRole` requires `ROLE_*` role names in Keycloak). `@EnableMethodSecurity` is absent, so `@PreAuthorize`
  on controllers is inert — put rules in `SecurityConfig`. Its transaction matchers use `/api/v1/transactions/**`
  while `TransactionController` is mapped at `/api/v1/transaction` (singular).
- Spring AI: `ChatClient` bean in `AiAgentConfig`, tools in `AtlasBankTools`, Ollama at `localhost:11434`
  model `qwen3:8b` (api-key is a placeholder); `/api/v1/ai/**` is `permitAll`.

## Runtime

- H2 in-memory `jdbc:h2:mem:atlasbank`, `ddl-auto: create-drop`, no migrations or seed data: schema comes from
  the JPA entities and data resets on every restart. H2 console at `/h2-console`.
- `docker/docker-compose.yaml` only provides Keycloak 26 + Postgres. Keycloak is published on **8181**, but
  `application.yaml` expects issuer `http://localhost:8080/realms/atlas-bank`; the realm is not imported and
  `keycloak_data` has no top-level `volumes:` entry. Align port/realm (or override
  `spring.security.oauth2.resourceserver.jwt.issuer-uri`) before starting.
- Only `AtlasBankApplicationTests` boots Spring, so it needs that issuer reachable; ArchUnit and domain unit
  tests are context-free and are the fast check.

## Do not "fix" silently

Existing typos are part of the API and call sites: `FeeCalcuilator`, `AccountrepositoryPort`, `ListAccountuseCase`,
`TransferDomainService.trasnfer`, `TransactionFactory.createTarnsfer`, `/api/v1/accounts/{id}/dasboard`.
