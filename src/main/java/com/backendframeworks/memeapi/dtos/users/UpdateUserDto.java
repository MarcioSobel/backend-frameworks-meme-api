package com.backendframeworks.memeapi.dtos.users;

public class UpdateUserDto {
    private String name;
    private String email;
    private String handle;
    private String password;

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHandle() {
        return handle;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
