package controllers;

import data.DAO.CourseDAO;
import data.DAO.StudentsCourseDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.StudentCourse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController()
public class StudentController {
    CourseDAO courseDAO = new CourseDAO();
    StudentsCourseDAO studentCourseDAO = new StudentsCourseDAO();

    public StudentController() throws SQLException, ClassNotFoundException {
    }

    @GetMapping("/showmarks")
    public String ShowMarks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return "login to access this page";
        }

        List<StudentCourse> arr;
        try {
            arr = studentCourseDAO.getStudentMarks((Integer) request.getSession().getAttribute("userid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //print the marks
        String htmlFile ="";

        htmlFile+=("<html><head><title>Add Course</title></head>");
        htmlFile+=("<body>");
        htmlFile+=("<h1>Your marks</h1>");
        htmlFile+=("<table border=\"1\"");
        htmlFile+=("<tr><th>Course name</th><th>midterm mark</th><th>assignments mark</th><th>final exam mark</th><th>total mark mark</th></tr>");
        for (StudentCourse sc: arr) {
            int total = sc.getMedterm_mark()+sc.getAssignments_mark()+ sc.getFinal_exam_mark();
            String courseName="";
            try {
                courseName = courseDAO.getCourseNameByID(sc.getCourse_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String row = String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",courseName,sc.getMedterm_mark(),sc.getAssignments_mark(),sc.getFinal_exam_mark(),total);
            htmlFile+=row;
        }
        htmlFile+=("</table><a href=\"/home\" >back to home page</a></body></html>");
        return htmlFile;
    }
}
