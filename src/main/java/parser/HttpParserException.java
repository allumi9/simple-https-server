package parser;

public class HttpParserException extends Exception {
    private HttpStatusCode errorCode;

    public HttpParserException(HttpStatusCode errorCode) {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }
}
