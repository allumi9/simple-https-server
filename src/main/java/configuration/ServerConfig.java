package configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ServerConfig {

    private static final Config config = ConfigFactory.parseResources("server.conf").resolve();

    public static final String WEBROOT = config.getString("server.webroot");
    public static final int PORT = config.getInt("server.port");

}
