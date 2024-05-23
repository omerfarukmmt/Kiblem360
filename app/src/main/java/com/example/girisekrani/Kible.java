package com.example.girisekrani;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Kible extends AppCompatActivity implements SensorEventListener {

    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    private ImageView compas_image;
    private TextView derece, kible;
    private Spinner citySpinner;


    private HashMap<String, Integer> cityQiblaAngles;
    private int selectedQiblaAngle = 151;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kible);

        compas_image = findViewById(R.id.image_compas);
        derece = findViewById(R.id.derece);
        kible = findViewById(R.id.kible);
        citySpinner = findViewById(R.id.city_spinner);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        cityQiblaAngles = new HashMap<>();
        cityQiblaAngles.put("Adana", 161);
        cityQiblaAngles.put("Adapazarı", 150);
        cityQiblaAngles.put("Adıyaman", 171);
        cityQiblaAngles.put("Afyon", 149);
        cityQiblaAngles.put("Ağrı", 185);
        cityQiblaAngles.put("Aksaray", 158);
        cityQiblaAngles.put("Amasya", 164);
        cityQiblaAngles.put("Ankara", 156);
        cityQiblaAngles.put("Antakya", 163);
        cityQiblaAngles.put("Antalya", 147);
        cityQiblaAngles.put("Ardahan", 183);
        cityQiblaAngles.put("Artvin", 180);
        cityQiblaAngles.put("Aydın", 141);
        cityQiblaAngles.put("Balıkesir", 143);
        cityQiblaAngles.put("Bartın", 156);
        cityQiblaAngles.put("Batman", 180);
        cityQiblaAngles.put("Bayburt", 176);
        cityQiblaAngles.put("Bilecik", 149);
        cityQiblaAngles.put("Bingöl", 177);
        cityQiblaAngles.put("Bitlis", 183);
        cityQiblaAngles.put("Bolu", 153);
        cityQiblaAngles.put("Burdur", 147);
        cityQiblaAngles.put("Bursa", 147);
        cityQiblaAngles.put("Çanakkale", 141);
        cityQiblaAngles.put("Çankırı", 158);
        cityQiblaAngles.put("Çorum", 162);
        cityQiblaAngles.put("Denizli", 144);
        cityQiblaAngles.put("Diyarbakır", 177);
        cityQiblaAngles.put("Düzce", 152);
        cityQiblaAngles.put("Edirne", 143);
        cityQiblaAngles.put("Elazığ", 174);
        cityQiblaAngles.put("Erzincan", 174);
        cityQiblaAngles.put("Erzurum", 179);
        cityQiblaAngles.put("Eskişehir", 150);
        cityQiblaAngles.put("Gaziantep", 167);
        cityQiblaAngles.put("Giresun", 171);
        cityQiblaAngles.put("Gümüşhane", 174);
        cityQiblaAngles.put("Hakkari", 188);
        cityQiblaAngles.put("Hatay", 156);
        cityQiblaAngles.put("Iğdır", 187);
        cityQiblaAngles.put("Isparta", 147);
        cityQiblaAngles.put("İstanbul", 148);
        cityQiblaAngles.put("İzmir", 140);
        cityQiblaAngles.put("Kahramanmaraş", 166);
        cityQiblaAngles.put("Karabük", 156);
        cityQiblaAngles.put("Karaman", 154);
        cityQiblaAngles.put("Kars", 184);
        cityQiblaAngles.put("Kastamonu", 159);
        cityQiblaAngles.put("Kayseri", 162);
        cityQiblaAngles.put("Kilis", 166);
        cityQiblaAngles.put("Kırıkkale", 157);
        cityQiblaAngles.put("Kırklareli", 145);
        cityQiblaAngles.put("Kırşehir", 159);
        cityQiblaAngles.put("Kocaeli", 149);
        cityQiblaAngles.put("Konya", 153);
        cityQiblaAngles.put("Kütahya", 148);
        cityQiblaAngles.put("Malatya", 171);
        cityQiblaAngles.put("Manisa", 141);
        cityQiblaAngles.put("Mardin", 179);
        cityQiblaAngles.put("Mersin", 158);
        cityQiblaAngles.put("Muğla", 141);
        cityQiblaAngles.put("Muş", 181);
        cityQiblaAngles.put("Nevşehir", 160);
        cityQiblaAngles.put("Niğde", 159);
        cityQiblaAngles.put("Ordu", 170);
        cityQiblaAngles.put("Osmaniye", 164);
        cityQiblaAngles.put("Rize", 177);
        cityQiblaAngles.put("Sakarya", 150);
        cityQiblaAngles.put("Samsun", 166);
        cityQiblaAngles.put("Siirt", 182);
        cityQiblaAngles.put("Sinop", 163);
        cityQiblaAngles.put("Sivas", 167);
        cityQiblaAngles.put("Şanlıurfa", 172);
        cityQiblaAngles.put("Şırnak", 184);
        cityQiblaAngles.put("Tekirdağ", 144);
        cityQiblaAngles.put("Tokat", 166);
        cityQiblaAngles.put("Trabzon", 175);
        cityQiblaAngles.put("Tunceli", 175);
        cityQiblaAngles.put("Uşak", 146);
        cityQiblaAngles.put("Van", 187);
        cityQiblaAngles.put("Yalova", 148);
        cityQiblaAngles.put("Yozgat", 161);
        cityQiblaAngles.put("Zonguldak", 154);
        cityQiblaAngles.put("Bakü", 202);
        cityQiblaAngles.put("Sabirabad", 199);
        cityQiblaAngles.put("Lefkoşa", 152);

        // Set up the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sehirler_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = parent.getItemAtPosition(position).toString();
                selectedQiblaAngle = cityQiblaAngles.getOrDefault(city, 151);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        derece.setText("Derece: " + Float.toString(degree));

        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        ra.setDuration(210);
        ra.setFillAfter(true);

        compas_image.startAnimation(ra);
        currentDegree = -degree;

        // Calculate the difference between the device's orientation and the Qibla direction
        float diff = degree - selectedQiblaAngle;
        if (diff < 0) {
            diff += 360;
        }

        // Update the Qibla direction text based on the difference
        if (diff <= 5 || diff >= 355) {
            kible.setText("Kıble: Tam Önünüzde");
            kible.setTextColor(Color.GREEN);
        } else if (diff > 5 && diff <= 45) {
            kible.setText("Kıble: Sağ Önünüzde");
            kible.setTextColor(Color.YELLOW);
        } else if (diff > 45 && diff < 135) {
            kible.setText("Kıble: Sağınızda");
            kible.setTextColor(Color.RED);
        } else if (diff >= 135 && diff <= 225) {
            kible.setText("Kıble: Arkanızda");
            kible.setTextColor(Color.RED);
        } else if (diff > 225 && diff <= 315) {
            kible.setText("Kıble: Solunuzda");
            kible.setTextColor(Color.RED);
        } else {
            kible.setText("Kıble: Sol Önünüzde");
            kible.setTextColor(Color.YELLOW);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
