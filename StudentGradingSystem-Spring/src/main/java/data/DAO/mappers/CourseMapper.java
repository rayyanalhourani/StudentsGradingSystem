package data.DAO.mappers;

import model.Course;
import model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("course_id");
        String name = rs.getString("course_name");
        int teacherID= rs.getInt("teacher_id");
        return new Course(id,name ,teacherID);
    }
}
