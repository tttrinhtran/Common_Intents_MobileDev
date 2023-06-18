package com.example.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText extends AppCompatActivity {
    EditText editText;
    ImageView imageView;

    public static final Integer RecordAudioRequestCode = 1;

    private SpeechRecognizer speechRecognizer;
    AlertDialog.Builder alertSpeechDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speechtotext_layout);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);

        if(ContextCompat.checkSelfPermission( SpeechToText.this, Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED ) {
            checkPermission();
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent speechIntent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        speechIntent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );

        speechIntent.putExtra( RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                ViewGroup viewGroup = findViewById( android.R.id.content );
                View dialogView = LayoutInflater.from( SpeechToText.this).inflate(R.layout.alertcustom, viewGroup, false );

                alertSpeechDialog = new AlertDialog.Builder(SpeechToText.this);
                alertSpeechDialog.setMessage("Listening...");
                alertSpeechDialog.setView(dialogView);
                alertDialog = alertSpeechDialog.create();
                alertDialog.show();
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle bundle) {
                imageView.setImageResource(R.drawable.baseline_mic_24);
                ArrayList<String> arrayList = bundle.getStringArrayList( SpeechRecognizer.RESULTS_RECOGNITION );
                editText.setText(arrayList.get(0));
                alertDialog.dismiss();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if( motionEvent.getAction() == MotionEvent.ACTION_UP ) {
                    speechRecognizer.stopListening();
                }

                if( motionEvent.getAction() == MotionEvent.ACTION_DOWN ) {
                    imageView.setImageResource( R.drawable.baseline_mic_24 );
                    speechRecognizer.startListening(speechIntent);
                }
                return false;
            }
        });
    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            ActivityCompat.requestPermissions( SpeechToText.this, new String[]{
                Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode );
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if( requestCode == RecordAudioRequestCode && grantResults.length > 0 ) {
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                Toast.makeText( this, "Permission Granted", Toast.LENGTH_SHORT ).show();
        }
    }
}