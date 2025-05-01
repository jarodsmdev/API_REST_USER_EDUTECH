package com.briones.users.management.model.dto;

import com.briones.users.management.model.Rol;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String address;
    private boolean isActive;
    private Rol rol;
}
