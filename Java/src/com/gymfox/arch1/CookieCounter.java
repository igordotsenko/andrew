package com.gymfox.arch1;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.arch1.HtmlTags.REFRESH;

public class CookieCounter extends HttpServlet {
    private final String COUNT_ATTRIBUTE = "count";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType("text/html");

            printWriter.println(getCookieCount(request));

            printWriter.println(REFRESH.getTags());
        }
    }

    private Integer getCookieCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer count = (Integer) session.getAttribute("count");

        if ( count == null ) {
            count = 1;
            session.setAttribute(COUNT_ATTRIBUTE, count);
        }
        session.setAttribute(COUNT_ATTRIBUTE, count + 1);

        return count;
    }
}
