import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer
import com.github.jengelman.gradle.plugins.shadow.transformers.ServiceFileTransformer

group = "no.nav.syfo"
version = "1.0.0"

val springBootVersion = "2.0.0.RELEASE"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://repo.adeo.no/repository/maven-releases/")
    maven(url = "http://packages.confluent.io/maven/")
}

dependencies {
    implementation("javax.inject:javax.inject:1")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-jta-atomikos:$springBootVersion")
    runtime("com.oracle:ojdbc8:12.2.0.1")
    implementation("org.flywaydb:flyway-core:5.0.7")
    implementation("org.bitbucket.b_c:jose4j:0.5.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.0.2")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.springframework.boot:spring-boot-starter-logging:$springBootVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:4.10")
    implementation("org.projectlombok:lombok:1.16.20")
    annotationProcessor("org.projectlombok:lombok:1.16.20")
    implementation("com.ibm.mq:com.ibm.mq.allclient:9.0.4.0")
    implementation("org.springframework:spring-jms:5.0.4.RELEASE")
    implementation("no.nav.syfo.tjenester:fellesformat:1.2")
    implementation("no.nav.syfo.tjenester:kith-hodemelding:1.1")
    implementation("no.nav.syfo.tjenester:kith-dialogmelding:1.1")
    implementation("no.nav.syfo.tjenester:kith-base64:1.1")
    implementation("no.nav.syfo.tjenester:kith-apprec:1.1")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.15.0")
    testImplementation("org.assertj:assertj-core:3.6.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.springframework.security:spring-security-test:5.0.3.RELEASE")
    testImplementation("com.h2database:h2:1.4.196")
}


tasks {
    withType<Jar> {
        manifest.attributes["Main-Class"] = "no.nav.syfo.Application"
    }

    create("printVersion") {
        doLast {
            println(project.version)
        }
    }

    withType<ShadowJar> {
        transform(ServiceFileTransformer::class.java) {
            setPath("META-INF/cxf")
            include("bus-extensions.txt")
        }
        transform(PropertiesFileTransformer::class.java) {
            paths = listOf("META-INF/spring.factories")
            mergeStrategy = "append"
        }
        mergeServiceFiles()
    }
}
