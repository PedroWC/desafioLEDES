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

# Instala o curl e o wait-for-it no contêiner
RUN apt-get update && apt-get install -y curl \
    && curl -o /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /usr/local/bin/wait-for-it.sh

# Copie o JAR da fase de build para o contêiner de execução
COPY --from=build /app/target/institute_manager-0.0.1-SNAPSHOT.jar /app/institute_manager.jar

# Exponha a porta 8080
EXPOSE 8080

# Comando de entrada para iniciar a aplicação após verificar se o DB está pronto
ENTRYPOINT ["sh", "-c", "wait-for-it.sh db:3306 -- java -jar institute_manager.jar"]
