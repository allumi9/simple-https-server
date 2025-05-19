package parser;

public enum HttpStatusCode {
    BAD_REQUEST(400, "Bad Request"),
    METHOD_NOT_ALLOWED(401, "Method not allowed"),
    URI_TOO_LONG(414, "URI too long"),

    INTERNAL_SERVER_ERROR(500, "Internal Server error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
