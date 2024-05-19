package com.example.girisekrani;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.girisekrani.databinding.ActivityAnaSayfaBinding;

public class AnaSayfa extends AppCompatActivity {
    ActivityAnaSayfaBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnaSayfaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new AnasayfaFragment());

        binding.navBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.anasayfa) {
                Log.d("NavBar", "Anasayfa selected");
                replaceFragment(new AnasayfaFragment());
            } else if (itemId == R.id.kible) {
                Log.d("NavBar", "Kible selected");
                replaceFragment(new KibleFragment());
            } else if (itemId == R.id.namazVakit) {
                Log.d("NavBar", "Namaz Vakit selected");
                replaceFragment(new VakitFragment());
            } else if (itemId == R.id.ayarlar) {
                Log.d("NavBar", "Ayarlar selected");
                replaceFragment(new SettingsFragment());
            } else {
                Log.d("NavBar", "Unknown item selected");
                return false;
            }

        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}