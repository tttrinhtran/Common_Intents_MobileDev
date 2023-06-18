package com.example.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Sharing extends AppCompatActivity {

    private Button sendButton;
    private Button nextButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_layout);

        sendButton = findViewById(R.id.sendButton);
        editText = findViewById(R.id.inputText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputString = editText.getText().toString();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, inputString );
                intent.setType("text/plain");

                if( intent.resolveActivity(getPackageManager()) != null ) {
                    startActivity(intent);
                }
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Sharing.this, SpeechToText.class );
                startActivity(intent);
            }
        });

    }
}