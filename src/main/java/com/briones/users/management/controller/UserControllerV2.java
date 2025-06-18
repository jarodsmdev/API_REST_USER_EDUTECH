package com.briones.users.management.controller;

import com.briones.users.management.assemblers.UserDtoModelAssembler;
import com.briones.users.management.exception.DuplicateKeyException;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.User;
import com.briones.users.management.model.dto.UserDto;
import com.briones.users.management.model.dto.UserMapper;
import com.briones.users.management.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name="User v2", description = "User API REST.  This API allows you to manage users.")
public class UserControllerV2 {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDtoModelAssembler userDtoAssembler;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Operation(
            summary = "Retrieve all users with HATEOAS links",
            description = "Returns a list of all users registered in the system, including HATEOAS links for navigation and related actions."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all users retrieved successfully with HATEOAS links",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = CollectionModel.class)
                    )
            ),
            // Puedes añadir otras respuestas de error si tu método las maneja
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))) // Asume tu ErrorResponse
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        // Mapea los User a UserDto y luego usa el ensamblador para convertirlos en EntityModel con enlaces
        List<EntityModel<UserDto>> userModels = users.stream()
                .map(userMapper::toDto)
                .map(userDtoAssembler::toModel)
                .toList();

        // Crea el CollectionModel para la lista, añadiento un enlace "self" a esta misma collección
        CollectionModel<EntityModel<UserDto>> collectionModel = CollectionModel.of(userModels,
                linkTo(methodOn(UserControllerV2.class).getAllUsers()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @Operation(
            summary = "Retrieve user by ID",
            description = "Returns the user information that matches the given UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserDto.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @Parameter(
            name = "uuid",
            description = "UUID of the user to be retrieved",
            required = true,
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID uuid) throws UserNotFoundException {
        User user = userService.getUserById(uuid);
        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(userDto);
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates and stores a new user in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Duplicate user or constraint violation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object to be created. Must include all required fields.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
            )
    )
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

    @Operation(
            summary = "Update existing user",
            description = "Updates the information of an existing user. The user must already exist."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Conflict or duplicate data")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object with updated fields. The user must already exist in the system.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
            )
    )
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws DuplicateKeyException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(userService.saveUser(user));
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Deletes the user that matches the provided UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Parameter(
            name = "uuid",
            description = "UUID of the user to be deleted",
            required = true,
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) throws UserNotFoundException {
        userService.deleteUserById(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
