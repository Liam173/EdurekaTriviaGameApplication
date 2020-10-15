package edurekatriviagame;

import java.io.*;
import java.net.*;

public class ServerSide {
    private static int store = 0;
    public static String equation() {
        int randomNumber1 = (int) (Math.random() * 10) + 1;
        int randomNumber2 = (int) (Math.random() * 10) + 1;
        int randomSymbol = (int) (Math.random() * 3) + 0;
        String array[] = {"+", "-", "/", "*"};
        String temp = array[randomSymbol];
        String output = randomNumber1 + " " + temp + " " + randomNumber2 + " = ";
        if (temp.equals("+")) {
            store = randomNumber1 + randomNumber2;
        } else if (temp.equals("-")) {
            store = randomNumber1 - randomNumber2;
        } else if (temp.equals("/")) {
            store = randomNumber1 / randomNumber2;
        } else if (temp.equals("*")) {
            store = randomNumber1 * randomNumber2;
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverListener = new ServerSocket(8000);
            System.out.println("Server is running");
            Socket mySocket = serverListener.accept();
            DataInputStream input = new DataInputStream(mySocket.getInputStream());
            PrintWriter toClient = new PrintWriter(mySocket.getOutputStream(), true);
            toClient.println("Welcome to Edureka  Trivia Game");
            toClient.flush();
            String problem = "";
            String temp1 = "";
            while (!temp1.equals("stop")) {
                PrintWriter outputTemp = new PrintWriter(mySocket.getOutputStream(), true);
                outputTemp.println(problem = equation());
                outputTemp.flush();
                String temp = (String) input.readUTF();
                if(temp1.equals("stop")){
                    System.out.println("The client says: stop");
                } else
                if (Integer.parseInt(temp) == store) {
                    toClient.println("Welldone, that's correct!");
                } else {
                    toClient.println("Wrong Answer!, the correct answer is: " + store);
                }
                System.out.println("Answer from Client: " + temp);
            }
            input.close();
            mySocket.close();
            serverListener.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
