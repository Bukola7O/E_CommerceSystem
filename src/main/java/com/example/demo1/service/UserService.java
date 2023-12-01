package com.example.demo1.service;

import java.sql.SQLException;

@FunctionalInterface
public interface UserService {
    void compile() throws SQLException, ClassNotFoundException;
}
