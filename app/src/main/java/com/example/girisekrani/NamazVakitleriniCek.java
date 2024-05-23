package com.example.girisekrani;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
public class NamazVakitleriniCek {
    private static final String BASE_URL = "https://api.aladhan.com/v1/timingsByCity";

    public static JsonObject getPrayerTimes(String city, String country) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + "?city=" + city + "&country=" + country + "&method=13";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.body().charStream(), JsonObject.class);
            return jsonResponse.getAsJsonObject("data").getAsJsonObject("timings");
        }
    }
}
