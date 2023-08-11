package data.DAO;

import model.StudentCourse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IStudntCourseDAO {
    public int addStudentToCourse(int courseID , int studentID , int userID) throws SQLException;
    public int setMarks(StudentCourse studentCourse, int userID) throws SQLException;
    public List<StudentCourse> getStudentMarks(int studentID) throws SQLException;
    public ArrayList<Integer> getCourseStatics(int courseID , int userID) throws SQLException;
}
