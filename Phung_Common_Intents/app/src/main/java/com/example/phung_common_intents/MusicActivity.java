package com.example.phung_common_intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_MUSIC = 1;

    Button BtnSongChoice;
    Button BtnPlaySong;
    TextView SongInformation;

    Uri selectedMusicUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        BtnSongChoice = findViewById(R.id.btn_choose_song);
        BtnPlaySong = findViewById(R.id.btn_play_song);
        SongInformation = findViewById(R.id.txt_info_song);

        BtnSongChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseSongFromStorage();
            }
        });

        BtnPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMusicUri != null) {
                    PlaySong();
                } else {
                    Toast.makeText(MusicActivity.this, "No audio file selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ChooseSongFromStorage() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_PICK_MUSIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_MUSIC && resultCode == RESULT_OK && data != null) {
            selectedMusicUri = data.getData();

            // Handle the selected music file
            if (selectedMusicUri != null) {
                String fileName = getFileNameFromUri(selectedMusicUri);
                if (fileName != null) {
                    SongInformation.setText(fileName);
                } else {
                    SongInformation.setText("No song is chosen !");
                }
            }
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        String[] projection = {MediaStore.Audio.Media.DISPLAY_NAME};

        CursorLoader cursorLoader = new CursorLoader(this, uri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            fileName = cursor.getString(columnIndex);
            cursor.close();
        }

        return fileName;
    }

    private void PlaySong() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedMusicUri, "audio/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

}