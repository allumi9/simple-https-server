package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    public static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private String webRoot;
    private int port;
    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webRoot) {
        this.port = port;
        this.webRoot = webRoot;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            LOGGER.error("Exception when creating ServerListenerThread: {}", e.getMessage());
        }

    }

    @Override
    public void run() {
        LOGGER.info(
                "Starting the server. Port: {} Webroot: {}",
                this.port,
                this.webRoot
        );

        try {
            while(serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info("Connection accepted: {}", socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }

        } catch (IOException e) {
            LOGGER.error("Exception when accepting connections: {}", e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }

        LOGGER.info("Closed all connections.");
    }
}
