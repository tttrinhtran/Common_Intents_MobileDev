package com.example.intent;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class searching extends AppCompatActivity {

    private Button searchButton;
    private Button nextButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_layout);

        searchButton = findViewById(R.id._btnsearch);
        editText = findViewById(R.id.inputText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputString = editText.getText().toString();
                if (!inputString.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, inputString);
                    try{
                        startActivity(intent);
                    }catch(Exception ActivityNotFoundException){
                        Toast.makeText(searching.this, "Intent cannot found", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(searching.this, "Please input all information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( searching.this, SpeechToText.class );
                startActivity(intent);
            }
        });

    }
}