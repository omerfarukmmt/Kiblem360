package com.example.girisekrani;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Ayarlar extends AppCompatActivity {

    private EditText searchQuery;
    private Button searchButton;
    private ListView resultsListView;
    private RatingBar ratingBar;
    private DataBaseHelper dataBaseHelper;
    private UyeBilgileriAdapter uyeBilgileriAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        searchQuery = findViewById(R.id.searchQuery);
        searchButton = findViewById(R.id.searchButton);
        resultsListView = findViewById(R.id.resultsListView);
        ratingBar = findViewById(R.id.puanla);

        dataBaseHelper = new DataBaseHelper(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchQuery.getText().toString();
                if (!query.isEmpty()) {
                    ArrayList<UyeBilgileri> results = dataBaseHelper.searchMembers(query);
                    if (results.size() > 0) {
                        uyeBilgileriAdapter = new UyeBilgileriAdapter(Ayarlar.this, results);
                        resultsListView.setAdapter(uyeBilgileriAdapter);
                    } else {
                        Toast.makeText(Ayarlar.this, "No results found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Ayarlar.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    showThankYouDialog();
                }
            }
        });
    }

    private void showThankYouDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Ayarlar.this);
        builder.setMessage("Teşekkürler :)")
                .setPositiveButton("Tamam", null)
                .create()
                .show();
    }
}
