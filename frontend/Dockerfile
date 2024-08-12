# Etapa 1: Construção da aplicação
FROM node:16 AS build

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar os arquivos de dependências
COPY package*.json ./
RUN npm install

# Copiar o código-fonte para o contêiner
COPY . .

# Construir o projeto
RUN npm run build

# Etapa 2: Criar a imagem final usando o `serve`
FROM node:16-alpine

# Instalar o serve globalmente
RUN npm install -g serve

# Copiar os arquivos de build do React para o contêiner
COPY --from=build /app/build /usr/src/app

# Expor a porta 3000 (ou outra porta que desejar)
EXPOSE 3000

# Definir o comando de inicialização do contêiner
CMD ["serve", "-s", "/usr/src/app", "-l", "3000"]
