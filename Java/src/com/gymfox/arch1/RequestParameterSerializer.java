package com.gymfox.arch1;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import static com.gymfox.arch1.HtmlTags.*;

final class RequestParameterSerializer {
    private RequestParameterSerializer() {}

    public static ArrayList<String> getRequestParameters(HttpServletRequest request) {
        return Collections.list(request.getParameterNames());
    }

    static String getRequestLines(HttpServletRequest request, String parameters) {
        return String.format(BODY.getTags(), parameters, request.getParameter(parameters));
    }
}
