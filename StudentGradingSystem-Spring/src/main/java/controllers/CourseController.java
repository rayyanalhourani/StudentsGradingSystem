package controllers;


import data.DAO.CourseDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class CourseController {
    CourseDAO courseDAO = new CourseDAO();

    public CourseController() throws SQLException, ClassNotFoundException {
    }

    @GetMapping("/addcourse")
    public String addCourse(){
        return "add course page";
    }

    @PostMapping ("/addcourse")
    public String addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return "login please to access this page";
        }
        //if the user not authorized to access to this page redirect to home page
        if(!request.getSession().getAttribute("role").equals("admin")){
            response.sendRedirect("/home");
            return "you are not allowed to access this page";
        }
        //get data from request
        int id = Integer.parseInt(request.getParameter("id"));
        String name =  request.getParameter("name");
        int teacherid = Integer.parseInt(request.getParameter("teacherid"));

        Course course = new Course(id , name , teacherid);
        int result = courseDAO.addCourse(course);
        //if the query executed print a message if the query done or not
        if(result==1){
            return  "Course add Successfully";
        } else{
            return  "Something went wrong ,check the course or teacher id and try again later";
        }
    }

}
