package data.DAO;

import data.DBConnection;
import model.StudentCourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentCourseDAO implements IStudntCourseDAO{
    private Statement statement = DBConnection.getInstance().getStatement();
    private Validation validation = new Validation();


    public StudentCourseDAO() throws SQLException, ClassNotFoundException {
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
                    return statement.executeUpdate(query);
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
                return statement.executeUpdate(query);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public ArrayList<StudentCourse> getStudentMarks(int studentID) throws SQLException {
        ArrayList<StudentCourse> marks = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select * from user_course where user_user_id=" + studentID);

            while (resultSet.next()){
                int mid = Integer.parseInt(resultSet.getString("midterm_grade"));
                int assignment = Integer.parseInt(resultSet.getString("assignments_grade"));
                int finalexam = Integer.parseInt(resultSet.getString("final_exam_grades"));
                int coursesID = Integer.parseInt(resultSet.getString("course_course_id"));
                marks.add(new StudentCourse(studentID , coursesID,mid,assignment,finalexam));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return marks;
    }

    public ArrayList<Integer> getCourseStatics(int courseID , int userID) throws SQLException {
        ResultSet resultSet;
        ArrayList<Integer> marks = new ArrayList<>();
        try {
            //check if the course is valid
            boolean validCourse = validation.isCourseExist(courseID);
            //check if the user is the teacher of course
            boolean isTeacherOfcourse = validation.isUserTheTeacherOfCourse(userID,courseID);

            if (!validCourse || !isTeacherOfcourse) {
                return marks;
            } else {
                resultSet = statement.executeQuery("select * from user_course where course_course_id=" + courseID);

                while (resultSet.next()){
                    int mid = Integer.parseInt(resultSet.getString("midterm_grade"));
                    int assignment = Integer.parseInt(resultSet.getString("assignments_grade"));
                    int finalexam = Integer.parseInt(resultSet.getString("final_exam_grades"));
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