package org.example.httpUtils;

import java.util.Scanner;

public class HttpImageStatusCli {
    private final HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();
    private final Scanner scanner = new Scanner(System.in);

    public void askStatus() {
        try {
            System.out.print("Enter HTTP status code: ");
            String input = scanner.nextLine();

            int code;
            try {
                code = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                return;
            }

            try {
                downloader.downloadStatusImage(code);
            } catch (HttpImageStatusException e) {
                System.out.println("There is no image for HTTP status " + code);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } finally {
            scanner.close();
        }
    }

}
