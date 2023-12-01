package com.example.demo1.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
    private String hashPassword;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
