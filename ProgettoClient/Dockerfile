# Stage 1
# Create image based on the official Node 8 image from dockerhub

FROM node:10.9-alpine as node

# Create a directory where our app will be placed
RUN mkdir -p /usr/src/app

# Change directory so that our commands run inside this new directory

WORKDIR /usr/src/app

# Copy dependency definitions

COPY package*.json ./

# Install dependecies

RUN npm -s install --production --prefix /var/task

# Get all the code needed to run the app

COPY . .

# Run the angular in product
RUN npm run build --base-href="/"

# Stage 2
FROM nginx:1.13.12-alpine

#copy dist content to html nginx folder, config nginx to point in index.html
COPY --from=node /usr/src/app/dist/ProgettoClient /usr/share/nginx/html

COPY ./nginx.conf /etc/nginx/nginx.conf
