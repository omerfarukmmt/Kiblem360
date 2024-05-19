package com.example.girisekrani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String MEMBER_TABLE = "MEMBER_TABLE";
    public static final String COLUMN_MEMBER_NAME = "MEMBER_NAME";
    public static final String COLUMN_MEMBER_SURNAME = "MEMBER_SURNAME";
    public static final String COLUMN_MEMBER_MAIL = "MEMBER_MAIL";
    public static final String COLUMN_MEMBER_PASSWORD = "MEMBER_PASSWORD";
    public static final String COLUMN_MEMBER_GENDER = "MEMBER_GENDER";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "member.db", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + MEMBER_TABLE + " (" + COLUMN_MEMBER_NAME + " TEXT, " + COLUMN_MEMBER_SURNAME + " TEXT, " + COLUMN_MEMBER_MAIL + " TEXT ," + COLUMN_MEMBER_PASSWORD + " TEXT, " + COLUMN_MEMBER_GENDER + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(UyeBilgileri uyeBilgileri){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEMBER_NAME,"ömer");
        cv.put(COLUMN_MEMBER_SURNAME,uyeBilgileri.getSoyisim());
        cv.put(COLUMN_MEMBER_MAIL,uyeBilgileri.getEmail());
        cv.put(COLUMN_MEMBER_PASSWORD,uyeBilgileri.getSifre());
        cv.put(COLUMN_MEMBER_GENDER,uyeBilgileri.getCinsiyet());


        long insert =  db.insert(MEMBER_TABLE,null,cv);
        if(insert==-1)
            return false;
        else
            return true;

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
}
