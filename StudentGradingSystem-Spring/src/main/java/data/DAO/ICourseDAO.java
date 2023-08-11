package data.DAO;

import model.Course;

public interface ICourseDAO {
    public int addCourse(Course course);
    public Course getCourseByID(int id);
    public void getCourseStatistics(int courseID , int teacherID);
}
