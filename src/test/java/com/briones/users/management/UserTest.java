package com.briones.users.management;

import com.briones.users.management.exception.DuplicateKeyException;
import com.briones.users.management.exception.UserNotFoundException;
import com.briones.users.management.model.Rol;
import com.briones.users.management.model.User;
import com.briones.users.management.model.dto.UserDto;
import com.briones.users.management.repository.UserRepository;
import com.briones.users.management.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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

    @Test
    public void testCrearUsuarioConEmailExistenteLanzaExcepcion() throws UserNotFoundException {
        // Arrange: Preparación del entorno del test
        // Creamos otro usuario que simula estar ya registrado en la BBDD
        User usuarioExistente = User.builder()
                .userId(UUID.randomUUID()) // ID diferente al del nuevo usuario
                .email("juan.perez@gmail.com") // mismo email
                .build();

        // Simulamos que al buscar por el email, el repositorio devuelve un usuario existente
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(usuarioExistente));

        // Act & Assert: Ejecución del método y verficación del resultado esperado

        // Verificamos que al intentar guardar un usuario con email ya registrado,
        // se lanza una excepción DuplicateKeyException
        DuplicateKeyException exception = assertThrows(
                DuplicateKeyException.class,
                () -> userService.saveUser(user) // Método que se está probando
        );

        // Validamos que el mensaje de la excepción sea el esperado
        assertTrue(exception.getMessage().contains("already exists"));

        // Verificamos que no se llamó al método save del repositorio,
        // ya que el guardado no debería ocurrir si hay un duplicado
        verify(userRepository, never()).save(any(User.class));
    }


}
