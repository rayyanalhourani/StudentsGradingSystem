package controllers;

import data.DAO.StudentsCourseDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

@RestController
public class TeacherController {
    StudentsCourseDAO studentCourseDAO=new StudentsCourseDAO();

    public TeacherController() throws SQLException, ClassNotFoundException {
    }

    @GetMapping("/showstatics")
    public String showStatics(){
        return "Show statics page";
    }

    @PostMapping("/showstatics")
    public String addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int courseID = Integer.parseInt(request.getParameter("courseid"));

        ArrayList<Integer> arr = new ArrayList<>();
        try {
            arr = studentCourseDAO.getCourseStatics(courseID, (Integer) request.getSession().getAttribute("userid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //check if there are an error or there are no data to show
        if(arr.size()==0){
            return "something went wrong , maybe you enterd a invalid course id or you are not the teacher of this course";
        }

        //count the statics and print them
        int max = findMax(arr);
        int min = findMin(arr);
        double avg = findAverage(arr);
        double median = findMedian(arr);


        String htmlFile ="";

        htmlFile+=("<html><head><title>Show statics</title></head>");
        htmlFile+=("<body>");
        htmlFile+=("<h1>Course Statics</h1>");
        htmlFile+=("<h2>Course marks average : "+avg +"</h2>");
        htmlFile+=("<h2>Course maximum mark : "+max +"</h2>");
        htmlFile+=("<h2>Course minimum mark : "+min +"</h2>");
        htmlFile+=("<h2>Course median of marks : "+median +"</h2>");
        htmlFile+=("<a href=\"/home\" >back to home page</a></body></html>");
        return htmlFile;
    }
    public int findMax(ArrayList<Integer> arrayList) {
        return Collections.max(arrayList);
    }

    public int findMin(ArrayList<Integer> arrayList) {
        return Collections.min(arrayList);
    }

    public double findAverage(ArrayList<Integer> arrayList) {
        int sum = 0;
        for (int num : arrayList) {
            sum += num;
        }
        return (double) sum / arrayList.size();
    }

    public double findMedian(ArrayList<Integer> arrayList) {
        Collections.sort(arrayList);

        if (arrayList.size() % 2 == 0) {
            int mid1 = arrayList.get(arrayList.size() / 2);
            int mid2 = arrayList.get(arrayList.size() / 2 - 1);
            return (double) (mid1 + mid2) / 2;
        } else {
            return (double) arrayList.get(arrayList.size() / 2);
        }
    }
}
