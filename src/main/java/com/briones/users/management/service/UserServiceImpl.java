package com.briones.users.management.service;

import com.briones.users.management.exception.DuplicateKeyException;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.User;
import com.briones.users.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws DuplicateKeyException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateKeyException("Email already exists " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID uuid) throws UserNotFoundException {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(UUID uuid) throws UserNotFoundException {
        if(!userRepository.existsById(uuid)){
            throw new UserNotFoundException("User not found, cannot delete");
        }
        userRepository.deleteById(uuid);
    }
}
