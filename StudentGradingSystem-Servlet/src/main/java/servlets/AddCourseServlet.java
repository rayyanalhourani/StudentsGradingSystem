package servlets;

import java.io.*;
import java.sql.SQLException;

import data.DAO.CourseDAO;
import data.DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Course;
import model.User;

@WebServlet(name = "AddCourseServlet", value = "/addcourse")
public class AddCourseServlet extends HttpServlet {
    private String message="";
   CourseDAO courseDAO = new CourseDAO();

    public AddCourseServlet() throws SQLException, ClassNotFoundException {
    }

    public void init() {}


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //check if user authorized to access page
        auth(request,response);
        request.getRequestDispatcher("/WEB-INF/AddCourse.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //check if user authorized to access page
        auth(request,response);

        //get data from request
        int id = Integer.parseInt(request.getParameter("id"));
        String name =  request.getParameter("name");
        int teacherid = Integer.parseInt(request.getParameter("teacherid"));

        Course course = new Course(id , name , teacherid);
        int result = courseDAO.addCourse(course);
        //if the query executed print a message if the query done or not
        if(result==1){
            message = "Course add Successfully";
            request.setAttribute("message",message);
            request.setAttribute("color","green");
        } else{
            message = "Something went wrong ,check the course or teacher id and try again later";
            request.setAttribute("message",message);
            request.setAttribute("color","red");
        }
        request.getRequestDispatcher("/WEB-INF/AddCourse.jsp").forward(request, response);
    }

    public void destroy() {}
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