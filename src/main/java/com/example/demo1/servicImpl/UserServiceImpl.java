package com.example.demo1.servicImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.demo1.dto.LoginRequestDTO;
import com.example.demo1.dto.UserDto;
import com.example.demo1.model.User;
//import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserServiceImpl {
    public static List<User> savedUsers = new ArrayList<>();
   public Function<UserDto, User> saveUserInformation = (userDto -> {
     User user = new User(userDto);
     user.setId(savedUsers.size()==0?1: (long) savedUsers.size()+1);
     savedUsers.add(user);
     return user;
    });

   public Function<LoginRequestDTO, User> findUserByEmail = (loggedInUser) -> savedUsers
           .stream()
           .filter(user -> Objects.equals(user.getEmail(), loggedInUser.getEmail()))
           .collect(Collectors.toList()).get(0);

   public Function<LoginRequestDTO, Boolean> verifyPassword = (user) -> BCrypt.verifyer()
       .verify(user.getPassword().toCharArray(),
               user.getHashPassword()).verified;
    }

