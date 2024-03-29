import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer
import com.github.jengelman.gradle.plugins.shadow.transformers.ServiceFileTransformer

group = "no.nav.syfo"
version = "1.0.0"

val javaxActivationVersion = "1.2.0"
val jaxbApiVersion = "2.4.0-b180830.0359"

val ibmMqAllclientVersion = "9.0.5.0"
val flywayVersion = "5.1.4"
val ojdbc8Version = "19.3.0.0"
val prometheusVersion = "1.3.14"
val syfotjenesterVersion = "1.2020.06.25-12.35-50610b959e55"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.springframework.boot") version "2.2.10.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

val githubUser: String by project
val githubPassword: String by project
repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://maven.pkg.github.com/navikt/syfotjenester")
        credentials {
            username = githubUser
            password = githubPassword
        }
    }
}

dependencies {
    implementation("javax.inject:javax.inject:1")
    implementation("org.bitbucket.b_c:jose4j:0.5.0")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jta-atomikos")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework:spring-jms")

    implementation("com.oracle.ojdbc:ojdbc8:$ojdbc8Version")
    implementation("org.flywaydb:flyway-core:$flywayVersion")

    implementation("com.sun.activation:javax.activation:$javaxActivationVersion")
    implementation("javax.xml.bind:jaxb-api:$jaxbApiVersion")

    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("io.micrometer:micrometer-registry-prometheus:$prometheusVersion")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("net.logstash.logback:logstash-logback-encoder:4.10")
    implementation("com.ibm.mq:com.ibm.mq.allclient:$ibmMqAllclientVersion")

    implementation("no.nav.syfotjenester:fellesformat:$syfotjenesterVersion")
    implementation("no.nav.syfotjenester:kith-apprec:$syfotjenesterVersion")
    implementation("no.nav.syfotjenester:kith-base64:$syfotjenesterVersion")
    implementation("no.nav.syfotjenester:kith-dialogmelding:$syfotjenesterVersion")
    implementation("no.nav.syfotjenester:kith-hodemelding:$syfotjenesterVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.h2database:h2")
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
