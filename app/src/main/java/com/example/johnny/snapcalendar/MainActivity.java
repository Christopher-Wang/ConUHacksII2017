package com.example.johnny.snapcalendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private String output;
    ImageView mimageView;
    Bitmap mphoto;
    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mimageView = (ImageView) this.findViewById(R.id.image_from_camera);
        textRecognizer = new TextRecognizer.Builder(this).build();
        Intent intent = getIntent();

        if(intent.getParcelableExtra(Cam.image)!=null){

            Bitmap bitmap = (Bitmap) intent.getParcelableExtra(Cam.image);
            mimageView.setImageBitmap(bitmap);
            try {
                SparseArray<TextBlock> foo = getText(bitmap);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void takeImageFromCamera(View view){
        Intent intent = new Intent(this, Cam.class);
        startActivity(intent);
    }

    public String getOutput() {
        return output;
    }

    public SparseArray<TextBlock> getText(Bitmap bitmap) throws ParseException {
        Frame outputFrame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> foo = findText(outputFrame);
        if(foo.get(0)!=null) {
            for(int i =0;i<foo.size();i++) {
                output = foo.get(i).getValue();
                Log.d("MAIN", output);

            try{
            DateParse date = new DateParse();
            date.printDate(output);
            Log.d("worked", "worked");}
            catch(ParseException e) {

            }
        }
        }
        else{
            Log.d("MAIN", "NOOOOOOOOOOOOOOOO TEXTTTTTTTT");
        }
        return foo;
    }

    private SparseArray<TextBlock> findText(Frame frame) {
        SparseArray<TextBlock> foo=textRecognizer.detect(frame);
        return foo;
    }
}
