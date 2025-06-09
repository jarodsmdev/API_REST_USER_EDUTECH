package com.briones.users.management;

import com.briones.users.management.model.Rol;
import com.briones.users.management.model.User;
import com.briones.users.management.model.dto.UserDto;
import com.briones.users.management.repository.UserRepository;
import com.briones.users.management.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan.perez@gmail.com")
                .phone("56987654321")
                .address("Calle Falsa 123")
                .password("123456789A!")
                .isActive(true)
                .rol(Rol.ROLE_ADMINISTRACION)
                .build();

        userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setActive(user.isActive());
        userDto.setRol(user.getRol());
    }


}
