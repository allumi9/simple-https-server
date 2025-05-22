package parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HttpVersionTests {

    @Test
    public void getCompatibleVersion_ExactMatch_ShouldReturnCorrectly() throws HttpParserException {
        var actualVersion = HttpVersion.getBestCompatibleVersion("HTTP/1.1");

        assertEquals(HttpVersion.HTTP_1_1, actualVersion);
    }

    @Test
    public void getCompatibleVersion_BadFormat_ShouldThrow() {
        try {
            HttpVersion.getBestCompatibleVersion("HTP/1.1");
            fail();
        } catch (Exception e) {
            // If it didn't fail means it passed
        }
    }

    @Test
    public void getCompatibleVersion_HigherThanSupported_ShouldThrow() {
        try {
            var ver = HttpVersion.getBestCompatibleVersion("HTTP/1.3");
            assertEquals(HttpVersion.HTTP_1_1, ver);
        } catch (Exception e) {
            fail();
        }
    }
}
