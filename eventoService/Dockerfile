FROM eclipse-temurin:21

ADD target/eventoService.jar evento-service.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "evento-service.jar"]
