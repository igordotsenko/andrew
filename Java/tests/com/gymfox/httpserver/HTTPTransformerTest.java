package com.gymfox.httpserver;

import com.gymfox.httpserver.HTTPServerExceptions.MalformedRequestException;
import org.junit.Test;

import static com.gymfox.httpserver.HTTPTransformer.validateLength;

public class HTTPTransformerTest {
    @Test
    public void validateRequestParametersTest() throws MalformedRequestException {
        validateLength(new String[]{"get", "/index.html", "http/1.1"});
    }

    @Test ( expected = MalformedRequestException.class )
    public void tooMuchRequestParametersTest() throws MalformedRequestException {
        validateLength(new String[]{"get", "/index.html", "http/1.1", "localhost", "Don't", "Stop", "Me", "Now"});
    }

    @Test ( expected = MalformedRequestException.class )
    public void notEnoughRequestParametersTest() throws MalformedRequestException {
        validateLength(new String[]{"get"});
    }
}
