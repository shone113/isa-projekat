# OnlyBuns

Veb aplikacija društvene mreže posvećene kunićima, urađena u okviru predmeta
**Internet softverske arhitekture (ISA)** (2025).

## O projektu

OnlyBuns je društvena mreža na kojoj korisnici objavljuju slike svojih kunića, lajkuju i komentarišu objave,
prate druge korisnike i dopisuju se u grupnim ćaskanjima. Objave imaju lokaciju, a sistem uključuje i
organizacije za brigu o kunićima, kojima se objave prosleđuju preko reda poruka. Administrator ima uvid u
analitiku korišćenja.

Projekat je namerno građen oko tema skalabilnosti i performansi: keširanje, ograničavanje broja zahteva,
konkurentni pristup podacima, asinhrona komunikacija, load balancer i praćenje metrika.

## Struktura repozitorijuma

- `OnlyBuns` – glavna Spring Boot aplikacija (backend)
- `SimpleMQ` – sopstvena jednostavna biblioteka za red poruka
- `load-balancer` – server za raspodelu opterećenja
- `rabbitmq-producer-example`, `rabbitmq-consumer-example` – primeri komunikacije preko RabbitMQ-a
  (organizacije za brigu o kunićima)

## Tehnologije

- Java 17, Spring Boot 2.7, Spring Security (JWT), Spring Data JPA, WebSocket
- PostgreSQL (H2 za testove)
- RabbitMQ (Spring AMQP)
- Ehcache (JCache), Resilience4j
- Micrometer, Actuator, Prometheus
- Thumbnailator (kompresija slika)
- Swagger / OpenAPI

## Moj doprinos

### 1. Kompresija starih slika
- Zakazani zadatak (`ImageCompressionService`, `@Scheduled`) pronalazi slike starije od 3 dana i čuva njihovu
  kompresovanu verziju (kvalitet 60%) u poseban direktorijum, bez ponovne obrade već kompresovanih slika
  (biblioteka Thumbnailator).
- `ImageController` (`POST api/image/upload`) i izmene u `ImageService` za čuvanje otpremljenih slika.

### 2. Nedeljni izveštaj o aktivnosti i obaveštenja o neaktivnosti
- `NotificationService` periodično pravi snimak statistike svakog korisnika (broj lajkova i pratilaca) i čuva ga
  u entitetu `UserStatsSnapshot`.
- Korisnicima koji se nisu prijavili 7 dana šalje se email sa pregledom novih lajkova i pratilaca u tom periodu
  (poređenjem poslednjeg i starijeg snimka).
- `SchedulerConfig` – pool niti za zakazane zadatke.

### 3. Lokacije objava i keširanje
- Entitet `Location` i čuvanje koordinata objave (geografska širina i dužina).
- Keširanje lokacija objava (`@Cacheable`, `@CachePut`, `@CacheEvict`, keš `postLocations` u `ehcache.xml`),
  uz ažuriranje keša pri izmeni lokacije.

### 4. Slanje objava organizacijama za brigu o kunićima
- `AdPostMessageDTO` i `PostService.sendPost` šalju podatke o objavi (opis, datum, autor) na RabbitMQ
  exchange (`RabbitConfig`).

### 5. Ograničenje broja komentara
- Korisnik može da ostavi najviše **60 komentara na sat**; prekoračenje baca `RateLimitExceededException`
  (`CommentService`, upit `countByCreatorInLastHour`).

### 6. Konkurentno lajkovanje objava
- Izmene u `PostService` i `IPostRepository` (atomski upiti za povećanje i smanjenje broja lajkova, provera da li
  je objava već lajkovana, obrada optimistic locking izuzetaka) kako bi lajkovanje bilo ispravno pri istovremenom
  pristupu.
- Test `PostServiceConcurrencyTest` pokreće 20 niti koje istovremeno lajkuju istu objavu.

### 7. Praćenje metrika
- Metrika `active_users` (`ActiveUserMetrics`, Micrometer) koja prati broj trenutno prijavljenih korisnika.
- Prometheus endpoint kroz Actuator, otvoren posebnim sigurnosnim lancem (`ActuatorSecurityConfig`).

## Pokretanje

1. Pokrenuti PostgreSQL i RabbitMQ (`localhost:5672`).
2. U `OnlyBuns/src/main/resources/application.properties` podesiti pristup bazi i email nalogu.
3. Pokrenuti aplikaciju:
   ```
   cd OnlyBuns
   ./mvnw spring-boot:run
   ```
4. Za metrike: Prometheus endpoint je dostupan na `/actuator/prometheus`.
