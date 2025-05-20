package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeAll() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest_Valid() throws IOException {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(getValidRequest());
        } catch (HttpParserException e) {
            fail(e);
        }

        assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void parseHttpRequest_MethodNotImplemented() throws IOException {
        try {
            httpParser.parseHttpRequest(getInvalid_MethodNotImplemented());
            fail();
        } catch (HttpParserException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequest_MethodTooLong() throws IOException {
        try {
            httpParser.parseHttpRequest(getInvalid_MethodTooLong());
            fail();
        } catch (HttpParserException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequest_TooManyItemsInRequestLine() throws IOException {
        try {
            httpParser.parseHttpRequest(getInvalid_TooManyRequestLineItems());
            fail();
        } catch (HttpParserException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequest_EmptyRequestLine() throws IOException {
        try {
            httpParser.parseHttpRequest(getInvalid_EmptyRequestLine());
            fail();
        } catch (HttpParserException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequest_CRnoLF() throws IOException {
        try {
            httpParser.parseHttpRequest(getInvalid_NoLFAfterCR());
            fail();
        } catch (HttpParserException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    private InputStream getValidRequest() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:138.0) Gecko/20100101 Firefox/138.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n";

         return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream getInvalid_MethodNotImplemented() {
        String rawData = "GoT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:138.0) Gecko/20100101 Firefox/138.0\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream getInvalid_MethodTooLong() {
        String rawData = "GETGETGETGOTGOTGOTGOT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:138.0) Gecko/20100101 Firefox/138.0\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream getInvalid_TooManyRequestLineItems() {
        String rawData = "GET / AAAAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:138.0) Gecko/20100101 Firefox/138.0\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream getInvalid_EmptyRequestLine() {
        String rawData = "\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:138.0) Gecko/20100101 Firefox/138.0\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream getInvalid_NoLFAfterCR() {
        String rawData = "GET / HTTP/1.1\r" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:138.0) Gecko/20100101 Firefox/138.0\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }
}