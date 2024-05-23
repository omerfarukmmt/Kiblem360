package com.example.girisekrani;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.CheckBox;


import androidx.appcompat.app.AppCompatActivity;

public class KVKK extends AppCompatActivity {

    WebView mywebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kvkk);
        mywebView=(WebView) findViewById(R.id.webView);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl("https://www.mevzuat.gov.tr/mevzuat?MevzuatNo=6698&MevzuatTur=1&MevzuatTertip=5");
        WebSettings webSettings=mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Log.d("KVKK", "KVKK açıldı");
        registerForContextMenu(mywebView);
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kvkk_onay, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == R.id.onayliyorum){
            ChechBoxDurumGüncelleme.getInstance().setChecked(true);
            Intent i =  new Intent(KVKK.this,HesapOlustur.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.onaylamiyorum) {
            ChechBoxDurumGüncelleme.getInstance().setChecked(false);
            Intent i =  new Intent(KVKK.this,HesapOlustur.class);
            startActivity(i);
        }
        return super.onContextItemSelected(item);
    }


}