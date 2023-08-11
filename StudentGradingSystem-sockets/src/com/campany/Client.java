package com.campany;

import implementation.AdminImpl;
import implementation.ClientImpl;
import implementation.StudentImpl;
import implementation.TeacherImpl;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String serverAddress = "localhost";
        final int serverPort = 12345;
        try {
            Socket clientSocket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to the server at " + serverAddress + ":" + serverPort);

            // Create a scanner to read user input from the console
            Scanner scanner = new Scanner(System.in);
            ClientImpl client = new ClientImpl(clientSocket);
            AdminImpl admin = new AdminImpl(clientSocket);
            TeacherImpl teacher = new TeacherImpl(clientSocket);
            StudentImpl student = new StudentImpl(clientSocket);
            String role="";
            while (true) {
                //login and return the role for user
                role=client.loginDashboard();

                //show the dashboard depend on the role
                if(role.equals("admin")){
                    admin.adminDashboard();
                }
                else if(role.equals("teacher")){
                    teacher.teacherDashboard();
                }

                else if(role.equals("student")){
                    student.studentDashboard();
                }
                break;
            }

            // Close the socket and release resources
            clientSocket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
