package edurekatriviagame;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientSide {
    public static void main(String[] args) throws IOException {
        try {
            String currentAnswer = "";
            Socket mySocket = new Socket("localhost", 8000);
            DataInputStream input = new DataInputStream(mySocket.getInputStream());
            DataOutputStream output = new DataOutputStream(mySocket.getOutputStream());
            BufferedReader first = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            BufferedReader second = new BufferedReader(new InputStreamReader(System.in));
            String answer = first.readLine();
            System.out.println(answer);
            String temp1 = "";
            while(!currentAnswer.equals("stop")){
                String equation = first.readLine();
                System.out.println("Question from the server: What is " + equation);
                temp1 = second.readLine();
                output.writeUTF(temp1);
                output.flush();
                String conclusion = first.readLine();
                System.out.println(conclusion);
            }
            PrintWriter toServer = new PrintWriter(mySocket.getOutputStream(), true);
            toServer.println("stop");
            toServer.flush();
            output.close();
            mySocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
