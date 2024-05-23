package com.example.girisekrani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.girisekrani.databinding.ActivityAnaSayfaBinding;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AnaSayfa extends AppCompatActivity {
    ActivityAnaSayfaBinding binding;
    WebView webViewAnaSayfa;
    private TextView countdownTextView;
    private Handler handler;
    private Runnable runnable;

    TextView zikirmatikTextView;
    Button zikirmatikButton,sifirla;
    int sayac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAnaSayfaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webViewAnaSayfa = findViewById(R.id.webViewAnaSayfa);
        webViewAnaSayfa.setWebViewClient(new WebViewClient());
        webViewAnaSayfa.loadUrl("https://www.kuranvemeali.com/kuran-oku/sure-fatiha/cuz-1/sayfa-0/meal-omer-celik");
        WebSettings webSettings = webViewAnaSayfa.getSettings();
        webSettings.setJavaScriptEnabled(true);

        zikirmatikTextView =findViewById(R.id.ZikirmatikTextView);
        zikirmatikButton =findViewById(R.id.zikirMatikBtn);
        sifirla =findViewById(R.id.sifirla);
        zikirmatikButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = zikirmatikTextView.getText().toString();
                int sayac = Integer.parseInt(currentText);
                sayac++;
                zikirmatikTextView.setText(String.valueOf(sayac));
            }
        });
        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = zikirmatikTextView.getText().toString();
                int sayac = Integer.parseInt(currentText);
                sayac=0;
                zikirmatikTextView.setText(String.valueOf(sayac));
            }
        });



        binding.navBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.anasayfa) {
                Log.d("NavBar", "Anasayfa selected");
                Intent i = new Intent(AnaSayfa.this, AnaSayfa.class);
                startActivity(i);
            } else if (itemId == R.id.kible) {
                Log.d("NavBar", "Kible selected");
                Intent i = new Intent(AnaSayfa.this, Kible.class);
                startActivity(i);
            } else if (itemId == R.id.namazVakit) {
                Log.d("NavBar", "Namaz Vakit selected");
                Intent i = new Intent(AnaSayfa.this, Vakitler.class);
                startActivity(i);
            } else if (itemId == R.id.ayarlar) {
                Log.d("NavBar", "Ayarlar selected");

                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String email = sharedPref.getString("email", null);

                Intent i = new Intent(AnaSayfa.this, Ayarlar.class);
                i.putExtra("USER_EMAIL", email);  // Email adresini intent ile Ayarlar activity'sine gönderiyoruz
                startActivity(i);
            } else {
                Log.d("NavBar", "Unknown item selected");
                return false;
            }
            return true;
        });

        countdownTextView = findViewById(R.id.countdownTextView);

        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", null);

        if (email != null) {
            Log.d("AnaSayfa", "Kullanıcı e-posta: " + email);
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            String city = dataBaseHelper.getCityFromDatabase(email);
            String country = "Turkey"; //

            new Thread(() -> {
                try {
                    JsonObject prayerTimes = NamazVakitleriniCek.getPrayerTimes(city, country);
                    runOnUiThread(() -> {
                        startCountdown(prayerTimes);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Log.e("AnaSayfa", "Kullanıcı e-posta bilgisi bulunamadı.");
        }
    }

    private void startCountdown(JsonObject prayerTimes) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String nextPrayerTime = getNextPrayerTime(prayerTimes);
                    if (nextPrayerTime != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        Date nextPrayerDate = sdf.parse(nextPrayerTime);
                        Date currentDate = new Date();
                        long diff = nextPrayerDate.getTime() - currentDate.getTime();


                        if (diff < 0) {
                            diff += 24 * 60 * 60 * 1000;
                        }

                        long hours = diff / (1000 * 60 * 60);
                        long minutes = (diff / (1000 * 60)) % 60;
                        long seconds = (diff / 1000) % 60;
                        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                        countdownTextView.setText(timeLeft);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    private String getNextPrayerTime(JsonObject prayerTimes) {
        String[] prayerNames = {"Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"};
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentDate = new Date();
        try {
            for (String prayerName : prayerNames) {
                Date prayerDate = sdf.parse(prayerTimes.get(prayerName).getAsString());
                if (prayerDate.after(currentDate)) {
                    return prayerTimes.get(prayerName).getAsString();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
