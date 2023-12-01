package com.example.demo1.model;

import com.example.demo1.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private BigDecimal balance;

    public User(UserDto signedUpUser) {
        this.name = signedUpUser.getName();
        this.email = signedUpUser.getEmail();
        String password = BCrypt.withDefaults().hashToString(12, signedUpUser.getPassword().toCharArray());
        this.password = password;
       // this.password = signedUpUser.getPassword();
    }
}
