package com.AIGame.mobdetectionmod;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private static final String SERVER_URL = "http://localhost:8000/detect";

    public void sendImage(byte[] imageBytes) {
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "image/png");

            try (OutputStream os = connection.getOutputStream()) {
                os.write(imageBytes);
            }

            InputStream responseStream = connection.getInputStream();
            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = responseStream.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, length);
            }

            String response = responseBuffer.toString("UTF-8");
            parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseResponse(String response) {
        MobDetectionMod.handleDetectionResponse(response);
    }

}
