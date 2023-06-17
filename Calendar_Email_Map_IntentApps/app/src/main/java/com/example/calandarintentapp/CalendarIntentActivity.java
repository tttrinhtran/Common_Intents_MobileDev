package com.example.calandarintentapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalendarIntentActivity extends AppCompatActivity {

    EditText _eTitle, _eLocation, _eDescription;
    Button _setBut,_nextBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _eTitle = findViewById(R.id.EventTitleBox);
        _eDescription = findViewById(R.id.DescriptionBox);
        _eLocation = findViewById(R.id.LocationBox);
        _setBut = (Button) findViewById(R.id.setBut);
        _nextBut = (Button) findViewById(R.id.ChangeActivity);

        _setBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sTitle = _eTitle.getText().toString();
                String sDescribe = _eDescription.getText().toString();
                String sLocate = _eLocation.getText().toString();

                if (!sTitle.trim().equals("") && !sDescribe.trim().equals("") && !sLocate.trim().equals("")) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);

                    intent.putExtra(CalendarContract.Events.TITLE, sTitle);
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, sLocate);
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, sDescribe);

                    try {
                        startActivity(intent);
                    }catch(Exception ActivityNotFoundException){
                        Toast.makeText(CalendarIntentActivity.this, "Intent not found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CalendarIntentActivity.this, "Please full fill the passing information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(CalendarIntentActivity.this, EmailIntentActivity.class);
                startActivity(temp);
            }
        });
    }
}