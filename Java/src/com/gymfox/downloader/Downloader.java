package com.gymfox.downloader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    private String url;
    private ExecutorService pool = Executors.newFixedThreadPool(1);

    public Downloader(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void downloadImage() {
        pool.execute(() -> {
            try {
                URL urlImage = new URL(getUrl());
                Path fileName = Paths.get(getUrl()).getFileName();
                HttpURLConnection myUrlConnection = (HttpURLConnection) urlImage.openConnection();
                InputStream inputStream = urlImage.openStream();
                OutputStream outputStream = new FileOutputStream(new File(String.valueOf(fileName)));
                byte[] buffer = new byte[myUrlConnection.getContentLength()];

                writeToFile(buffer, inputStream, outputStream);

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeExecutors();
            }
        });
    }

    private void writeToFile(byte[] buffer, InputStream inputStream, OutputStream outputStream) throws IOException {
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    private void closeExecutors() {
        pool.shutdown();
    }

    @Override
    public String toString() {
        return url;
    }
}

class StartDownloader {
    public static void main(String[] args) {
        String url = "https://upload.wikimedia.org/wikipedia/commons/9/9c/Image-Porkeri_001.jpg";
        System.out.print("Enter image URL: " + url + "\n");
//        Scanner in = new Scanner(System.in);
//        in.nextLine() = url;

        Downloader downloader = new Downloader(url);
        downloader.downloadImage();
    }
}
