package parser;

import core.HttpConnectionWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private static final int CR = 0x20;
    private static final int LF = 0x20;
    private static final int SP = 0x20;

    private InputStreamReader inputStreamReader;
    private HttpRequest httpRequest;
    public static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        httpRequest = new HttpRequest();

        parseRequestLine();
        parseHeaders();
        parseBody();

        return httpRequest;
    }

    private void parseRequestLine() throws IOException {
        int _byte;
        while((_byte = inputStreamReader.read()) >= 0) {
            if (_byte == CR) {
                _byte = inputStreamReader.read();
                if (_byte == LF) {
                    return;
                }
            }
        }
    }
    private void parseHeaders() {
    }
    private void parseBody() {
    }


}
