package data.DAO.mappers;

import model.StudentCourse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseMapper implements RowMapper<StudentCourse> {
    @Override
    public StudentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
        int studentID = rs.getInt("user_user_id");
        int coursesID = rs.getInt("course_course_id");
        int mid,assignment,finalexam;

        if(rs.getString("midterm_grade")==null){
            mid=0;
        }else {
        mid =rs.getInt("midterm_grade");
        }

        if(rs.getString("assignments_grade")==null){
            assignment=0;
        }else {
            assignment =rs.getInt("assignments_grade");
        }

        if(rs.getString("final_exam_grades")==null){
            finalexam=0;
        }else {
            finalexam =rs.getInt("final_exam_grades");
        }

        return new StudentCourse(studentID,coursesID,mid,assignment,finalexam);
    }
}
