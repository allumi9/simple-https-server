import configuration.ServerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info(
                "Starting the server. Port: {} Webroot: {}",
                ServerConfig.PORT,
                ServerConfig.WEBROOT
        );

        try {
            ServerSocket serverSocket = new ServerSocket(ServerConfig.PORT);
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            final String htmlResponse = "<html><head><title>My http server</title></head><body><h1>Hello</h1></body><html>";

            final String CRLF = "\n\r";

            final String httpResponse = "HTTP/1.1 200 OK" + CRLF
                    + "Content-Type: text/html" + CRLF
                    + "Content-Length: " + htmlResponse.getBytes().length + CRLF
                    + CRLF + htmlResponse + CRLF;

            outputStream.write(httpResponse.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
            LOGGER.info("Closed all connections.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
