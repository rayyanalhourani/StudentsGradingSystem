package controllers;

import data.DAO.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.sql.SQLException;

@RestController
public class UserController {
    UserDAO userDAO =new UserDAO();

    public UserController() throws SQLException, ClassNotFoundException {
    }

    @GetMapping("/login")
    public String login(){
        return "Login Page";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession();
        int userid= Integer.parseInt(request.getParameter("id"));
        String password=request.getParameter("password");

        String role=userDAO.login(userid,password);

        if(role.isEmpty()){
            return "Wrong user name or password";
        }
        else {
            session.setAttribute("role", role);
            session.setAttribute("userid", userid);
            return "loged in Successfully";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //clear session attribute
        HttpSession session = request.getSession();
        session.setAttribute("userid",null);
        return "loged out";
    }

    @GetMapping("/")
    public String Home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            return "login to access this page";
        }
        return "home page";
    }

    @GetMapping("/adduser")
    public String AddUser(){
        return "add user page";
    }

    @PostMapping("/adduser")
    public String AddUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return "login to access this page";
        }
        //if the user not authorized to access to this page redirect to home page
        if(!request.getSession().getAttribute("role").equals("admin")){
            return "you are not authorized to access this page";
        }

        //get data from request
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password =request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User(id , name , password , role);
        int result = userDAO.addUser(user);
        //if the query executed print a message if the query done or not
        if(result==1){
            return "User add Successfully";
        }
        else{
            return "Something went wrong ,check the entered id and try again later";
        }
    }
}
