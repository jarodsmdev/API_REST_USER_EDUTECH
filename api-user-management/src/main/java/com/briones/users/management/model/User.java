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
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be in a valid format")
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String address;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).+$", message = "Password must contain at least one uppercase letter, one number, and one special character")
    private String password;
    private boolean isActive = false;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
