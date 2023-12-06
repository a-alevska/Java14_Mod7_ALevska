package org.example.httpUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.example.httpUtils.Utils.*;

public class HttpStatusImageDownloader {
    private final HttpStatusChecker checker = new HttpStatusChecker();
    private final OkHttpClient client = new OkHttpClient();

    public void downloadStatusImage(int code) throws HttpImageStatusException {

        String imageUrl;

        try {
            imageUrl = checker.getStatusImage(code);
        } catch (Exception e) {
            throw new HttpImageStatusException(FILE_NOT_FOUND_EXCEPTION_TEXT);
        }

        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new HttpImageStatusException(ERROR_DOWNLOADING + "HTTP status code: ");
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new HttpImageStatusException(ERROR_DOWNLOADING + "Response body is null.");
            }

            String fileName = "cat " + code + ".jpg";

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(body.bytes());
                System.out.println("Image downloaded and saved as " + fileName);
            } catch (IOException e) {
                throw new HttpImageStatusException(ERROR_SAVING + e.getMessage());
            }
        } catch (IOException e) {
            throw new HttpImageStatusException(ERROR_DOWNLOADING + e.getMessage());
        }
    }
}

