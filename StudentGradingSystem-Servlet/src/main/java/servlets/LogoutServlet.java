package servlets;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //clear session attribute
        HttpSession session = request.getSession();
        session.setAttribute("userid",null);
        response.sendRedirect("/login");
    }

    public void destroy() {
    }
}