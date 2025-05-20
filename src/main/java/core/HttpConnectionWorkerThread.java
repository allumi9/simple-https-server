package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.HttpParser;
import parser.HttpParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    public static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private final HttpParser httpParser;
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
        this.httpParser = new HttpParser();
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            httpParser.parseHttpRequest(inputStream);

            final String htmlResponse = "<html><head><title>My http server</title></head><body><h1>Hello</h1></body><html>";

            final String CRLF = "\r\n";

            final String httpResponse = "HTTP/1.1 200 OK" + CRLF
                    + "Content-Type: text/html" + CRLF
                    + "Content-Length: " + htmlResponse.getBytes().length + CRLF
                    + CRLF + htmlResponse + CRLF;

            outputStream.write(httpResponse.getBytes());


            LOGGER.info("Connection processing finished");
        } catch (IOException e) {
            LOGGER.error("Exception when processing connection: {}", e.getMessage());
        } catch (HttpParserException e) {
            LOGGER.error("Exception when parsing the request: {}", e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {}

        }
    }
}
