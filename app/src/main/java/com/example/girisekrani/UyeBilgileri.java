package com.example.girisekrani;

public class UyeBilgileri {
    private String isim,soyisim,email,sifre,cinsiyet,sehir;


    public UyeBilgileri(String isim, String soyisim, String email, String sifre, String cinsiyet,String selectedSehir) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.email = email;
        this.sifre = sifre;
        this.cinsiyet = cinsiyet;
        this.sehir = selectedSehir;
    }

    public UyeBilgileri() {
    }

    @Override
    public String toString() {
        return "UyeBilgileri{" +
                "isim='" + isim + '\'' +
                ", soyisim='" + soyisim + '\'' +
                ", email='" + email + '\'' +
                ", sifre='" + sifre + '\'' +
                ", cinsiyet='" + cinsiyet + '\'' +
                '}';
    }

    public String getSehir() {
        return sehir;
    }
    public void setSehir(String selectedSehir) {
        this.sehir = selectedSehir;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
}
