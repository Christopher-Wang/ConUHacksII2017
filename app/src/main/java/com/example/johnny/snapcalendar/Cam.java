package com.example.johnny.snapcalendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.vision.text.TextRecognizer;

import java.util.Calendar;

public class Cam extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    TextRecognizer textRecognizer;
    public final static String image ="com.example.johnny.snapcalendar.image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        textRecognizer = new TextRecognizer.Builder(this).build();
        takeImageFromCamera();
    }
    public void takeImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MAIN", "NOOOOOOOOOOOOOOOO TEXTTTTTTTT 1111111111111111111111111");
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(image, mphoto);
            startActivity(intent);


        }
        else{
            Log.d("MAIN", "NOOOOOOOOOOOOOOOO TEXTTTTTTTT ksghvidshfbilvawejfgbv");
        }
    }

    public void addEvent(String title, Calendar begin){
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,begin);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
