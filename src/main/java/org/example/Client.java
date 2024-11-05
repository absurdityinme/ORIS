package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket clientSocket = new Socket("127.0.0.1", ManyClientServer.SERVER_PORT);
                // send questions
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                // get answers
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                Scanner sc = new Scanner(System.in)
                )
        {
            String question;
            do {
                System.out.print("Введите ваш вопрос: ");
                question = sc.nextLine();

                // send to server
                bw.write(question);
                bw.newLine();
                bw.flush();

                String answer;
                // get answer
                if ((answer = br.readLine()) != null) System.out.println("Ответ: " + answer);
            }
            while (!question.equals("Q"));
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
