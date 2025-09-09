package ru.kpfu.itis.mukminov.http;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        String url1 = "https://jsonplaceholder.typicode.com/posts";

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("id", "1");

        Map<String, String> data = new HashMap<>();
        data.put("title", "Новый пост");
        data.put("body", "Текст содержимого поста");
        data.put("userId", "1");

        MyHttpClient myHttpClient = new MyHttpClient();

        System.out.println(myHttpClient.get(url1, headers, params));
    }


}
