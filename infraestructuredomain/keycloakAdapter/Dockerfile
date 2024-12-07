# Usar una imagen base de JRE 21 Alpine
FROM eclipse-temurin:21-jre-alpine

# Crear grupo 'devopsc' y usuario 'admin', que pertenezca al grupo 'devopsc'
RUN addgroup -S devopsc && adduser -S admin -G devopsc

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR especificado al momento de construir la imagen (docker build --build-arg JAR_FILE=nombre-del-jar.jar .)
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Cambiar la propiedad del archivo copiado al usuario 'admin'
RUN chown admin:devopsc /app/app.jar

# Cambiar a usuario 'admin' para ejecutar la aplicación como rootless
USER admin

# Ejecutar la aplicación con el archivo JAR
CMD ["java", "-jar", "app.jar"]
