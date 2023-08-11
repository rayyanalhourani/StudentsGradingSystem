package com.campany;

import implementation.ServerImple;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    public static void main(String[] args) {
        final int port = 12345; // Change this to the desired port number
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                handleClient();
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleClient() throws IOException, ClassNotFoundException, SQLException{
            ServerImple serverImple =new ServerImple(clientSocket);

            while (true) {
                //call the function depend on the first part of received string from client
                String message = serverImple.receivFromClient();
                if (message.startsWith("LOGIN")) {
                    serverImple.login(message);
                }
                else if(message.startsWith("ADDUSER")){
                    serverImple.addUser(message);
                }

                else if(message.startsWith("ADDCOURSE")){
                    serverImple.addCourse(message );
                }

                else if(message.startsWith("ADDSTUDENTTOCOURSE")){
                    serverImple.addStudentToCourse(message);
                }

                else if(message.startsWith("SETMARKS")){
                    serverImple.setMarks(message);
                }
                else if(message.startsWith("GETMARKS")){
                    serverImple.getMarks(message);
                }
            }
        }
    }
}

