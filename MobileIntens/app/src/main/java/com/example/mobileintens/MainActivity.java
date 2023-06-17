package com.example.mobileintens;

import android.Manifest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final  int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private static final  int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        CameraButton();
        AlarmButton();
        WebButton(); 
    }

    private void WebButton() {
        Button btn = (Button) findViewById(R.id.buttonWeb);
        btn.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.google.com.vn/?hl=vi");

                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void AlarmButton() {
        Button btn = (Button) findViewById(R.id.buttonAlarm);
        btn.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText hour = (EditText) findViewById(R.id.textHour);
                EditText minute = (EditText) findViewById(R.id.textMinute);
                SetAlarm(hour, minute);
            }
        });
    }

    private void SetAlarm(EditText hour, EditText minute) {
        if(!hour.getText().toString().isEmpty() && !minute.getText().toString().isEmpty()){
            int h = Integer.parseInt(hour.getText().toString());
            int m = Integer.parseInt(minute.getText().toString());
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_HOUR, h)
                    .putExtra(AlarmClock.EXTRA_MINUTES, m);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }else{
                Toast.makeText(this, "There is no app for Alarm", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please input value", Toast.LENGTH_SHORT).show();
        }

    }


    private void CameraButton() {
        Button btn = (Button) findViewById(R.id.buttonCam);
        btn.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Capture();
            }
        });
    }

    // if there is no permission -> request, else -> continue
    private void Capture() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            saveImage(imageBitmap);

        }
    }

    //save image to gallery
    private void saveImage(Bitmap imageBitmap) {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";
        File imageFile = new File(storageDir,fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(imageFile));
            sendBroadcast(mediaScanIntent);

            Toast.makeText(this, "Image saved successful", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void viewImage(Bitmap imageBitmap) {
        Button btn = (Button) findViewById(R.id.viewImage);
        btn.setMovementMethod(LinkMovementMethod.getInstance());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(imageBitmap);
        final Context context = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),Integer.parseInt(imageBitmap.toString()));
            }
        });



    }
}