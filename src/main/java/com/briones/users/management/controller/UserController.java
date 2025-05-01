package com.briones.users.management.controller;

import com.briones.users.management.exception.DuplicateKeyException;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.User;
import com.briones.users.management.model.dto.UserDto;
import com.briones.users.management.model.dto.UserMapper;
import com.briones.users.management.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID uuid) throws UserNotFoundException {
        User user = userService.getUserById(uuid);
        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(userDto);
    }

    @PostMapping
    private ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws DuplicateKeyException, UserNotFoundException {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getUserId())
                .toUri();
        return ResponseEntity.created(location)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(userService.saveUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws DuplicateKeyException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(userService.saveUser(user));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) throws UserNotFoundException {
        userService.deleteUserById(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
