package ru.kpfu.itis.mukminov.http;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        String url1 = "https://jsonplaceholder.typicode.com/posts";
        String url2 = "https://gorest.co.in/public/v2/users";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer 8a002ab41b4a0eab4ed5fadc9934dc45ea05f6c3fa0fcd8885cb758a03f81ee8");

        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("id", "1");

        Map<String, String> data = new HashMap<>();
        data.put("id", "9274653");
        data.put("name", "Ilya");
        data.put("email", "ilya.m6@example.com");
        data.put("gender", "male");
        data.put("status", "active");

        MyHttpClient myHttpClient = new MyHttpClient();

        System.out.println(myHttpClient.get(url1, headers, params));
        System.out.println(myHttpClient.post(url2, headers, data));
    }


}
