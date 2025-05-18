import configuration.ServerConfig;
import core.ServerListenerThread;

public class HttpServer {
    public static void main(String[] args) {
        ServerListenerThread serverListenerThread = new ServerListenerThread(ServerConfig.PORT, ServerConfig.WEBROOT);
        serverListenerThread.start();

    }

}
