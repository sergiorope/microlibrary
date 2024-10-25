FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S devopsc && adduser -S admin -G devopsc

WORKDIR /app

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

RUN chown admin:devopsc /app/app.jar

USER admin

CMD ["java", "-jar", "app.jar"]

#Se crea un usuario admin para que no pueda tener acceso a todas las áreas del contenedor que pueda tener informacion vulnerable
