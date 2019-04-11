package com.gymfox.arch1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.arch1.HtmlTags.*;
import static com.gymfox.arch1.RequestParameterSerializer.getRequestLines;

public class GETHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType("text/html");
            printWriter.println(TABLE.getTags());
            printWriter.println(HEADER.getTags());

            RequestParameterSerializer.getRequestParameters(request)
                    .forEach(parameters -> printWriter.println(getRequestLines(request, parameters)));

            printWriter.println(FORM.getTags());
        }
    }
}
