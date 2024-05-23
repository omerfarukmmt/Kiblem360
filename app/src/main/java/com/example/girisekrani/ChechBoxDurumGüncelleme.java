package com.example.girisekrani;

public class ChechBoxDurumGüncelleme {
    private static ChechBoxDurumGüncelleme instance;
    private boolean isChecked = false;

    private ChechBoxDurumGüncelleme() {
        // Private constructor to prevent instantiation
    }

    public static synchronized ChechBoxDurumGüncelleme getInstance() {
        if (instance == null) {
            instance = new ChechBoxDurumGüncelleme();
        }
        return instance;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
