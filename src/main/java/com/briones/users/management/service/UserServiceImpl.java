package com.briones.users.management.service;

import com.briones.users.management.exception.DuplicateKeyException;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.User;
import com.briones.users.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
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
    public User saveUser(User user) throws DuplicateKeyException, UserNotFoundException {
        // Verificar si el correo ya existe
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getUserId().equals(user.getUserId())) {
            // Si el correo pertenece a otro usuario, lanzar excepción
            throw new DuplicateKeyException("The user with the email " + user.getEmail() + " already exists", "email", user.getEmail());
        }

        try {
            // Guardar o actualizar el usuario
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateKeyException("The user with the email " + user.getEmail() + " already exists", "email", user.getEmail());
        }
    }

    @Override
    public User getUserById(UUID uuid) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(uuid);
        if(!user.isPresent()) throw new UserNotFoundException("User not found");
        return user.get();
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(UUID uuid) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(uuid);
        if(!user.isPresent()) throw new UserNotFoundException("Cannot delete user, user not found");
        userRepository.deleteById(uuid);
    }
}
