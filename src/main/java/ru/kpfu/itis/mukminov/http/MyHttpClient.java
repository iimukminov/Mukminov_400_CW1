package ru.kpfu.itis.mukminov.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MyHttpClient implements HttpClient{
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        if (params != null) {
            StringBuilder urlParams = new StringBuilder();

            for (String key: params.keySet()) {

                if (!urlParams.isEmpty()) {
                    urlParams.append("&");
                }
                try {
                    urlParams.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            url = url + "?" + urlParams.toString();
        }

        String resultString;
        try {
            URL getURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("GET");
            for (String key: headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            resultString = readResponse(connection);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return resultString;
    }

    public String post(String url, Map<String, String> headers, Map<String, String> data) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL postURL = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) postURL.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);

            for (String key: headers.keySet()) {
                postConnection.setRequestProperty(key, headers.get(key));
            }

            String jsonInput = objectMapper.writeValueAsString(data);

            try (OutputStream outputStream = postConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            String resultString = readResponse(postConnection);
            postConnection.disconnect();
            return resultString;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL putURL = new URL(url);
            HttpURLConnection putConnection = (HttpURLConnection) putURL.openConnection();
            putConnection.setRequestMethod("PUT");
            putConnection.setDoOutput(true);

            for (String key: headers.keySet()) {
                putConnection.setRequestProperty(key, headers.get(key));
            }

            String jsonInput = objectMapper.writeValueAsString(data);

            try (OutputStream outputStream = putConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            String resultString = readResponse(putConnection);
            putConnection.disconnect();
            return resultString;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL deleteURL = new URL(url);
            HttpURLConnection deleteConnection = (HttpURLConnection) deleteURL.openConnection();
            deleteConnection.setRequestMethod("DELETE");
            deleteConnection.setDoOutput(true);

            for (String key: headers.keySet()) {
                deleteConnection.setRequestProperty(key, headers.get(key));
            }

            String jsonInput = objectMapper.writeValueAsString(data);

            try (OutputStream outputStream = deleteConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            String resultString = readResponse(deleteConnection);
            deleteConnection.disconnect();
            return resultString;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readResponse(HttpURLConnection connection) {
        if (connection == null) {
            return null;
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                return content.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
