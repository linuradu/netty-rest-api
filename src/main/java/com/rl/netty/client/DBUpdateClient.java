package com.rl.netty.client;


import com.rl.netty.client.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DBUpdateClient {

    private final static Logger LOG = LoggerFactory.getLogger(DBUpdateClient.class);
    private static final String REST_URI = "http://localhost:8080/users";

    /**
     * This client is reading all users and is updating the name asynchronously.
     */
    public static void main(String[] args) {
        final Client client = ClientBuilder.newClient();
        final List<UserDTO> users = client.target(REST_URI).request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
        });

        users.forEach(user -> {
            user.setName(user.getName() + "1");

            final CompletableFuture<Response> future = CompletableFuture.supplyAsync(() -> {
                LOG.info("User {} update executed.", user.getName());

                return client.target(REST_URI)
                        .path(user.getId().toString())
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(user, MediaType.APPLICATION_JSON));
            });

            future.thenAccept(response ->
                    LOG.info("User {} updated successfully. Status {}.", user.getName(), response.getStatus())
            );

            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
