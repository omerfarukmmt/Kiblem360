package com.example.girisekrani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String MEMBER_TABLE = "MEMBER_TABLE";
    public static final String COLUMN_MEMBER_NAME = "MEMBER_NAME";
    public static final String COLUMN_MEMBER_SURNAME = "MEMBER_SURNAME";
    public static final String COLUMN_MEMBER_MAIL = "MEMBER_MAIL";
    public static final String COLUMN_MEMBER_PASSWORD = "MEMBER_PASSWORD";
    public static final String COLUMN_MEMBER_GENDER = "MEMBER_GENDER";
    public static final String COLUMN_MEMBER_CITY = "MEMBER_CITY";
    public static final String COLUMN_MEMBER_RATING = "MEMBER_RATING";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "member.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MEMBER_TABLE + " (" +
                COLUMN_MEMBER_NAME + " TEXT, " +
                COLUMN_MEMBER_SURNAME + " TEXT, " +
                COLUMN_MEMBER_MAIL + " TEXT, " +
                COLUMN_MEMBER_PASSWORD + " TEXT, " +
                COLUMN_MEMBER_GENDER + " TEXT, " +
                COLUMN_MEMBER_CITY + " TEXT, " +
                COLUMN_MEMBER_RATING + " REAL)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + MEMBER_TABLE + " ADD COLUMN " + COLUMN_MEMBER_RATING + " REAL");
        }
    }

    public boolean addOne(UyeBilgileri uyeBilgileri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEMBER_NAME, uyeBilgileri.getIsim());
        cv.put(COLUMN_MEMBER_SURNAME, uyeBilgileri.getSoyisim());
        cv.put(COLUMN_MEMBER_MAIL, uyeBilgileri.getEmail());
        cv.put(COLUMN_MEMBER_PASSWORD, uyeBilgileri.getSifre());
        cv.put(COLUMN_MEMBER_GENDER, uyeBilgileri.getCinsiyet());
        cv.put(COLUMN_MEMBER_CITY, uyeBilgileri.getSehir());

        long insert = db.insert(MEMBER_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_MEMBER_MAIL};
        String selection = COLUMN_MEMBER_MAIL + "=? AND " + COLUMN_MEMBER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(MEMBER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public String getCityFromDatabase(String email) {
        String city = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MEMBER_CITY + " FROM " + MEMBER_TABLE + " WHERE " + COLUMN_MEMBER_MAIL + " = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            int cityIndex = cursor.getColumnIndex(COLUMN_MEMBER_CITY);
            if (cityIndex != -1) {
                city = cursor.getString(cityIndex);
            }
        }
        cursor.close();
        db.close();
        return city;
    }

    public ArrayList<UyeBilgileri> searchMembers(String query) {
        ArrayList<UyeBilgileri> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MEMBER_TABLE + " WHERE " +
                COLUMN_MEMBER_NAME + " LIKE ? OR " +
                COLUMN_MEMBER_SURNAME + " LIKE ? OR " +
                COLUMN_MEMBER_MAIL + " LIKE ? OR " +
                COLUMN_MEMBER_CITY + " LIKE ? OR " +
                COLUMN_MEMBER_GENDER + " LIKE ?", new String[]{"%" + query + "%", "%" + query + "%", "%" + query + "%", "%" + query + "%", "%" + query + "%"});
        if (cursor.moveToFirst()) {
            do {
                String isim = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME));
                String soyisim = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_SURNAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_MAIL));
                String sifre = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_PASSWORD));
                String cinsiyet = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_GENDER));
                String sehir = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_CITY));
                float rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_MEMBER_RATING));
                UyeBilgileri uyeBilgileri = new UyeBilgileri(isim,soyisim,email,sifre,cinsiyet,sehir,rating);
                resultList.add(uyeBilgileri);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }
    public boolean updateUser(UyeBilgileri uyeBilgileri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEMBER_NAME, uyeBilgileri.getIsim());
        cv.put(COLUMN_MEMBER_SURNAME, uyeBilgileri.getSoyisim());
        cv.put(COLUMN_MEMBER_MAIL, uyeBilgileri.getEmail());
        cv.put(COLUMN_MEMBER_PASSWORD, uyeBilgileri.getSifre());
        cv.put(COLUMN_MEMBER_GENDER, uyeBilgileri.getCinsiyet());
        cv.put(COLUMN_MEMBER_CITY, uyeBilgileri.getSehir());
        cv.put(COLUMN_MEMBER_RATING, uyeBilgileri.getRating());

        int update = db.update(MEMBER_TABLE, cv, COLUMN_MEMBER_MAIL + " = ?", new String[]{uyeBilgileri.getEmail()});
        db.close();
        return update > 0;
    }

    public UyeBilgileri getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MEMBER_TABLE + " WHERE " + COLUMN_MEMBER_MAIL + " = ?", new String[]{email});
        UyeBilgileri uyeBilgileri = null;
        if (cursor.moveToFirst()) {
            String isim = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME));
            String soyisim = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_SURNAME));
            String sifre = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_PASSWORD));
            String cinsiyet = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_GENDER));
            String sehir = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_CITY));
            float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MEMBER_RATING));
            uyeBilgileri = new UyeBilgileri(isim, soyisim, email, sifre, cinsiyet, sehir,rating);
        }
        cursor.close();
        db.close();
        return uyeBilgileri;
    }
    public boolean deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(MEMBER_TABLE, COLUMN_MEMBER_MAIL + " = ?", new String[]{email});
        db.close();
        return rowsAffected > 0;
    }

}
