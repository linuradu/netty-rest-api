package com.rl.netty.model.converter;

import com.rl.netty.model.User;
import com.rl.netty.model.UserDTO;

public class UserConverter {

    public UserDTO toDTO(final User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getDate());
    }

    public User toEntity(final UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getDate());
    }
}
