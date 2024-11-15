package task2;

import java.io.*;
import java.net.Socket;

public class RequestHandler {

    public static final String ROOT_DIRECTORY = "html/";
    public static void handle(Socket clientSocket) {
        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            String headerLine = br.readLine();

            String[] firstLine = headerLine.split("\\s+");
            String method = firstLine[0];
            String uri = firstLine[1].substring(1);
            String httpVer = firstLine[2];

            System.out.println(method + " " + uri + " " + httpVer);
            while (headerLine != null && !headerLine.equals("")) {
                headerLine = br.readLine();
                System.out.println(headerLine);
            }
            ServerResponse response = new ServerResponse(clientSocket.getOutputStream());
            if (!httpVer.equals("HTTP/1.1")) {
                response.sendVerNotSupported();
                clientSocket.close();
            }
            else {
                File file = new File(ROOT_DIRECTORY + uri);
                if (!file.exists()) {
                    response.sendNotFound();
                }
                else {
                    response.sendHtmlFile(file);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
