package com.gymfox.arch1;

public enum HtmlTags {
    TABLE("<table width = \"100%\" border = \"1\">"),
    HEADER("<tr>\n" +
            "<th>Key</th>\n" +
            "<th>Value</th>\n" +
            "</tr>"),
    BODY("<tr align = \"center\">\n" +
            "<td>%s</td>\n" +
            "<td>%s</td>\n" +
            "</tr>"),
    FORM("<form>" +
            "<p><input placeholder=\"first_name\" name=\"first_name\">\n" +
            "<p><input placeholder=\"last_name\" name=\"last_name\">\n" +
            "<p><input placeholder=\"profession\" name=\"profession\">\n" +
            "<p><input type=\"submit\" value=\"Submit\"></p>\n" +
            "</form>"),
    REFRESH("<form>\n" +
            "<input type=\"button\" onClick=\"history.go(0)\" value=\"Update\">\n" +
            "</form>");

    private String tags;

    HtmlTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }
}
