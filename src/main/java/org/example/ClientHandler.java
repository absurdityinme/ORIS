package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                // send questions
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                // get answers
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))
        ) {
            // get question
            String question;
            while ((question = br.readLine()) != null) {
                if (!question.equals("Q")) {
                    // send answer
                    bw.write(getAnswer(question));
                    bw.newLine();
                    bw.flush();
                }
                else {
                    ManyClientServer.serverClose();
                    break;
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAnswer(String question) {
        return switch (question) {
            case "Любимый исполнитель?" -> "Шары";
            case "Как тебя зовут?" -> "У меня нет имени";
            case "Какая погода?" -> "Всегда хорошая";
            case "Слышно ли басистов?" -> "Что вы сказали?";
            case "Какая математика самая лучшая?" -> "Дискретная";
            case "Сколько всего знаков в Ведьмаке?" -> "5";
            default -> "Извините, не могу ответить на данный вопрос";
        };
    }
}
