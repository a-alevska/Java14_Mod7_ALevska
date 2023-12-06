package org.example.httpUtils;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.example.httpUtils.Utils.*;

public class HttpStatusChecker {

    public String getStatusImage(int code) throws Exception {
        String stringUrl = START_URL + code + EXTENSION;
        HttpURLConnection connection = (HttpURLConnection) new URL(stringUrl).openConnection();
        int responseCode = connection.getResponseCode();
        if(responseCode == 404) {
            throw new FileNotFoundException(String.format(FILE_NOT_FOUND_EXCEPTION_TEXT, code));
        } else {
            return stringUrl;
        }
    }
}