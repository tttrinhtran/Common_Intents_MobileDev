package com.example.phung_common_intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button BtnSongChoice;
    Button BtnPlaySong;
    Button BtnInternalStorage;
    TextView SongInformation;

    Button _nextIntent;

    Uri selectedMusicUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnInternalStorage = findViewById(R.id.btn_internal_storage_settings);
        _nextIntent = findViewById(R.id.btn_to_note);




        BtnInternalStorage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInternalStorageSettings();
            }
        }));

        _nextIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(MainActivity.this, PhoneCallAcitivity.class);
                startActivity(temp);
            }
        });
    }



    private void openInternalStorageSettings() {
        Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
        startActivity(intent);
    }

}