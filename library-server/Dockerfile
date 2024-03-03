FROM eclipse-temurin:17-jre

ADD ./target/library-server-*.jar /app/library-server.jar

ENTRYPOINT ["java", "-jar", "/app/library-server.jar"]

EXPOSE 8761