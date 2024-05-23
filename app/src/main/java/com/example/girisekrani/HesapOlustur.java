package com.example.girisekrani;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HesapOlustur extends AppCompatActivity {
    EditText isim,soyisim,mail,sifre;
    RadioButton erkek,kiz;
    RadioGroup cinsiyet;
    CheckBox sifreGoster,kvkk_check;
    String secilenCinsiyet, secilenSehir;;
    Button kayitOl;
    Spinner sehirler;

    TextView girisYapYnldr,kvkk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hesap_olustur);
        sehirler = findViewById(R.id.sehirler);
        kayitOl =findViewById(R.id.register);
        isim = findViewById(R.id.name);
        soyisim = findViewById(R.id.surname);
        mail = findViewById(R.id.email);
        sifre = findViewById(R.id.password);
        cinsiyet =findViewById(R.id.gender);
        sifreGoster=findViewById(R.id.showPasswordRegister);
        girisYapYnldr = findViewById(R.id.girisYapYnldr);
        kvkk = findViewById(R.id.kvkk);
        kvkk_check =findViewById(R.id.kvkk_check);
        kayitOl.setEnabled(false);

        kvkk_check.setChecked(ChechBoxDurumGüncelleme.getInstance().isChecked());


        kvkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(HesapOlustur.this,KVKK.class);
                startActivity(i);
            }


        });
        girisYapYnldr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(HesapOlustur.this,MainActivity.class);
                startActivity(i);
            }


        });
        sifreGoster.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked) {
                sifre.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                sifre.setInputType((InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        });

        cinsiyet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                secilenCinsiyet = radioButton.getText().toString();
            }
        });


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.sehirler_items, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sehirler.setAdapter(adapter);

            sehirler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    secilenSehir = selectedItem;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            kvkk_check.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked) {
                sifre.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                sifre.setInputType((InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        });
        kvkk_check.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked) {
                kayitOl.setEnabled(true);
                kayitOl.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view){
                        UyeBilgileri uyeBilgileri = new UyeBilgileri(isim.getText().toString(),soyisim.getText().toString(),mail.getText().toString(),sifre.getText().toString(),secilenCinsiyet,secilenSehir);
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(HesapOlustur.this);

                        boolean basari = dataBaseHelper.addOne(uyeBilgileri);
                        if (basari) {
                            new AlertDialog.Builder(HesapOlustur.this)
                                    .setTitle("Kayıt Başarılı")
                                    .setMessage("Kayıt işleminiz başarıyla tamamlandı.")
                                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(HesapOlustur.this, MainActivity.class);
                                            startActivity(i);
                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(HesapOlustur.this, "Kayıt işlemi başarısız oldu.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            else{

            }
        });







    }

}