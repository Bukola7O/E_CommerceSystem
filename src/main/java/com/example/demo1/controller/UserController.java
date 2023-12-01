package com.example.demo1.controller;

import com.example.demo1.dao.ProductDao;
import com.example.demo1.dao.UserDao;
import com.example.demo1.dto.LoginRequestDTO;
import com.example.demo1.dto.UserDto;
import com.example.demo1.model.User;
import com.example.demo1.servicImpl.UserServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@WebServlet(name = "user", value = "/user")
public class UserController extends HttpServlet {
    private Logger logger = Logger.getGlobal();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login")==null?null:req.getParameter("login");
        String admin = req.getParameter("admin");
        if (admin!=null&& Objects.equals(admin, "true")){
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
            dispatcher.forward(req, resp);
        }
        if (login!=null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req, resp);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("sign-up.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Displaying user information now...");
        UserServiceImpl userService = new UserServiceImpl();
        UserDao userDao = new UserDao();
        // "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
        //conditional rendering...
        String login = req.getParameter("login")==null?null:req.getParameter("login");
        if (login!=null){
            LoginRequestDTO loggedInUser = new LoginRequestDTO(req.getParameter("email"), req.getParameter("password"));
//            Users user = userService.findUserByEmail.apply(loggedInUser);
            User user =  userDao.findUser.apply(loggedInUser);
            loggedInUser.setHashPassword(user.getPassword());
            if (userService.verifyPassword.apply(loggedInUser)){
                HttpSession session = req.getSession();
                session.setAttribute("userID", user.getId());
                req.setAttribute("product-list", new ProductDao().findAllProducts.get());

                RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
                dispatcher.forward(req, resp);
            }
            else{
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);
            }
        }
        UserDto userDto = new UserDto();
        userDto.setName(req.getParameter("username"));
        userDto.setPassword(req.getParameter("password"));
        userDto.setEmail(req.getParameter("email"));
//       logger.info("SAVED USER --> "+userService.saveUserInformation.apply(userDto));
        logger.info("SAVED USER --> "+userDao.saveUser.apply(new User(userDto)));
        RequestDispatcher dispatcher = req.getRequestDispatcher("successful.jsp");
        dispatcher.forward(req, resp);

    }
}
