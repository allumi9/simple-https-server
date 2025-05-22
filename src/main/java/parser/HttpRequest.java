package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HttpRequest extends HttpMessage {
    private HttpMethod method;
    private String requestTarget;
    private String stringHttpVersion;
    private HttpVersion bestCompatibleHttpVersion;

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    public void setStringHttpVersion(String stringHttpVersion) throws HttpParserException{
        this.stringHttpVersion = stringHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(stringHttpVersion);
        if (this.bestCompatibleHttpVersion == null) {
            throw new HttpParserException(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED);
        }
    }

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

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) throws HttpParserException {
        if (requestTarget == null || requestTarget.isEmpty()) {
            throw new HttpParserException(HttpStatusCode.BAD_REQUEST);
        }
        this.requestTarget = requestTarget;
    }
}
