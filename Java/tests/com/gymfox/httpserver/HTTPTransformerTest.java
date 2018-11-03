package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class HTTPTransformerTest {
    @Test
    public void checkRequestParametersTest() throws HTTPServerExceptions.InvalidRequestParametersCountException {
        Assert.assertEquals("[get, /, http/1.1]",
                Arrays.toString(new HTTPTransformer().checkRequestParameters(new String[]{"get", "/", "http/1.1"})));
        Assert.assertEquals("[get, /index.html, http/1.1]",
                Arrays.toString(new HTTPTransformer().checkRequestParameters(new String[]{"get", "/index.html", "http/1.1"})));
        Assert.assertEquals("[get, /test/, http/1.1]",
                Arrays.toString(new HTTPTransformer().checkRequestParameters(new String[]{"get", "/test/", "http/1.1"})));
    }

    @Test(expected = HTTPServerExceptions.InvalidRequestParametersCountException.class)
    public void invalidParametersCountTest() throws HTTPServerExceptions.InvalidRequestParametersCountException {
        new HTTPTransformer().checkRequestParameters(new String[]{"get", "/", "test", "http/1.1"});
    }
}
