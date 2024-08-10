# Usar uma imagem base do Node.js para construir o projeto
FROM node:16 AS build

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar apenas os arquivos de dependências
COPY package*.json ./
RUN npm install

# Copiar o código-fonte para o contêiner
COPY . .

# Construir o projeto
RUN npm run build


# Segunda etapa: criar a imagem final com o servidor NGINX
FROM nginx:alpine

# Copiar os arquivos de build do React para o diretório padrão do NGINX
COPY --from=build /app/build /usr/share/nginx/html

# Expor a porta 80
EXPOSE 80

# Definir o comando de inicialização do contêiner
CMD ["nginx", "-g", "daemon off;"]