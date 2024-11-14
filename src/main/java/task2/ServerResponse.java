package task2;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Arrays;

public class ServerResponse {

    public OutputStream outputStream;
    public BufferedWriter bw;

    public ServerResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        bw = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void sendHtmlFile(File file) {
        try {
            byte[] buffer = Files.readAllBytes(file.toPath());

            String[] response = {
            "HTTP/1.1 200 OK\r\n",
            "Server: NewSuperServer\r\n",
            "Content-Type: text/html; charset=utf-8\r\n",
            "Content-Length: " + buffer.length + "\r\n",
            "\r\n"};

            for (String responseHeaderLine : response) {
                bw.write(responseHeaderLine);
                bw.flush();
            }

            outputStream.write(buffer);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendHtmlText(File file) {
        try(FileInputStream fis = new FileInputStream(file)) {

            StringBuilder textOfFile = new StringBuilder();
            int IByte;
            while ((IByte = fis.read()) != -1) {
                textOfFile.append((char) IByte);
            }

            String[] response = {
                    "HTTP/1.1 200 OK\r\n",
                    "Server: NewSuperServer\r\n",
                    "Content-Type: text/html; charset=utf-8\r\n",
                    "Content-Length: " + textOfFile.toString().length() + "\r\n",
                    "\r\n"};

            for (String responseHeaderLine : response) {
                bw.write(responseHeaderLine);
                bw.newLine();
                bw.flush();
            }

            bw.write(textOfFile.toString());
            bw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendVerNotSupported() {
        try {
            String[] response = {"HTTP/1.1 505 HTTP Version Not Supported\r\n","Server: NewSuperServer\r\n","\r\n"};

            for (String responseHeaderLine : response) {
                bw.write(responseHeaderLine);
                bw.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNotFound() {
        try {
            String[] response = {"HTTP/1.1 404 Not Found\r\n", "Server: NewSuperServer\r\n", "\r\n"};

            for (String responseHeaderLine : response) {
                bw.write(responseHeaderLine);
                bw.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
