package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import data.DAO.StudentCourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ShowStatics", value = "/showstatics")
public class ShowStaticsServlet extends HttpServlet {
    StudentCourseDAO studentCourseDAO=new StudentCourseDAO();
    public ShowStaticsServlet() throws SQLException, ClassNotFoundException {
    }

    public void init() {}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
        request.getRequestDispatcher("/WEB-INF/ShowStatics.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseID = Integer.parseInt(request.getParameter("courseid"));

        response.setContentType("text/html");
        ArrayList<Integer> arr = new ArrayList<>();
        try {
            arr = studentCourseDAO.getCourseStatics(courseID, (Integer) request.getSession().getAttribute("userid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //check if there are an error or there are no data to show
        if(arr.size()==0){
            String message = "something went wrong , maybe you enterd a invalid course id or you are not the teacher of this course";
            request.setAttribute("message",message);
            request.setAttribute("color","red");
            request.getRequestDispatcher("/WEB-INF/ShowStatics.jsp").forward(request, response);
            return;
        }
        //count the statics and print them
        int max = findMax(arr);
        int min = findMin(arr);
        double avg = findAverage(arr);
        double median = findMedian(arr);
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Show statics</title></head>");
        out.println("<body>");
        out.println("<h1>Course Statics</h1>");
        out.println("<h2>Course marks average : "+avg +"</h2>");
        out.println("<h2>Course maximum mark : "+max +"</h2>");
        out.println("<h2>Course minimum mark : "+min +"</h2>");
        out.println("<h2>Course median of marks : "+median +"</h2>");
        out.println("<a href=\"/home\" >back to home page</a></body></html>");
    }

    public void destroy() {
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