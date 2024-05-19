package com.example.girisekrani;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HesapOlustur extends AppCompatActivity {
    TextView deneme;
    EditText isim,soyisim,mail,sifre;
    RadioButton erkek,kiz;
    RadioGroup cinsiyet;
    CheckBox sifreGoster;
    String secilenCinsiyet;
    Button kayitOl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hesap_olustur);

        kayitOl =findViewById(R.id.register);
        deneme = findViewById(R.id.deneme);
        isim = findViewById(R.id.name);
        soyisim = findViewById(R.id.surname);
        mail = findViewById(R.id.email);
        sifre = findViewById(R.id.password);
        cinsiyet =findViewById(R.id.gender);
        sifreGoster=findViewById(R.id.showPasswordRegister);


        sifreGoster.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked) {
                sifre.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                deneme.setText("Tiksiz");
            }
            else{
                sifre.setInputType((InputType.TYPE_TEXT_VARIATION_PASSWORD));
                deneme.setText("Tikli");
            }
        });

        cinsiyet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                secilenCinsiyet = radioButton.getText().toString();
            }
        });


        kayitOl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                UyeBilgileri uyeBilgileri = new UyeBilgileri(isim.getText().toString(),soyisim.getText().toString(),mail.getText().toString(),sifre.getText().toString(),secilenCinsiyet);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(HesapOlustur.this);

                boolean basari = dataBaseHelper.addOne(uyeBilgileri);

                Toast.makeText(HesapOlustur.this,"Success= "+basari , Toast.LENGTH_SHORT).show();
                Intent i =  new Intent(HesapOlustur.this,MainActivity.class);
                startActivity(i);

            }
        });


    }

}