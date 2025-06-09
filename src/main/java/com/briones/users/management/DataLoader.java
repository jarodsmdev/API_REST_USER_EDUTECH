package com.briones.users.management;

import com.briones.users.management.model.Rol;
import com.briones.users.management.model.User;
import com.briones.users.management.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
                    //.birthDate(faker.date())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().phoneNumber())
                    .address(faker.address().fullAddress())
                    .password("123456789A!")
                    .isActive(faker.bool().bool())
                    .rol(Rol.ROLE_ADMINISTRACION)
                    .build();
            userRepository.save(user);
        }
    }
}
