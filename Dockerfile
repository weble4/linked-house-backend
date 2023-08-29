FROM eclipse-temurin:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "app.jar"]
EXPOSE 8080