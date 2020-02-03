package com.rl.netty.rest.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rl.netty.model.UserDTO;
import com.rl.netty.rest.CrudController;
import com.rl.netty.server.HttpResponseHandler;
import com.rl.netty.server.model.request.RequestObject;
import com.rl.netty.service.UserService;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

public class UserController implements CrudController {

    private UserService userService = new UserService();

    @Override
    public FullHttpResponse create(final RequestObject requestObject) {
        try {
            final UserDTO userDTO = new ObjectMapper().readValue(requestObject.getContent(), UserDTO.class);
            userService.createUser(userDTO);

            return HttpResponseHandler.http200(new ObjectMapper().writeValueAsString(userDTO));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The provided payload does not respect the required format.");
        } catch (Exception ex) {
            throw new RuntimeException("Exception while saving the user.");
        }
    }

    @Override
    public FullHttpResponse update(final RequestObject requestObject, final Long objectId) {
        try {
            final UserDTO userDTO = new ObjectMapper().readValue(requestObject.getContent(), UserDTO.class);
            userService.updateUser(userDTO, objectId);

            return HttpResponseHandler.http200(new ObjectMapper().writeValueAsString(userDTO));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The provided payload does not respect the required format.");
        } catch (Exception ex) {
            throw new RuntimeException("Exception while updating the user.");
        }
    }

    @Override
    public FullHttpResponse delete(final RequestObject requestObject, final Long objectId) {
        try {
            userService.deleteUser(objectId);
        } catch (Exception ex) {
            throw new RuntimeException("Exception while deleting the user.");
        }
        return HttpResponseHandler.http200("");
    }

    @Override
    public FullHttpResponse get(final RequestObject requestObject, final Long objectId) {
        try {
            final UserDTO userDTO = userService.loadUser(objectId);
            return HttpResponseHandler.http200(new ObjectMapper().writeValueAsString(userDTO));
        } catch (Exception ex) {
            throw new RuntimeException("Exception while loading the user.");
        }
    }

    @Override
    public FullHttpResponse getAll(final RequestObject requestObject) {
        try {
            final List<UserDTO> usersDTO = userService.loadUsers(requestObject.getRequestContext().getUriParams());
            return HttpResponseHandler.http200(new ObjectMapper().writeValueAsString(usersDTO));
        } catch (Exception ex) {
            throw new RuntimeException("Exception while loading the user.");
        }
    }
}
