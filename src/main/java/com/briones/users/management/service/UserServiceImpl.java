package com.briones.users.management.service;

import com.briones.users.management.model.User;
import com.briones.users.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws Exception{
        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID uuid) throws Exception {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public void deleteUserById(UUID uuid) throws Exception {
        if(!userRepository.existsById(uuid)){
            throw new Exception("User not found, cannot delete");
        }
        userRepository.deleteById(uuid);
    }
}
