FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="Álvaro al.escobedoj@gmail.com"
LABEL version="1.0"
LABEL description="API de empleados(Examen) con Spring Boot y H2"

WORKDIR /examen

COPY examen-1.0.0.jar examen.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "examen.jar"]