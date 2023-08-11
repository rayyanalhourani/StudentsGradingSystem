package servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private String message="";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return;
        }

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role.equals("admin")){
            request.getRequestDispatcher("/WEB-INF/AdminView.jsp").forward(request, response);
        }
        else if (role.equals("teacher")){
            request.getRequestDispatcher("/WEB-INF/TeacherView.jsp").forward(request, response);
        }
        else if (role.equals("student")){
            request.getRequestDispatcher("/WEB-INF/StudentView.jsp").forward(request, response);
        }
    }

    public void destroy() {
    }
}