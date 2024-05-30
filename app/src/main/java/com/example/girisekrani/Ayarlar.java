package com.example.girisekrani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.girisekrani.databinding.ActivityAnaSayfaBinding;
import com.example.girisekrani.databinding.ActivityAyarlarBinding;

import java.util.ArrayList;

public class Ayarlar extends AppCompatActivity {

    private EditText searchQuery, editName, editSurname, editEmail, editCity, editPassword;
    private Button searchButton, updateButton;
    private ListView resultsListView;
    private RatingBar ratingBar;
    private DataBaseHelper dataBaseHelper;
    private UyeBilgileriAdapter uyeBilgileriAdapter;
    private UyeBilgileri currentUser;

    ActivityAyarlarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        binding = ActivityAyarlarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navBar.setSelectedItemId(R.id.ayarlar);

        searchQuery = findViewById(R.id.searchQuery);
        searchButton = findViewById(R.id.searchButton);
        resultsListView = findViewById(R.id.resultsListView);
        ratingBar = findViewById(R.id.puanla);
        editName = findViewById(R.id.editName);
        editSurname = findViewById(R.id.editSurname);
        editEmail = findViewById(R.id.editEmail);
        editCity = findViewById(R.id.editCity);
        editPassword = findViewById(R.id.editPassword);
        updateButton = findViewById(R.id.updateButton);

        dataBaseHelper = new DataBaseHelper(this);

        // Mevcut kullanıcının bilgilerini çekin (bu örnekte email ile)
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("USER_EMAIL");
        if (userEmail != null) {
            currentUser = dataBaseHelper.getUserByEmail(userEmail);

            if (currentUser != null) {
                editName.setText(currentUser.getIsim());
                editSurname.setText(currentUser.getSoyisim());
                editEmail.setText(currentUser.getEmail());
                editCity.setText(currentUser.getSehir());
                editPassword.setText(currentUser.getSifre());
            } else {
                Toast.makeText(this, "Kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
                finish(); // Eğer kullanıcı bulunamazsa activity'i kapatabiliriz
            }
        } else {
            Toast.makeText(this, "Email adresi bulunamadı", Toast.LENGTH_SHORT).show();
            finish(); // Eğer email null ise activity'i kapatabiliriz
        }

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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });

        binding.navBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.anasayfa) {
                Log.d("NavBar", "Anasayfa selected");
                Intent i = new Intent(Ayarlar.this, AnaSayfa.class);
                startActivity(i);
            } else if (itemId == R.id.kible) {
                Log.d("NavBar", "Kible selected");
                Intent i = new Intent(Ayarlar.this, Kible.class);
                startActivity(i);
            } else if (itemId == R.id.namazVakit) {
                Log.d("NavBar", "Namaz Vakit selected");
                Intent i = new Intent(Ayarlar.this, Vakitler.class);
                startActivity(i);
            } else if (itemId == R.id.ayarlar) {
                Log.d("NavBar", "Ayarlar selected");

                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String email = sharedPref.getString("email", null);

                Intent i = new Intent(Ayarlar.this, Ayarlar.class);
                i.putExtra("USER_EMAIL", email);  // Email adresini intent ile Ayarlar activity'sine gönderiyoruz
                startActivity(i);
            } else {
                Log.d("NavBar", "Unknown item selected");
                return false;
            }
            return true;
        });
    }

    private void showThankYouDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Ayarlar.this);
        builder.setMessage("Teşekkürler :)")
                .setPositiveButton("Tamam", null)
                .create()
                .show();
    }

    private void updateUserInfo() {
        String name = editName.getText().toString();
        String surname = editSurname.getText().toString();
        String email = editEmail.getText().toString();
        String city = editCity.getText().toString();
        String password = editPassword.getText().toString();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || city.isEmpty() || password.isEmpty()) {
            Toast.makeText(Ayarlar.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setIsim(name);
        currentUser.setSoyisim(surname);
        currentUser.setEmail(email);
        currentUser.setSehir(city);
        currentUser.setSifre(password);

        boolean success = dataBaseHelper.updateUser(currentUser);
        if (success) {
            Toast.makeText(Ayarlar.this, "Bilgiler başarıyla güncellendi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Ayarlar.this, "Bilgiler güncellenirken hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }


}
