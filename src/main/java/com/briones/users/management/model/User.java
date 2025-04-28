package com.briones.users.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID userId;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthDate;
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String address;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).+$", message = "La contraseña debe contener al menos una letra mayúscula, un número y un carácter especial")
    private String password;
    private boolean isActive = false;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
