package data.DAO;

import data.DAO.mappers.CourseMapper;
import data.DBConnection;
import data.Jdbc;
import model.Course;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Validation validation = new Validation();
    private JdbcTemplate jdbcTemplate = new Jdbc().getJdbcTemplate();


    public CourseDAO() throws SQLException, ClassNotFoundException {
    }

    public int addCourse(Course course){
        try{
            ResultSet resultSet;
            //check if user is existed and check if it's a teacher
            boolean Existteacher= validation.isTeacherExist(course.getTeacher_id());
            //check if course is existed
            boolean Existcourse=validation.isCourseExist(course.getCourse_id());

                if (!Existteacher || Existcourse) {
                    return 0;
                }
                else {
                    String query = String.format("INSERT INTO course (course_id, course_name, teacher_id) VALUES ( %s , '%s' , %s )",course.getCourse_id(),course.getCourse_name(),course.getTeacher_id());
                    return jdbcTemplate.update(query);
                }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public String getCourseNameByID(int id) throws SQLException {
        String query="select * from course where course_id ="+id;
        List<Course> courses = jdbcTemplate.query(query,new CourseMapper());
        if(courses.size()>0) {
            return courses.get(0).getCourse_name();
        }
        return "";
    }
}
