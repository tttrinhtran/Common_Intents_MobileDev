package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneCall extends AppCompatActivity {

    private Button button;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonecall_layout);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText phoneNumber = findViewById(R.id.phoneNumber);
                String number = phoneNumber.getText().toString();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData( Uri.parse("tel:" + number ) );
                startActivity(intent);

            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PhoneCall.this, Sharing.class );
                startActivity(intent);
            }
        });
    }
}