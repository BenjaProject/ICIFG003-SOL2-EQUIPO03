# Etapa 1: Compilación con Node.js
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production

# Etapa 2: Servidor Web
FROM nginx:alpine
# RECUERDA: Cambia "nombre-proyecto" por el nombre real en tu angular.json
COPY --from=build /app/dist/nombre-proyecto/browser /usr/share/nginx/html
EXPOSE 80