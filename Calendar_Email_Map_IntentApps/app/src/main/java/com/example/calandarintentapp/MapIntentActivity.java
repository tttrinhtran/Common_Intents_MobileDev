package com.example.calandarintentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapIntentActivity extends AppCompatActivity {
    Button _nextButton, _startMapButton;
    EditText _latitudeText, _longitudeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_intent);

        _nextButton = (Button) findViewById(R.id.ToMainActivityButton);
        _startMapButton = (Button) findViewById(R.id.setMapButton);
        _latitudeText = (EditText) findViewById(R.id.LatitudeBox);
        _longitudeText = (EditText) findViewById(R.id.LongitudeBox);

        _startMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sLatitude = _latitudeText.getText().toString(), sLongtitude = _longitudeText.getText().toString();
                if (!sLatitude.isEmpty() && !sLongtitude.isEmpty()) {

                    Uri route = Uri.parse("geo:" + sLatitude + ',' + sLongtitude + "?z=10&q=restaurants");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(route);
                    try {
                        startActivity(intent);
                    } catch (Exception ActivityNotFoundException) {
                        Toast.makeText(MapIntentActivity.this, "Intent not Found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MapIntentActivity.this, "Please full fill the information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(MapIntentActivity.this, CalendarIntentActivity.class);
                startActivity(temp);
            }
        });
    }
}