package com.asistencias.Asistencias.dtos;

public class AuthResponse {
    private String token;
    private String userEmail;
    private Long id;
    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}