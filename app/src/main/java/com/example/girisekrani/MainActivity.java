package com.example.girisekrani;



import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView hesapOlustur;
    EditText emailGrs, sifreGrs;
    Button btnGrs;
    CheckBox sifreGoster;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailGrs=findViewById(R.id.emailLogin);
        sifreGrs = findViewById(R.id.passwordLogin);
        btnGrs=findViewById(R.id.loginBtn);
        sifreGoster=findViewById(R.id.showPasswordLogin);

        hesapOlustur = findViewById(R.id.hspOlstr);
        hesapOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(MainActivity.this,HesapOlustur.class);
                startActivity(i);
            }


        });
        btnGrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailGrs.getText().toString();
                String password = sifreGrs.getText().toString();

                DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);

                boolean loginSuccessful = dbHelper.checkLogin(email, password);

                if (loginSuccessful) {

                    Toast.makeText(MainActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AnaSayfa.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(MainActivity.this, "Hatalı e-posta veya şifre!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sifreGoster.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked) {
                sifreGrs.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            }
            else{
                sifreGrs.setInputType((InputType.TYPE_TEXT_VARIATION_PASSWORD));

            }
        });



    }
}