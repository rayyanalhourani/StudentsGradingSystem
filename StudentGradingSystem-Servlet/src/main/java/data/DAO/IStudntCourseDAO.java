package data.DAO;

import model.StudentCourse;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IStudntCourseDAO {
    public int addStudentToCourse(int courseID , int studentID , int userID) throws SQLException;
    public int setMarks(StudentCourse studentCourse, int userID) throws SQLException;
    public ArrayList<StudentCourse> getStudentMarks(int studentID) throws SQLException;
    public ArrayList<Integer> getCourseStatics(int courseID , int userID) throws SQLException;
}
