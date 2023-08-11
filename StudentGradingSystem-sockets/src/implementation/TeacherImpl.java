package implementation;

import java.io.IOException;
import java.net.Socket;

public class TeacherImpl extends ClientImpl{

    public TeacherImpl(Socket clientSocket) throws IOException {
        super(clientSocket);
    }

    public  void teacherDashboard() throws IOException {
        while (true){
            System.out.println("--------------------------------------");
            System.out.println("teacher dashboard \n 1-Add student to course \n 2-Enter/update marks  \n 3-exit");

            String choice = scanner.nextLine();

            if(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")){
                System.out.println("invalid input");
            }
            else {
                //call function depend on user choice
                //add student to course
                if (choice.equals("1")) {
                    addStudentToCourse();
                }
                //enter marks
                else if(choice.equals("2")){
                    setMarks();
                }
                //exit
                else if(choice.equals("3")){
                    break;
                }
            }
        }
    }

    private void addStudentToCourse() throws IOException {
        String toServer="";
        while (true) {
            //enter the student and course data and put it on string
            toServer = "ADDSTUDENTTOCOURSE|";
            System.out.println("enter the course id");
            String courseID = scanner.nextLine();
            System.out.println("enter student id ");
            String studentID = scanner.nextLine();
            toServer += courseID + "|" + studentID+"|"+userID;

            //send them to server and wait for response
            sendToServer(toServer);
            String fromServer = receivFromServer();

            //print a message that says the student add to course successfully or not
            if (fromServer.length() > 1) {
                System.out.println(fromServer);
            } else if (Integer.parseInt(fromServer) >= 0) {
                System.out.println("student added to course successfully");
            } else {
                System.out.println("something went wrong");
            }
            break;
        }
    }

    private void setMarks() throws IOException {
        String toServer="";
        while (true) {
            //enter marks and student and course data and put it on string
            toServer = "SETMARKS|";
            System.out.println("enter the course id");
            String courseID = scanner.nextLine();
            System.out.println("enter student id ");
            String studentID = scanner.nextLine();
            System.out.println("enter medterm exam mark ");
            String midExam = scanner.nextLine();
            System.out.println("enter assignments mark ");
            String assignments = scanner.nextLine();
            System.out.println("enter final exam mark ");
            String finalExam = scanner.nextLine();
            toServer+=courseID+"|"+studentID+"|"+midExam+"|"+assignments+"|"+finalExam+"|"+userID;

            //send them to server and wait for response
            sendToServer(toServer);

            String fromServer = receivFromServer();
            //print a message that says the marks updated successfully or not
            if (Integer.parseInt(fromServer) >= 0) {
                System.out.println("marks update successfully");
            }
            else if (fromServer.length() > 1) {
                 System.out.println(fromServer);
            }
             else {
                System.out.println("something went wrong");
            }
            break;
        }
    }
}
