package implementation;

import java.io.IOException;
import java.net.Socket;

public class AdminImpl extends ClientImpl{
    public AdminImpl(Socket clientSocket) throws IOException {
        super(clientSocket);
    }

    public void adminDashboard() throws IOException {
        while (true){
            System.out.println("--------------------------------------");
            System.out.println("Admin dashboard \n 1-Add user \n 2-Add course \n 3-exit");
            String choice = scanner.nextLine();
            if(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")){
                System.out.println("invalid input");
            }
            else {
                //call function depend on user choice
                //add user
                if(choice.equals("1")){
                    addUser();
                }
                //add course
                else if(choice.equals("2")){
                    addCourse();
                }
                //exit
                else if(choice.equals("3")){
                    break;
                }
            }

        }
    }

    private void addUser() throws IOException {
        //enter the user data and put it on string
        String toServer="";
        toServer+="ADDUSER|";
        System.out.println("enter the user id");
        String userID = scanner.nextLine();
        System.out.println("enter user name ");
        String userName = scanner.nextLine();
        System.out.println("enter the password");
        String password = scanner.nextLine();
        System.out.println("enter 1 to set role as teacher , and 2 for set role as student");
        String setRole ="";
        while (true){
            setRole=scanner.nextLine();
            if(setRole.equals("1")){
                setRole="teacher";
                break;
            }
            else if(setRole.equals("2")){
                setRole="student";
                break;
            }
            else {
                System.out.println("invalid input , please enter 1 or 2");
            }
        }
        while (true) {
            toServer += setRole + "|" + userID + "|"+userName+"|" +password;
            //send the string to server and wait response
            sendToServer(toServer);

            String message = receivFromServer();
            //print message for user that says the user add or not
            if(message.equals("1")){
                System.out.println("User Add successfully");
                break;
            }
            else{
                System.out.println("user not added");
                break;
            }
        }
    }

    private void addCourse() throws IOException {
        String toServer="";
        while (true) {
            //enter the user data and put it on string
            toServer = "ADDCOURSE|";
            System.out.println("enter the course id");
            String courseID = super.scanner.nextLine();
            System.out.println("enter course name ");
            String courseName = scanner.nextLine();
            System.out.println("enter teacher id ");
            String teacherID = scanner.nextLine();
            toServer += courseID + "|" + courseName + "|" + teacherID;

            //send the string to server and wait response
            sendToServer(toServer);

            String fromServer = receivFromServer();
            //print message for user that says the course add or not
            if (fromServer.length() > 1) {
                System.out.println(fromServer);
            }
            else if(Integer.parseInt(fromServer)>=0){
                System.out.println("course Add successfully");
            }
            else {
                System.out.println("something went wronge");
            }
            break;
        }
    }
}


