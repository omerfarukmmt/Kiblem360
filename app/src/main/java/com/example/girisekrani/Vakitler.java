package com.example.girisekrani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.girisekrani.databinding.ActivityVakitlerBinding;
import com.google.gson.JsonObject;
import android.os.CountDownTimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.io.IOException;

public class Vakitler extends AppCompatActivity {

    private TextView Imsak;
    private TextView Gunes;
    private TextView Ogle;
    private TextView Ikindi;
    private TextView Aksam;
    private TextView Yatsi;
    private TextView countdownTextView;

    ActivityVakitlerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakitler);
        binding = ActivityVakitlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navBar.setSelectedItemId(R.id.namazVakit);

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
        countdownTextView = findViewById(R.id.countdownTextView);

        new Thread(() -> {
            try {
                JsonObject prayerTimes = NamazVakitleriniCek.getPrayerTimes(city, country);
                runOnUiThread(() -> {
                    try {
                    Imsak.setText(prayerTimes.get("Fajr").getAsString());
                    Gunes.setText(prayerTimes.get("Sunrise").getAsString());
                    Ogle.setText(prayerTimes.get("Dhuhr").getAsString());
                    Ikindi.setText(prayerTimes.get("Asr").getAsString());
                    Aksam.setText(prayerTimes.get("Maghrib").getAsString());
                    Yatsi.setText(prayerTimes.get("Isha").getAsString());

                    // Bir sonraki namaz vaktine kalan süreyi hesapla ve göster

                        calculateNextPrayerTime(prayerTimes);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        binding.navBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.anasayfa) {
                Log.d("NavBar", "Anasayfa selected");
                Intent i = new Intent(Vakitler.this, AnaSayfa.class);
                startActivity(i);
            } else if (itemId == R.id.kible) {
                Log.d("NavBar", "Kible selected");
                Intent i = new Intent(Vakitler.this, Kible.class);
                startActivity(i);
            } else if (itemId == R.id.namazVakit) {
                Log.d("NavBar", "Namaz Vakit selected");
                Intent i = new Intent(Vakitler.this, Vakitler.class);
                startActivity(i);
            } else if (itemId == R.id.ayarlar) {
                Log.d("NavBar", "Ayarlar selected");

                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                Intent i = new Intent(Vakitler.this, Ayarlar.class);
                i.putExtra("USER_EMAIL", email);
                startActivity(i);
            } else {
                Log.d("NavBar", "Unknown item selected");
                return false;
            }
            return true;
        });
    }

    private void calculateNextPrayerTime(JsonObject prayerTimes) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date now = Calendar.getInstance().getTime();
        String[] prayerNames = {"Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"};
        Date nextPrayerTime = null;

        for (String prayerName : prayerNames) {
            Date prayerTime = sdf.parse(prayerTimes.get(prayerName).getAsString());
            if (prayerTime != null && prayerTime.after(now)) {
                nextPrayerTime = prayerTime;
                break;
            }
        }

        if (nextPrayerTime == null) {
            nextPrayerTime = sdf.parse(prayerTimes.get("Fajr").getAsString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextPrayerTime);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            nextPrayerTime = calendar.getTime();
        }

        long timeDifference = nextPrayerTime.getTime() - now.getTime();
        startCountdown(timeDifference);
    }

    private void startCountdown(long millisUntilFinished) {
        new CountDownTimer(millisUntilFinished, 1000) {
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / (1000 * 60 * 60)) % 24;
                long minutes = (millisUntilFinished / (1000 * 60)) % 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                countdownTextView.setText("Bir Sonraki Namaza Kalan: " + time);
            }

            public void onFinish() {
                countdownTextView.setText("Bir Sonraki Namaza Kalan: 00:00:00");
            }
        }.start();
    }
}

