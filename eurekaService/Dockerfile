FROM eclipse-temurin:21

COPY target/eurekaService.jar eureka-server.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
