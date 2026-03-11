package com.example.Task.Management.API.dto;


import com.example.Task.Management.API.enums.Role;
import jakarta.validation.constraints.NotNull;


public class AuthResponse {

    private String token;
    private String username;
    private Role role;

    public AuthResponse(String token, String username, @NotNull(message = "Role is required") Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }
}
