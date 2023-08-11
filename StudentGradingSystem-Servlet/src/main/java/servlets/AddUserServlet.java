package servlets;

import java.io.*;
import java.sql.SQLException;

import data.DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;

@WebServlet(name = "AddUserServlet", value = "/adduser")
public class AddUserServlet extends HttpServlet {
    private String message;
    UserDAO userDAO = new UserDAO();

    public AddUserServlet() throws SQLException, ClassNotFoundException {
    }

    public void init() {
        message = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //check if user authorized to access page
        auth(request,response);
        request.getRequestDispatcher("/WEB-INF/AddUser.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //check if user authorized to access page
        auth(request,response);

        //get data from request
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password =request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User(id , name , password , role);
        int result = userDAO.addUser(user);
        //if the query executed print a message if the query done or not
        if(result==1){
            message = "User add Successfully";
            request.setAttribute("message",message);
            request.setAttribute("color","green");
        }
        else{
            message = "Something went wrong ,check the entered id and try again later";
            request.setAttribute("message",message);
            request.setAttribute("color","red");
        }
        request.getRequestDispatcher("/WEB-INF/AddUser.jsp").forward(request, response);
    }

    public void destroy() {
    }

    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return;
        }
        //if the user not authorized to access to this page redirect to home page
        if(!request.getSession().getAttribute("role").equals("admin")){
            response.sendRedirect("/home");
            return;
        }
    }
}