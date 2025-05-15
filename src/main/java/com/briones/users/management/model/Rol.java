package com.briones.users.management.model;

public enum Rol {
    ROLE_ADMIN("Administrador del sistema"),
    ROLE_PROFESOR("Profesor de la institución"),
    ROLE_SOPORTE("Soporte técnico"),
    ROLE_ADMINISTRACION("Administración general");

    private final String description;

    Rol(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
