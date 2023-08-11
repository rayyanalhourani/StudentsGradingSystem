package implementation;

import java.io.IOException;
import java.net.Socket;

public class StudentImpl extends ClientImpl{
    public StudentImpl(Socket clientSocket) throws IOException {
        super(clientSocket);
    }

    public void studentDashboard() throws IOException {
        while (true){
            System.out.println("--------------------------------------");
            System.out.println("teacher dashboard \n 1-show marks  \n 2-exit");

            String choice = scanner.nextLine();

            String toServer="";
            //call function depend on user choice
            if(!choice.equals("1") && !choice.equals("2")){
                System.out.println("invalid input");
            }

            //get marks
            if(choice.equals("1")){
                getMarks();
            }
            //exit
            else if(choice.equals("2")){
                break;
            }
        }
    }

    private void getMarks() throws IOException {
        String toServer="";
        while (true) {
            //send the user id to server the wait the response that contains the marks and courses names
            //and print them
            toServer = "GETMARKS|" + userID;

            sendToServer(toServer);
            //receive marks and courses names
            String marks = receivFromServer();
            String coursenames = receivFromServer();

            //split the string then put it on array
            String[] marksArr = marks.split("[|]");
            String[] coursenamesArr = coursenames.split("[|]");

            //print all marks
            int c = 0;
            int index = 0;
            System.out.println("courses ");
            for (int i = 0; i < marksArr.length; i++) {

                if (i % 3 == 0) {
                    System.out.println("\ncourse name : " + coursenamesArr[index]);
                    index++;
                }
                System.out.println(marksArr[i]);
            }
            break;
        }

    }
}
