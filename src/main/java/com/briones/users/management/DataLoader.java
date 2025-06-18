package com.briones.users.management;

import com.briones.users.management.model.Rol;
import com.briones.users.management.model.User;
import com.briones.users.management.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    Faker faker = new Faker();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Generar usuarios falsos
        for(int i = 0; i < 10 ; i++){
            User user = User.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .birthDate(faker.date().birthday(18,65).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().phoneNumber())
                    .address(faker.address().fullAddress())
                    .password(faker.number().digits(9) + faker.letterify("?").toUpperCase().charAt(0) + "!")
                    .isActive(faker.bool().bool())
                    .rol(faker.options().option(
                            Rol.ROLE_ADMIN,
                            Rol.ROLE_PROFESOR,
                            Rol.ROLE_SOPORTE,
                            Rol.ROLE_ADMINISTRACION
                    ))
                    .build();
            userRepository.save(user);
        }
    }
}
