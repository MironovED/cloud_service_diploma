FROM eclipse-temurin:17

EXPOSE 8080

ADD target/spring-boot-rest-0.0.1-SNAPSHOT.jar cloudService.jar

CMD ["java", "-jar", "cloudService.jar"]