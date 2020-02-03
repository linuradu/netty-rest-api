# Netty Server

## Build

1. Run `mvn clean install` for a dev building the sources.
2. execute DB statement: dbInit.sql
3. After a successful build execute: `com.rl.netty.server.NettyServer`.

## Testing

In order to test the running netty REST API you have the following urls as examples.
    `localhost:8080/users`              - PUT method
    `localhost:8080/users/1`            - GET, POST, DELETE methods
    `localhost:8080/users`              - GEt method
    `localhost:8080/users?name=George`  - GEt method

## Application requirement

1. Basic deploy of a Netty server with a valid HTTP channel
2. A Mysql database with one table: users
    Users(id, name, email, date)

3. RESTful route to manipulate users: add, delete, update, read all

Accept sort and filter params for the /users route

4. All communication will be done using Json
5. Create at least 3 users by creating an sql script that does that
6. Log all server requests
