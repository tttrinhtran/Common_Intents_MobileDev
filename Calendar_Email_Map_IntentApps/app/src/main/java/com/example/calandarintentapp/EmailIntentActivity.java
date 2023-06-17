package com.example.calandarintentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailIntentActivity extends AppCompatActivity {

    EditText _address, _subject, _attachment;
    Button _SendBut, _NextBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_intent);

        _address = (EditText) findViewById(R.id.AddressBox);
        _subject = (EditText) findViewById(R.id.SubjectBox);
        _attachment = (EditText) findViewById(R.id.AttachmentBox);
        _SendBut = (Button) findViewById(R.id.sendButton);
        _NextBut = (Button) findViewById(R.id.NextButton);

        _SendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _sAddress = _address.getText().toString();
                String _sSubject = _subject.getText().toString();
                String _sAttach = _attachment.getText().toString();

                if (!_sAddress.isEmpty() && !_sSubject.isEmpty() && !_sAttach.isEmpty()){
                    Intent intent = new Intent (Intent.ACTION_SEND);
                    intent.setType("message/rfc822");

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{_sAddress});
                    intent.putExtra(Intent.EXTRA_SUBJECT, _sSubject);
                    intent.putExtra(Intent.EXTRA_TEXT, _sAttach);

                    try {
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                    }catch(Exception ActivityNotFoundException){
                        Toast.makeText(EmailIntentActivity.this, "Intent not Found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EmailIntentActivity.this, "Please full fill all information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _NextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(EmailIntentActivity.this, MapIntentActivity.class);
                startActivity(temp);
            }
        });
    }
}