package com.example.girisekrani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UyeBilgileriAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UyeBilgileri> uyeBilgileriList;

    public UyeBilgileriAdapter(Context context, ArrayList<UyeBilgileri> uyeBilgileriList) {
        this.context = context;
        this.uyeBilgileriList = uyeBilgileriList;
    }

    @Override
    public int getCount() {
        return uyeBilgileriList.size();
    }

    @Override
    public Object getItem(int position) {
        return uyeBilgileriList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_uye_bilgileri_adapter, parent, false);
        }

        UyeBilgileri uyeBilgileri = (UyeBilgileri) getItem(position);

        TextView isim = convertView.findViewById(R.id.isim);
        TextView soyisim = convertView.findViewById(R.id.soyisim);
        TextView email = convertView.findViewById(R.id.email);
        TextView cinsiyet = convertView.findViewById(R.id.cinsiyet);
        TextView sehir = convertView.findViewById(R.id.sehir);

        isim.setText(uyeBilgileri.getIsim());
        soyisim.setText(uyeBilgileri.getSoyisim());
        email.setText(uyeBilgileri.getEmail());
        cinsiyet.setText(uyeBilgileri.getCinsiyet());
        sehir.setText(uyeBilgileri.getSehir());

        return convertView;
    }
}
