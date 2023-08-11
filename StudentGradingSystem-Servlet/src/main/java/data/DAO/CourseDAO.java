package data.DAO;

import data.DBConnection;
import model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseDAO implements ICourseDAO{
    private final Statement statement = DBConnection.getInstance().getStatement();
    private Validation validation = new Validation();

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
                    return statement.executeUpdate(query);
                }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public String getCourseNameByID(int id) throws SQLException {
        String query="select * from course where course_id ="+id;
        ResultSet resultSet = statement.executeQuery(query);
        String name = "";
        while (resultSet.next()){
            name = resultSet.getString("course_name");
        }
        return name;
    }
}
