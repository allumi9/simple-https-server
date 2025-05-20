package parser;

import core.HttpConnectionWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private static final int CR = 0x0D;
    private static final int LF = 0x0A;
    private static final int SP = 0x20;

    private InputStreamReader inputStreamReader;
    private HttpRequest httpRequest;
    public static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException, HttpParserException {
        inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        httpRequest = new HttpRequest();

        parseRequestLine();
        parseHeaders();
        parseBody();

        return httpRequest;
    }

    private void parseRequestLine() throws IOException, HttpParserException {
        var processingBuffer = new StringBuilder();
        int _byte;
        boolean isMethodParsed = false, isTargetParsed = false;

        while((_byte = inputStreamReader.read()) >= 0) {
            if (_byte == CR) {
                _byte = inputStreamReader.read();
                if (_byte == LF) {
                    if (!isMethodParsed || !isTargetParsed) {
                        throw new HttpParserException(HttpStatusCode.BAD_REQUEST);
                    }
                    LOGGER.info("Version: {}", processingBuffer);
                    processingBuffer.delete(0, processingBuffer.length());
                    return;
                } else {
                    throw new HttpParserException(HttpStatusCode.BAD_REQUEST);
                }
            }

            if (_byte == SP) {
                // TODO Process previous data
                if (!isMethodParsed) {
                    LOGGER.info("Method: {}", processingBuffer);
                    httpRequest.setMethod(processingBuffer.toString());
                    isMethodParsed = true;
                    processingBuffer.delete(0, processingBuffer.length());
                } else if (!isTargetParsed) {
                    LOGGER.info("Target resource: {}", processingBuffer);
                    isTargetParsed = true;
                    processingBuffer.delete(0, processingBuffer.length());
                } else {
                    throw new HttpParserException(HttpStatusCode.BAD_REQUEST);
                }
            } else {
                processingBuffer.append((char) _byte);
                if (!isMethodParsed && processingBuffer.length() > HttpMethod.MAX_LENGTH) {
                    throw new HttpParserException(HttpStatusCode.NOT_IMPLEMENTED);
                }
            }
        }
    }
    private void parseHeaders() {
    }
    private void parseBody() {
    }


}
