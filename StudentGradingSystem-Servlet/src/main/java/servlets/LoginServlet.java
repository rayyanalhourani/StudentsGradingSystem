package servlets;

import java.io.*;
import java.sql.SQLException;

import data.DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jboss.weld.context.http.Http;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private String message;
    private UserDAO userDAO = new UserDAO();

    public LoginServlet() throws SQLException, ClassNotFoundException {
    }

    public void init() {
        message = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("message",message);
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int userid= Integer.parseInt(request.getParameter("id"));
        String password=request.getParameter("password");

        String role=userDAO.login(userid,password);
        //if the query executed print a message if the query done or not
        if(role.equals("")){
            message="Wrong user name or password";
            request.setAttribute("message",message);
            request.setAttribute("color","red");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        else{
            session.setAttribute("role",role);
            session.setAttribute("userid",userid);
            response.sendRedirect("/home");
        }
    }

    public void destroy() {
    }
}