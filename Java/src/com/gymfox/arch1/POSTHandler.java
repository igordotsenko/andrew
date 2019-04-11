package com.gymfox.arch1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.arch1.HtmlTags.HEADER;
import static com.gymfox.arch1.HtmlTags.TABLE;
import static com.gymfox.arch1.RequestParameterSerializer.getRequestLines;

public class POSTHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("form.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            printWriter.println(TABLE.getTags());
            printWriter.println(HEADER.getTags());

            RequestParameterSerializer.getRequestParameters(request)
                    .forEach(parameters -> printWriter.println(getRequestLines(request, parameters)));
        }
    }
}
