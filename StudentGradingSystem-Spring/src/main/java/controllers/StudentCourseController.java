package controllers;

import data.DAO.StudentsCourseDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.StudentCourse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class StudentCourseController {
    StudentsCourseDAO studentCourseDAO = new StudentsCourseDAO();

    public StudentCourseController() throws SQLException, ClassNotFoundException {
    }

    @GetMapping("/addusertocourse")
    public String addUserToCourse(){
        return "Add user To course page";
    }

    @PostMapping("/addusertocourse")
    public String addUserToCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            return "login to access this page";
        }
        //if the user not authorized to access to this page redirect to home page
        if(!request.getSession().getAttribute("role").equals("teacher") && !request.getSession().getAttribute("role").equals("admin")){
            return "you are not allowed to access this page";
        }
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
            return "User add to course Successfully";
        }
        else{
            return  "Something went wrong ,check the entered ids and try again later";
        }
    }

    @GetMapping("/entermarks")
    public String enterMarks(){
        return "enter marks page";
    }

    @PostMapping("/entermarks")
    public String enterMarks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            return "you have to login to access this page";
        }
        //if the user not authorized to access to this page redirect to home page
        if(!request.getSession().getAttribute("role").equals("teacher") && !request.getSession().getAttribute("role").equals("admin")){
            response.sendRedirect("/home");
            return "you are not allowed to access this page";
        }

        //get data from request
        int studentID = Integer.parseInt(request.getParameter("studentid"));
        int courseID = Integer.parseInt(request.getParameter("courseid"));
        int midGrade = Integer.parseInt(request.getParameter("midtermgrade"));
        int assignmentsGrade = Integer.parseInt(request.getParameter("assignmentsgrade"));
        int finalGrade = Integer.parseInt(request.getParameter("finalgrade"));

        StudentCourse studentCourse = new StudentCourse(studentID,courseID,midGrade,assignmentsGrade,finalGrade);

        int result = 0;
        try {
            result = studentCourseDAO.setMarks(studentCourse , (Integer) request.getSession().getAttribute("userid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //if the query executed print a message if the query done or not
        if(result==1){
            return  "marks entered Successfully";
        }
        else{
            return "Something went wrong ,check the entered ids and try again later";

        }
    }


}
