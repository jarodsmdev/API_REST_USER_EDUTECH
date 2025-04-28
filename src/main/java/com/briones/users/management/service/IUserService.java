package com.briones.users.management.service;


import com.briones.users.management.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    List<User> getAllUsers();
    User saveUser(User user) throws Exception;
    User getUserById(UUID uuid) throws Exception;
    User getUserByEmail(String email) throws Exception;
    void deleteUserById(UUID uuid) throws Exception;
}
