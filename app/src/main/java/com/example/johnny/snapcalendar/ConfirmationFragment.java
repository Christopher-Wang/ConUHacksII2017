package com.example.johnny.snapcalendar;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmationFragment extends DialogFragment {
    String date;
    Bitmap poster;
    public Bitmap getPoster(Bitmap bitmap) {
        poster = bitmap;
        return poster;
    }
    public String getDate (String textblock){
        date = textblock;
        return date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirmation, container, false);
        getDialog().setTitle("Simple Dialog");
        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        ImageView preview = (ImageView) rootView.findViewById(R.id.posterView);
        TextView displayDate = (TextView) rootView.findViewById(R.id.dateView);

        displayDate.setText(getDate(date));
        preview.setImageBitmap(poster);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}