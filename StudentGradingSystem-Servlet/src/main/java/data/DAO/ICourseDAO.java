package data.DAO;

import model.Course;

import java.sql.SQLException;

public interface ICourseDAO {
    public int addCourse(Course course);
    public String getCourseNameByID(int id) throws SQLException;

}
