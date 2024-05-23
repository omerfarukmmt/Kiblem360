package com.example.girisekrani;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.io.IOException;

public class Vakitler extends AppCompatActivity {

    private TextView Imsak;
    private TextView Gunes;
    private TextView Ogle;
    private TextView Ikindi;
    private TextView Aksam;
    private TextView Yatsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakitler);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);

        if (email == null) {
            finish();
            return;
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        String city = dataBaseHelper.getCityFromDatabase(email);
        String country = "Turkey";

        Imsak = findViewById(R.id.imsak_vakti);
        Gunes = findViewById(R.id.gunes_vakti);
        Ogle = findViewById(R.id.ogle_vakti);
        Ikindi = findViewById(R.id.ikindi_vakti);
        Aksam = findViewById(R.id.aksam_vakti);
        Yatsi = findViewById(R.id.yatsi_vakti);

        new Thread(() -> {
            try {
                JsonObject prayerTimes = NamazVakitleriniCek.getPrayerTimes(city, country);
                runOnUiThread(() -> {
                    Imsak.setText(prayerTimes.get("Fajr").getAsString());
                    Gunes.setText(prayerTimes.get("Sunrise").getAsString());
                    Ogle.setText(prayerTimes.get("Dhuhr").getAsString());
                    Ikindi.setText(prayerTimes.get("Asr").getAsString());
                    Aksam.setText(prayerTimes.get("Maghrib").getAsString());
                    Yatsi.setText(prayerTimes.get("Isha").getAsString());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
