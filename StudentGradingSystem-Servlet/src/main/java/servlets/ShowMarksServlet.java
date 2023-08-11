package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DAO.StudentCourseDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.StudentCourse;

@WebServlet(name = "getMarks", value = "/getmarks")
public class ShowMarksServlet extends HttpServlet {
    StudentCourseDAO studentCourseDAO = new StudentCourseDAO();

    public ShowMarksServlet() throws SQLException, ClassNotFoundException {
    }

    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if the user not loged-in redirect to login page
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/login");
            return;
        }
        response.setContentType("text/html");
        ArrayList<StudentCourse> arr;
        try {
            arr = studentCourseDAO.getStudentMarks((Integer) request.getSession().getAttribute("userid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //print the marks table
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Add Course</title></head>");
        out.println("<body>");
        out.println("<h1>Your marks</h1>");
        out.println("<table border=\"1\"");
        out.println("<tr><th>Course name</th><th>midterm mark</th><th>assignments mark</th><th>final exam mark</th><th>total mark mark</th></tr>");
        for (StudentCourse sc: arr) {
            int total = sc.getMedterm_mark()+sc.getAssignments_mark()+ sc.getFinal_exam_mark();
            String row = String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",sc.getCourse_id(),sc.getMedterm_mark(),sc.getAssignments_mark(),sc.getFinal_exam_mark(),total);
            out.println(row);
        }
        out.println("</table><a href=\"/home\" >back to home page</a></body></html>");
    }

    public void destroy() {
    }
}