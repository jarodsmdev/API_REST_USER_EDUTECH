package com.briones.users.management.model;

public enum Rol {
    ROLE_ADMIN("System Administrator"),
    ROLE_PROFESOR("Institution Professor"),
    ROLE_SOPORTE("Technical Support"),
    ROLE_ADMINISTRACION("General Administration");

    private final String description;

    Rol(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
