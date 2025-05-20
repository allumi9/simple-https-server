package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HttpRequest extends HttpMessage {
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    public static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String method) throws HttpParserException {
        if (Arrays.stream(HttpMethod.values()).noneMatch(a -> a.name().equals(method))) {
            LOGGER.error("Method not implemented exception thrown for method: {}", method);
            throw new HttpParserException(HttpStatusCode.NOT_IMPLEMENTED);
        }
        this.method = HttpMethod.valueOf(method);
    }
}
