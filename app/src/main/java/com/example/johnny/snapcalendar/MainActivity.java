package com.example.johnny.snapcalendar;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> output = new ArrayList<String>();
    TextView text;
    private String title;
    ImageView mimageView;
    Bitmap mphoto;
    TextRecognizer textRecognizer;
    StringBuilder t= new StringBuilder();;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            FragmentManager fm = getFragmentManager();
            ConfirmationFragment dialogFragment = new ConfirmationFragment ();
           // dialogFragment.getDate(foo.get(0).getValue());
            dialogFragment.getPoster(bitmap);
            dialogFragment.show(fm, "Sample Fragment");
        }

    }

    public void takeImageFromCamera(View view){
        Intent intent = new Intent(this, Cam.class);
        startActivity(intent);
    }


    public SparseArray<TextBlock> getText(Bitmap bitmap) throws ParseException, IOException {
        Frame outputFrame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> foo = findText(outputFrame);
        if(foo.get(0)!=null) {
            for(int i =0;i<foo.size();i++) {
                output.add(i, foo.get(i).getValue());
                t.append(foo.get(i)+"\n");

                //Log.d("MAIN", output.get(i));


                try{
                    DateParse date = new DateParse();
                    date.printDate(output.get(i));
                    Log.d("worked", "worked");
                } catch(ParseException e) {
                    Log.d("not worked", "unworked");
                }
            }
            String bar=t.toString();
            text.setText(bar);
            title = output.get(0);
//            GoogleCalendar calendar = new GoogleCalendar();
//            GoogleCalendar calendar = new GoogleCalendar()
//            DateTime startDateTime = calendar.startDateTime(output[0]);
//            EventDateTime start = calendar.startEventDateTime(startDateTime, "America/New_York");
//            event.setStart(start);
//            EventDateTime end = calendar.endEventDateTime(startDateTime, "America/New_York");
//            event.setEnd(end);
//            String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
//            event.setRecurrence(Arrays.asList(recurrence));
//            EventReminder[] reminderOverrides = new EventReminder[] {
//                    new EventReminder().setMethod("email").setMinutes(24 * 60),
//                    new EventReminder().setMethod("popup").setMinutes(10),
//            };
//            Event.Reminders reminders = new Event.Reminders()
//                    .setUseDefault(false)
//                    .setOverrides(Arrays.asList(reminderOverrides));
//            event.setReminders(reminders);

//            String calendarId = "primary";
//            event = calendar.getService().events().insert(calendarId, event).execute();
//            System.out.printf("Event created: %s\n", event.getHtmlLink());


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
