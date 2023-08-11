package servlets;

import data.DAO.StudentCourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "AddUserToCourseServlet", value = "/addstudenttocourse")
public class AddUserToCourseServlet extends HttpServlet {
    private String message;
    private StudentCourseDAO studentCourseDAO = new StudentCourseDAO();

    public AddUserToCourseServlet() throws SQLException, ClassNotFoundException {
    }

    public void init() {
        message = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //check if user authorized to access page
        auth(request,response);
        request.getRequestDispatcher("/WEB-INF/AddStudentToCourse.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //check if user authorized to access page
        auth(request,response);

        //get data from request
        int studentID = Integer.parseInt(request.getParameter("studentid"));
        int courseID = Integer.parseInt(request.getParameter("courseid"));

        int result = 0;
        try {
            result = studentCourseDAO.addStudentToCourse(courseID,studentID, (Integer) request.getSession().getAttribute("userid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //if the query executed print a message if the query done or not
        if(result==1){
            message = "User add to course Successfully";
            request.setAttribute("message",message);
            request.setAttribute("color","green");
        }
        else{
            message = "Something went wrong ,check the entered ids and try again later";
            request.setAttribute("message",message);
            request.setAttribute("color","red");
        }
        request.getRequestDispatcher("/WEB-INF/AddStudentToCourse.jsp").forward(request, response);
    }

    public void destroy() {}

    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return;
        }
        //if the user not authorized to access to this page redirect to home page
        if(!request.getSession().getAttribute("role").equals("teacher") && !request.getSession().getAttribute("role").equals("admin")){
            response.sendRedirect("/home");
            return;
        }
    }
}