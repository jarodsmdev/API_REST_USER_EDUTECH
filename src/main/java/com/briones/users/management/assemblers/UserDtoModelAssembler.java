package com.briones.users.management.assemblers;

import com.briones.users.management.controller.UserController;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {
    @Override
    public EntityModel<User> toModel(User user) {
        try {
            return EntityModel.of(user,
                    // 1. Primero se crea el Link con linkTo() y methodOn()
                    // 2. Luego, sobre ese Link, se aplica .withSelfRel()
                    linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel(),
                    linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        } catch (UserNotFoundException e) {
            // Es poco común que un UserNotFoundException ocurra aquí.
            // Si user.getUserId() lanzara una excepción, sería antes.
            // Si UserController.getUserById() lanza una excepción, esta es atrapada por el controlador.
            // Aquí, generalmente, la excepción sería para problemas al construir la URL.
            // Si el ID del usuario es inválido o nulo, podría haber un NullPointerException.
            // Considera si esta captura de excepción es realmente necesaria aquí.
            throw new RuntimeException("Error al ensamblar el modelo para el usuario con ID: " + user.getUserId(), e);
        }
    }
}
