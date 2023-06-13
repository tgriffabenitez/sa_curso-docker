package com.sistemasactivos.users.service;

import com.sistemasactivos.users.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    Optional<User> findUserById(Long id);
    User saveUser(User user);
    void deleteUserById(Long id);
    List<User> findUsersById(Iterable<Long> ids);
}
