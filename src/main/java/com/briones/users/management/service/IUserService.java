package com.briones.users.management.service;


import com.briones.users.management.exception.DuplicateKeyException;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    List<User> getAllUsers();
    User saveUser(User user) throws DuplicateKeyException;
    User getUserById(UUID uuid) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    void deleteUserById(UUID uuid) throws UserNotFoundException;
}
