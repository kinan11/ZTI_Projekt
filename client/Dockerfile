# FROM node:alpine
# WORKDIR '/app'

# COPY package.json .
# RUN npm install
# COPY . .
# CMD ["npm","start"]
# Stage 1: Budowanie aplikacji React
FROM node:16.15.0 as build

# Ustawienie katalogu roboczego
WORKDIR /app

# Skopiowanie plików package.json i package-lock.json
COPY package*.json ./

# Instalacja zależności
RUN npm install

# Skopiowanie reszty plików aplikacji
COPY . .

# Budowanie aplikacji
RUN npm run build

# Stage 2: Uruchamianie aplikacji na serwerze Nginx
FROM nginx:1.21.0

# Kopiowanie skompilowanych plików z poprzedniego etapu
COPY --from=build /app/build /usr/share/nginx/html

# Konfiguracja plików statycznych Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Port, na którym będzie działać serwer Nginx
EXPOSE 80

# Start serwera Nginx
CMD ["nginx", "-g", "daemon off;"]
