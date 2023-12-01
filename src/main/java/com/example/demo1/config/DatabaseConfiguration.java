package com.example.demo1.config;

import lombok.Getter;

@Getter
public class DatabaseConfiguration {
    private final String DB_URL = "jdbc:mysql://localhost:3306/ProductDB";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Tojuade1,*";

}
