# BUILD
FROM maven:3.9.8-eclipse-temurin-22 as build
WORKDIR /app

# Copie apenas o pom.xml primeiro
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Agora copie o código-fonte e compile
COPY src /app/src/
RUN mvn package -DskipTests

# EXEC
FROM eclipse-temurin:22-jre
WORKDIR /app

# Instala o curl no contêiner
RUN apt-get update && apt-get install -y curl

# Copie o JAR da fase de build para o contêiner de execução
COPY --from=build /app/target/institute_manager-0.0.1-SNAPSHOT.jar /app/institute_manager.jar

# Exponha a porta 8080
EXPOSE 8080

# Comando de entrada para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "institute_manager.jar"]
