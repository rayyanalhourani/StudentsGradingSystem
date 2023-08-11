package data.DAO;

import data.DAO.mappers.StudentCourseMapper;
import data.DBConnection;
import data.Jdbc;
import model.StudentCourse;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentsCourseDAO implements IStudntCourseDAO{
    private Validation validation = new Validation();
    private JdbcTemplate jdbcTemplate = new Jdbc().getJdbcTemplate();


    public StudentsCourseDAO() throws SQLException, ClassNotFoundException {
    }

    public int addStudentToCourse(int courseID , int studentID , int userID) throws SQLException {
        //check if course is exist
        boolean ExistCourse= validation.isCourseExist(courseID);

        //check if the added user is existed and his role is student
        boolean ExistStudent=validation.isStudentExist(studentID);

        //check if user added to course
        boolean addedStudent=validation.isStudentAddedToCourse(studentID , courseID);

        //check if user is the teacher of the course
        boolean isTeacherOfcourse = validation.isUserTheTeacherOfCourse(userID,courseID);

            if (!ExistCourse || !ExistStudent || addedStudent || !isTeacherOfcourse) {
                return 0;
            }
            else {
                try {
                    String query = String.format("INSERT INTO user_course (user_user_id, course_course_id) VALUES (%s , %s)",studentID,courseID);
                    return jdbcTemplate.update(query);
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
            return 0;
    }

    public int setMarks(StudentCourse studentCourse,int userID) throws SQLException {
        try {
            //check of the user is in course
            boolean isStudentinCourse = validation.isStudentAddedToCourse(studentCourse.getStudent_id() , studentCourse.getCourse_id());

            //check if the user is the teacher of course
            boolean isTeacherOfcourse = validation.isUserTheTeacherOfCourse(userID,studentCourse.getCourse_id());

            if (!isStudentinCourse || !isTeacherOfcourse) {
                return 0;
            } else {
                String query = String.format("UPDATE user_course SET  midterm_grade=%s, assignments_grade=%s , final_exam_grades=%s WHERE user_user_id=%s and course_course_id=%S",
                        studentCourse.getMedterm_mark(), studentCourse.getAssignments_mark(), studentCourse.getFinal_exam_mark(),
                        studentCourse.getStudent_id(), studentCourse.getCourse_id());
                return jdbcTemplate.update(query);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public List<StudentCourse> getStudentMarks(int studentID) throws SQLException {
        List<StudentCourse> studentCourses;
        try {
            String query = "select * from user_course where user_user_id=" + studentID;
            studentCourses = jdbcTemplate.query(query,new StudentCourseMapper());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return studentCourses;
    }

    public ArrayList<Integer> getCourseStatics(int courseID , int userID) throws SQLException {
        ArrayList<Integer> marks = new ArrayList<>();
        try {
            //check if the course is valid
            boolean validCourse = validation.isCourseExist(courseID);
            //check if the user is the teacher of course
            boolean isTeacherOfcourse = validation.isUserTheTeacherOfCourse(userID,courseID);

            if (!validCourse || !isTeacherOfcourse) {
                return marks;
            } else {
                String query = "select * from user_course where course_course_id=" + courseID;
                List<StudentCourse> studentCourses = jdbcTemplate.query(query,new StudentCourseMapper());

                for (StudentCourse sc:studentCourses) {
                    int mid = sc.getMedterm_mark();
                    int assignment = sc.getAssignments_mark();
                    int finalexam = sc.getFinal_exam_mark();
                    marks.add(mid+assignment+finalexam);
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
            marks.clear();
        }
        return marks;
    }

}