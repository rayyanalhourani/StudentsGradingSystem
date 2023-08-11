package implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientImpl {
    protected InputStream inputStream = null;
    protected OutputStream outputStream = null;
    public static String userID;
    protected Scanner scanner = new Scanner(System.in);

    public ClientImpl(Socket clientSocket) throws IOException {
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
    }

    public void sendToServer(String message) throws IOException {
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    public String receivFromServer() throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }

    public String loginDashboard() throws IOException {
        System.out.println("-------------------------------------- \nLogin");
        while (true) {
            //send the login details to server then wait
            //the response from server if valid or not and return the role of user
            String toServer = "LOGIN|";
            System.out.println("Enter your ID");
            userID = scanner.nextLine();
            System.out.println("Enter your password");
            String password = scanner.nextLine();
            toServer+=userID+"|"+password;

            sendToServer(toServer);

            String fromServer = receivFromServer();

            if (fromServer.startsWith("valid")) {
                return fromServer.substring(6);
            }
            System.out.println("Wrong ID or password \n");
        }
    }
}
