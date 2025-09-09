package ru.kpfu.itis.mukminov.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class MyHttpClient implements HttpClient{
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        if (params != null) {
            StringBuilder urlParams = new StringBuilder(url + "?");

            for (String key: params.keySet()) {
                try {
                    urlParams.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            url = urlParams.toString().substring(0, urlParams.length() - 1);
        }

        String resultString;
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
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
        return null;
    }

    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        return null;
    }

    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        return null;
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
