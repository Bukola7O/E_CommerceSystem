package com.example.demo1.dao;

import com.example.demo1.config.DatabaseConfiguration;
import com.example.demo1.dto.LoginRequestDTO;
import com.example.demo1.model.User;
import com.example.demo1.service.UserService;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Logger;

public class UserDao {
    private Logger logger = Logger.getGlobal();
    private Connection connection;

    public UserService connect = () -> {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DatabaseConfiguration configuration = new DatabaseConfiguration();
        Properties properties = new Properties();
        properties.setProperty("User", configuration.getDB_URL());
        properties.setProperty("Password", configuration.getDB_PASSWORD());
        if (connection==null || connection.isClosed()){
            System.out.println(configuration.getDB_URL());
            connection = DriverManager.getConnection(configuration.getDB_URL(), configuration.getDB_USER(), configuration.getDB_PASSWORD());
        }
    };


    public Function<User, Boolean> saveUser = (users -> {
        try {
            connect.compile();
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL exception"+ e.getMessage());
            throw new RuntimeException(e);
        }
        String query = "INSERT INTO ProductDB.users  (name, email, password, balance) VALUES (?, ?, ?, ?) ";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, users.getName());
            preparedStatement.setString(2, users.getEmail());
            preparedStatement.setString(3, users.getPassword());
            preparedStatement.setDouble(4, new BigDecimal(50000000).doubleValue());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });


    public Function<User, Boolean> updateUserBalance = (user -> {
        try {
            connect.compile();
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL exception"+e.getMessage());
            throw new RuntimeException(e);
        }
        String query = "UPDATE ProductDB.users SET balance = ? WHERE id = ? ";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(2, user.getId());
            preparedStatement.setDouble(1, user.getBalance().doubleValue());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });


    public Function<LoginRequestDTO, User> findUser = (user -> {
        try {
            connect.compile();
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL exception"+e.getMessage());
            throw new RuntimeException(e);
        }
        String query = "SELECT * FROM ProductDB.users WHERE email = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return User.builder()
                        .id(resultSet.getLong(1))
                        .email(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .name(resultSet.getString(4))
                        .balance(resultSet.getBigDecimal(5)).build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    });
    public Function<Long, User> findUserById = (id -> {
        try {
            connect.compile();
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL exception"+e.getMessage());
            throw new RuntimeException(e);
        }
        String query = "SELECT * FROM ProductDB.users WHERE id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return User.builder()
                        .id(resultSet.getLong(1))
                        .email(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .name(resultSet.getString(4))
                        .balance(resultSet.getBigDecimal(5)).build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    });


}

