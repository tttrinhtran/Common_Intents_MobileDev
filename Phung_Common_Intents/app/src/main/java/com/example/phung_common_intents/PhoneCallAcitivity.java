package com.example.phung_common_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneCallAcitivity extends AppCompatActivity {

    Button _btnMakeNote, _btnBack;

    EditText _boxPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_intent_acitivity);

        _btnMakeNote = findViewById(R.id.btn_call);
        _btnBack = findViewById(R.id.btn_prev_Activity);
        _boxPhoneNum = findViewById(R.id.PhoneNumberBox);


        _btnMakeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _sPhoneNum = _boxPhoneNum.getText().toString();
                if (!_sPhoneNum.isEmpty()) {
                    Uri calling = Uri.parse("tel:" + _sPhoneNum);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(calling);
                    try {
                        startActivity(intent);
                    } catch (Exception ActivityNotFoundException) {
                        Toast.makeText(PhoneCallAcitivity.this, "Intent does not Found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PhoneCallAcitivity.this, "Please fill the phone number.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(PhoneCallAcitivity.this, MusicActivity.class);
                startActivity(temp);
            }
        });
    }
}