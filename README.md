# Netty Server

## Build

1. Run `mvn clean install` for building the sources.
2. Execute DB statement: dbInit.sql

## Server
1. After a successful build run: `com.rl.netty.server.NettyServer`.

##Client
1. Use the client `com.rl.netty.client.DBUpdateClient` in order to update the users.
 This client is getting all users using the REST API and after is updating the users names asynchronously.

## Testing

In order to test the running Netty REST API, you have the following urls as examples:
   -  `localhost:8080/users`							PUT method
   - `localhost:8080/users/1`           	 GET, POST, DELETE methods
   -  `localhost:8080/users`              GET method
   - `localhost:8080/users?name=George`  GET method

## Application requirement

1. Basic deploy of a Netty server with a valid HTTP channel
2. A Mysql database with one table: users
    Users(id, name, email, date)

3. RESTful route to manipulate users: add, delete, update, read all

    Accept sort and filter params for the /users route

4. All communication will be done using Json
5. Create at least 3 users by creating an sql script that does that
6. Log all server requests
