package ru.kpfu.itis.mukminov.http;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        String urlGet = "https://jsonplaceholder.typicode.com/posts";
        String urlPost = "https://gorest.co.in/public/v2/users";
        String urlPut = "https://gorest.co.in/public/v2/users/8111029";
        String urlDelete = "https://gorest.co.in/public/v2/users/8111029";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer 8a002ab41b4a0eab4ed5fadc9934dc45ea05f6c3fa0fcd8885cb758a03f81ee8");

        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("id", "1");

        Map<String, String> dataPost = new HashMap<>();
        dataPost.put("name", "Ilya");
        dataPost.put("email", "ilyha.06@example.com");
        dataPost.put("gender", "male");
        dataPost.put("status", "active");

        Map<String, String> dataPut = new HashMap<>();
        dataPut.put("name", "neIlya");
        dataPut.put("email", "neIlya.06@example.com");
        dataPut.put("gender", "male");
        dataPut.put("status", "active");

        Map<String, String> dataDelete = new HashMap<>();
        dataDelete.put("email", "neIlya.06@example.com");
        dataDelete.put("gender", "male");
        dataDelete.put("status", "active");

        MyHttpClient myHttpClient = new MyHttpClient();

        System.out.println(myHttpClient.get(urlGet, headers, params));
        System.out.println(myHttpClient.post(urlPost, headers, dataPost));
        System.out.println(myHttpClient.put(urlPut, headers, dataPut));
        System.out.println(myHttpClient.delete(urlDelete, headers, dataDelete));
    }


}
