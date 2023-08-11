package implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServerImple {
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private DBConnection connection = DBConnection.getInstance();
    private Statement statement = connection.getStatement();
    private Validation validation=new Validation();

    public ServerImple(Socket clientSocket) throws IOException, SQLException, ClassNotFoundException {
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
    }

    public void sendToClient(String message) throws IOException {
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    public String receivFromClient() throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }

    public void login(String massage) throws IOException, SQLException {
        //receive data from client and split it into variables
        String[] arr = massage.split("[|]", 3);
        int userid = Integer.parseInt(arr[1]);
        String sentPass = arr[2];

        //get the user details and compare the original password with sent password
        ResultSet resultSet = statement.executeQuery("select * from user where User_ID=" + userid);
        String pass = "", role = "";
        while (resultSet.next()) {
            pass = resultSet.getString("Password");
            role = resultSet.getString("Role");
        }
        //return valid and the role of user if its valid
        if (!pass.equals("") && sentPass.equals(pass)) {
            sendToClient("valid|" + role);
        } else {
            sendToClient("invalid");
        }
    }

    public void addUser(String massage) throws IOException, SQLException {
        //receive data from client and split it into variables
        String[] arr = massage.split("[|]", 5);
        String id = arr[2];
        String name = arr[3];
        String password = arr[4];
        String role = arr[1];

        //check if the user exist or not before add it
        boolean existedUser = validation.isUserExist(Integer.parseInt(arr[2]));
        while (true) {
            //if user exist send message for that else insert it and send message for that
            if (existedUser) {
                sendToClient("this ID used before , try again");
            } else {
                String query = String.format("INSERT INTO user (user_id, user_name, password, role) VALUES (%s , '%s' , '%s' , '%s')",id,name,password,role);
                int test = statement.executeUpdate(query);

                sendToClient(String.valueOf(test));
            }
            break;
        }
    }

    public void addCourse(String massage) throws IOException, SQLException {
        //receive data from client and split it into variables
        String[] arr = massage.split("[|]");
        int id= Integer.parseInt(arr[1]);
        String name=arr[2];
        int teacherID= Integer.parseInt(arr[3]);

        //check if teacher and course are exist or not
        boolean existedTeacher=validation.isTeacherExist(teacherID);
        boolean existedCourse=validation.isCourseExist(id);

        while (true) {
            //if teacher not found send message says that
            if (!existedTeacher) {
                sendToClient("invalid teacher id");
            }
            //if exist send message says that
            else if(existedCourse){
                sendToClient("this ID used before , try again");
            }
            else {
                String query = String.format("INSERT INTO course (course_id, course_name, teacher_id) VALUES ( %s , '%s' , %s )",id,name,teacherID);
                int test = statement.executeUpdate(query);
                sendToClient(String.valueOf(test));
            }
            break;
        }
    }

    public void addStudentToCourse(String massage) throws IOException, SQLException {
        //receive data from client and split it into variables
        String[] arr = massage.split("[|]");
        int courseID = Integer.parseInt(arr[1]);
        int studentID = Integer.parseInt(arr[2]);
        int userID = Integer.parseInt(arr[3]);
        //check if the course and user is exists
        boolean existedCourse=validation.isCourseExist(courseID);
        boolean existedStudent=validation.isStudentExist(studentID);
        //check if the student added before to course
        boolean addedStudent= validation.isStudentAddedToCourse(studentID,courseID);
        //check if the user is the teacher of course
        boolean teacherOfCourse=validation.isUserTheTeacherOfCourse(userID,courseID);

        while (true) {
            //if there ary any problem with adding send a message for client that says the problem
            if (!existedCourse) {
                sendToClient("invalid course id");
            }
            else if (!existedStudent) {
                sendToClient("invalid student id");
            }
            else if (addedStudent) {
                sendToClient("this student already add to this course");
            }
            else if(!teacherOfCourse){
                sendToClient("this is not your course");
            }
            else {
                String query = String.format("INSERT INTO user_course (user_user_id, course_course_id) VALUES (%s , %s)",arr[2],arr[1]);
                int test = statement.executeUpdate(query);

                sendToClient(String.valueOf(test));
            }
            break;
        }
    }

    public void setMarks(String massage) throws IOException, SQLException {
        while (true){
            //receive data from client and split it into variables
            String[] arr = massage.split("[|]");
            int courseID = Integer.parseInt(arr[1]);
            int studentID = Integer.parseInt(arr[2]);
            String midExam = arr[3];
            String assignments =arr[4];
            String finalExam =arr[5];
            int userID = Integer.parseInt(arr[6]);
            //check if student is added to course
            boolean isStudentinCourse=validation.isStudentAddedToCourse(studentID,courseID);
            //check if user is the teacher of course
            boolean isTeacherOfcourse=validation.isUserTheTeacherOfCourse(userID,courseID);

            while (true) {
                //if there ary any problem with adding send a message for client that says the problem
                if (!isStudentinCourse) {
                    sendToClient("this student is not in that course");
                }
                else if (!isTeacherOfcourse) {
                    sendToClient("you are not the teacher of that course");
                }
                else {
                    String query = String.format("UPDATE user_course SET  midterm_grade=%s, assignments_grade=%s , final_exam_grades=%s WHERE user_user_id=%s and course_course_id=%S",midExam,assignments,finalExam,studentID,courseID);
                    int test = statement.executeUpdate(query);
                    sendToClient(String.valueOf(test));
                }
                break;
            }
        }
    }

    public void getMarks(String massage) throws IOException, SQLException {
        while (true){
            //receive data from client and split it into variables
            String[] arr = massage.split("[|]");
            int studentID = Integer.parseInt(arr[1]);
            //get the user marks and courses
            ResultSet resultSet = statement.executeQuery("select * from user_course where user_user_id=" + studentID);

            //put the marks in string and send it to server
            String result="";
            ArrayList<String> coursesIDS = new ArrayList<>();
            while (resultSet.next()){
                result+="midterm grade : "+resultSet.getString("midterm_grade")+"|";
                result+="assignments grade : "+resultSet.getString("assignments_grade")+"|";
                result+="final exam grade : "+resultSet.getString("final_exam_grades")+"|";
                coursesIDS.add(resultSet.getString("course_course_id"));
            }
            //put the course names in string and send it to server
            String courseNames="";
            for (String id: coursesIDS) {
                resultSet = statement.executeQuery("select course_name from course where course_id=" + id);

                while (resultSet.next()){
                    courseNames+=resultSet.getString("course_name")+"|";
                }
            }
            //send the data to client
            sendToClient(result);
            sendToClient(courseNames);
            break;
        }
    }


}
