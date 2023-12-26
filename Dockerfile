FROM ubuntu:24.04

LABEL maintainer="make a miracle"

# Actualizar el sistema y instalar herramientas necesarias
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Crear el directorio de la aplicación
WORKDIR /app

# Copiar la aplicación
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Establecer el comando de entrada
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]