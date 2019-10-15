FROM navikt/java:8-appdynamics
ENV APPD_ENABLED=true
COPY build/libs/*.jar app.jar

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom \
               -Dspring.profiles.active=remote"
