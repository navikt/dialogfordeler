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

security-token-service-jwks.url={URL til STS ISSUER}
security-token-service-issuer.url={URL til STS JWKS}

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

# Henvendelser

Spørsmål knyttet til koden kan rettes mot:

* Anders Østby, [anders.ostby@nav.no](mailto:anders.ostby@nav.no)

Spørsmål knyttet til prosjektet kan rettes mot:
* DigiSYFO: [nav.digisyfo@nav.no](mailto:nav.digisyfo@nav.no)