package data.DAO;

import data.DAO.mappers.CourseMapper;
import data.DAO.mappers.StudentCourseMapper;
import data.DAO.mappers.UserMapper;
import data.DBConnection;
import data.Jdbc;
import model.Course;
import model.StudentCourse;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Validation {
    private JdbcTemplate jdbcTemplate = new Jdbc().getJdbcTemplate();


    public Validation() throws SQLException, ClassNotFoundException {
    }

    public boolean isUserExist(int userid) throws SQLException {
        String query = "select * from user where user_id=" + userid;
        List<User> users = jdbcTemplate.query(query, new UserMapper());
        boolean User=false;
        if (!users.isEmpty()) {
            User = true;
        }

        return User;
    }

    public boolean isTeacherExist(int id) throws SQLException {
        String query = "select * from user where user_id=" +id +" and role='teacher'";
        List<User> users = jdbcTemplate.query(query, new UserMapper());
        boolean teacher=false;
        if(!users.isEmpty()){
            teacher=true;
        }
        return teacher;
    }

    public boolean isCourseExist(int id) throws SQLException {
        String query = "select * from course where course_id=" + id;
        List<Course> courses = jdbcTemplate.query(query, new CourseMapper());
        boolean course=false;
        if(!courses.isEmpty()){
            course=true;
        }
        return course;
    }

    public boolean isUserTheTeacherOfCourse(int  teacherID, int courseID ) throws SQLException {
        String query = "select * from course where course_id=" + courseID +" and teacher_id="+teacherID;
        List<Course> courses = jdbcTemplate.query(query, new CourseMapper());
        boolean isTeacherToCourse= false;
        if(!courses.isEmpty()){
            isTeacherToCourse=true;
        }
        return isTeacherToCourse;
    }

    public boolean isStudentExist(int studentID) throws SQLException {
        String query = "select * from user where user_id=" + studentID+" and role='student'";
        List<User> users = jdbcTemplate.query(query, new UserMapper());
        boolean validStudent=false;
        if(!users.isEmpty()){
            validStudent=true;
        }
        return validStudent;
    }

    public boolean isStudentAddedToCourse(int studentID , int courseID) throws SQLException {
        String query = "select * from user_course where user_user_id=" + studentID +" and course_course_id="+courseID;
        List<StudentCourse> users = jdbcTemplate.query(query, new StudentCourseMapper());
        boolean addedStudent=false;
        if(!users.isEmpty()){
            addedStudent=true;
        }
        return addedStudent;
    }
}
