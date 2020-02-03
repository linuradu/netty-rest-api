package com.rl.netty.service;

import com.rl.netty.model.User;
import com.rl.netty.model.UserDTO;
import com.rl.netty.model.converter.UserConverter;
import com.rl.netty.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final UserConverter userConverter = new UserConverter();

    public UserDTO createUser(final UserDTO userDTO) {
        final User createdUser = userRepository.saveUser(userConverter.toEntity(userDTO));
        return Optional.of(createdUser).map(userConverter::toDTO).orElse(null);
    }

    public UserDTO updateUser(final UserDTO userDTO, final Long userId) {
        if (!existsUser(userId)) {
            throw new IllegalArgumentException("User with the given id is not found!");
        }
        userDTO.setId(userId);

        return Optional.of(userRepository.updateUser(userConverter.toEntity(userDTO)))
                .map(userConverter::toDTO).orElse(null);
    }

    public UserDTO getUser(final Long userId) {
        return Optional.of(userRepository.getUser(userId))
                .map(userConverter::toDTO).orElse(null);
    }

    public void deleteUser(final Long userId) {
        if (!existsUser(userId)) {
            throw new IllegalArgumentException("User with the given id is not found!");
        }
        userRepository.deleteUser(userId);
    }

    public UserDTO loadUser(final Long userId) {
        if (!existsUser(userId)) {
            throw new IllegalArgumentException("User with the given id is not found!");
        }
        return Optional.of(userRepository.loadUser(userId))
                .map(userConverter::toDTO).orElse(null);
    }

    public List<UserDTO> loadUsers(final Map<String, String> uriParams) {
        return userRepository.loadUsers(uriParams).stream().map(userConverter::toDTO).collect(Collectors.toList());

    }

    private boolean existsUser(final Long userId) {
        return userRepository.existsUser(userId);
    }
}
