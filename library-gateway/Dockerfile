FROM eclipse-temurin:17-jre

ADD ./target/library-gateway-*.jar /app/library-gateway.jar

ENTRYPOINT ["java", "-jar", "/app/library-gateway.jar"]

EXPOSE 8080