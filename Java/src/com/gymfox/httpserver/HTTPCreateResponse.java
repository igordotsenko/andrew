package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import static com.gymfox.httpserver.HTTPCreateRequest.getHttpVersion;

final class HTTPCreateResponse {
    private static volatile String response;
    //    public static final int FIRST_RESPONSE_LINE = 0;

    private HTTPCreateResponse() {}

    static void createResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf8"));
        StringBuilder answer = new StringBuilder();
        String line;

        answer.append("Response:\n\t");
//        answer.append(connection.getHeaderField(FIRST_RESPONSE_LINE)).append("\n");
        answer.append(getHttpVersion()).append(" ").append(connection.getResponseCode()).append(" ")
                .append(connection.getResponseMessage()).append("\r\n\t");
        answer.append("Connection: close").append("\r\n\t");
        answer.append("Content-length: ").append(connection.getHeaderField("content-length")).append("\r\n\t");
        answer.append("Content-type: ").append(connection.getHeaderField("content-type")).append("\r\n\t");
        answer.append("Date: ").append(connection.getHeaderField("date")).append("\r\n\t\n");

        while ((line = reader.readLine()) != null) {
            answer.append(line).append("\n");
        }
        reader.close();

        response = answer.toString();
    }

    static String getResponse() {
        return response;
    }
}
