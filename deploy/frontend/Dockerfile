FROM node:18 AS build

WORKDIR /app
COPY frontend /app
COPY deploy/.env .env

RUN npm install
RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html
COPY deploy/nginx/default.conf /etc/nginx/conf.d/default.conf
