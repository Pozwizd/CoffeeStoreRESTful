FROM eclipse-temurin:17-jdk
ARG JAR_FILE=target/CoffeeAppRest-latest.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
