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
        url = createUrlWithParams(url, params);

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
        return doFunction(url, headers, data, "POST");
    }

    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        return doFunction(url, headers, data, "PUT");
    }

    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        return doFunction(url, headers, data, "DELETE");
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

    private static String doFunction(String url, Map<String, String> headers, Map<String, String> data, String function) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL functionURL = new URL(url);
            HttpURLConnection functionConnection = (HttpURLConnection) functionURL.openConnection();
            functionConnection.setRequestMethod(function);
            functionConnection.setDoOutput(true);

            for (String key: headers.keySet()) {
                functionConnection.setRequestProperty(key, headers.get(key));
            }

            String jsonInput = objectMapper.writeValueAsString(data);

            try (OutputStream outputStream = functionConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            String resultString = readResponse(functionConnection);
            functionConnection.disconnect();
            return resultString;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createUrlWithParams(String url, Map<String, String> params) {
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
            url = url + "?" + urlParams;
        }

        return url;
    }
}
