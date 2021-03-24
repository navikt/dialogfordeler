Dialogfordeler
================

Dialogfordeler skal rute meldinger til og fra lege til riktig fagsystem

# Komme i gang
### Legg inn følgende properties i application-local.properties:
```
mqgateway03.hostname={Hostname til MQGateway}
mqgateway03.name={Navn på MQGateway}
mqgateway03.port={Port til MQGateway}
dialogfordeler.channel.name={Navn på channel mot MQGateway}

dialogfordeler.dialogmeldinger.queuename={Kønavn til Dialogfordeler-kø}
mottak.queue.eia2.meldinger.queuename={Kønavn til Eia-kø}
mottak.queue.utsending.queuename={Kønavn til eMottak-kø}

spring.datasource.url={URL til database}
spring.datasource.username={Brukernavn til database}
spring.datasource.password={Passord til database}
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver

security-token-service-jwks.url={URL til STS JWKS}
security-token-service-issuer.url={URL til STS ISSUER}

abac.url={URL til ABAC}
serviceuser.username={Brukernavn servicebruker}
serviceuser.password={Passord servicebruker}
```
### Kjør i IntelliJ
Kjør `main()` i `Application.java`.\
Husk og aktivere profilen "local", hvis dette er første gangen appen startes.

### Kjør i kommandolinje
```
mvn spring-boot:run -Dspring.profiles.active=local
```

---

### Alerterator
Dialogfordeler er satt opp med alerterator, slik når appen er nede vil det sendes en varsling til Slack kanalene #syfo-alarm.
Spec'en for alerts ligger i filen alerts.yaml. Hvis man ønsker å forandre på hvilke varsler som skal sendes må man forandre
på alerts.yaml og deretter kjøre:
`kubectl apply -f .nais/alerts.yaml`.
For å se status på dialogfordeler alerts kan man kjøre:
`kubectl describe alert dialogfordeler-alerts`.
Dokumentasjon for Alerterator ligger her: https://doc.nais.io/observability/alerts

## Hente pakker fra Github Package Registry
Noen pakker hentes fra Github Package Registry som krever autentisering.
Pakkene kan lastes ned via build.gradle slik:
```
val githubUser: String by project
val githubPassword: String by project
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/navikt/syfotjenester")
        credentials {
            username = githubUser
            password = githubPassword
        }
    }
}
```

`githubUser` og `githubPassword` settes i `~/.gradle/gradle.properties`:

```
githubUser=x-access-token
githubPassword=<token>
```

Hvor `<token>` er et personal access token med scope `read:packages`(og SSO enabled).

Evt. kan variablene kan også konfigureres som miljøvariabler eller brukes i kommandolinjen:

* `ORG_GRADLE_PROJECT_githubUser`
* `ORG_GRADLE_PROJECT_githubPassword`

```
./gradlew -PgithubUser=x-access-token -PgithubPassword=[token]
```

# Henvendelser

Spørsmål knyttet til koden kan rettes mot:

* Anders Østby, [anders.ostby@nav.no](mailto:anders.ostby@nav.no)

Spørsmål knyttet til prosjektet kan rettes mot:
* DigiSYFO: [nav.digisyfo@nav.no](mailto:nav.digisyfo@nav.no)
