package parser;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public enum HttpMethod {
    GET, HEAD, POST, PUT, DELETE;
    public static final int MAX_LENGTH;

    static {
        AtomicInteger tempMaxLength = new AtomicInteger(-1);
        Arrays.stream(HttpMethod.values()).forEach(a -> {
            if (a.name().length() > tempMaxLength.get()) {
                tempMaxLength.set(a.name().length());
            }
        });
        MAX_LENGTH = tempMaxLength.intValue();
    }
}
