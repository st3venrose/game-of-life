
FROM node:12.14.1-alpine as build
WORKDIR /usr/src/app
COPY . .
RUN npm ci
RUN npm run build

FROM nginx:1.17.0-alpine
COPY --from=build /usr/src/app/dist/gol-frontend /usr/share/nginx/ui-app
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]

