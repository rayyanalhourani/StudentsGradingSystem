package data.DAO;

import data.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Validation{
    private final Statement statement = DBConnection.getInstance().getStatement();

    public Validation() throws SQLException, ClassNotFoundException{
    }

    public boolean isUserExist(int userid) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from user where user_id=" + userid);
        boolean User=false;
        while(resultSet.next()){
            User=true;
        }
        return User;
    }

    public boolean isTeacherExist(int id) throws SQLException {
        boolean teacher=false;
        ResultSet resultSet = statement.executeQuery("select * from user where user_id=" +id +" and role='teacher'");
        while(resultSet.next()){
            teacher=true;
        }
        return teacher;
    }

    public boolean isCourseExist(int id) throws SQLException {
        boolean course=false;
        ResultSet resultSet = statement.executeQuery("select * from course where course_id=" + id);
        while(resultSet.next()){
            course=true;
        }
        return course;
    }

    public boolean isUserTheTeacherOfCourse(int  teacherID, int courseID ) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from course where course_id=" + courseID +" and teacher_id="+teacherID);
        boolean isTeacherToCourse= false;
        while (resultSet.next()){
            isTeacherToCourse=true;
        }
        return isTeacherToCourse;
    }

    public boolean isStudentExist(int studentID) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from user where user_id=" + studentID+" and role='student'");
        boolean validStudent=false;
        while(resultSet.next()){
            validStudent=true;
        }
        return validStudent;
    }

    public boolean isStudentAddedToCourse(int studentID , int courseID) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from user_course where user_user_id=" + studentID +" and course_course_id="+courseID);
        boolean addedStudent=false;
        while(resultSet.next()){
            addedStudent=true;
        }
        return addedStudent;
    }
}
