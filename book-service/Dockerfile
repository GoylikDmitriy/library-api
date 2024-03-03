FROM eclipse-temurin:17-jre

ADD ./target/book-service-*.jar /app/book-service.jar

ENTRYPOINT ["java", "-jar", "/app/book-service.jar"]