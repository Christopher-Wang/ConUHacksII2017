package com.example.johnny.snapcalendar;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ConfirmationFragment extends DialogFragment {
    String date;
    Bitmap poster;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://snapcalender.appspot.com");
    StorageReference posterStorageReference = storageReference.child("Poster");

    public Bitmap getPoster(Bitmap bitmap) {
        poster = bitmap;
        return poster;
    }
    public String getDate (String textblock){
        date = textblock;
        return date;
    }
    public void upload (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = posterStorageReference.putBytes(data);
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
            }
        });
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirmation, container, false);
        getDialog().setTitle("Simple Dialog");
        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        Button interested = (Button) rootView.findViewById(R.id.interested);
        ImageView preview = (ImageView) rootView.findViewById(R.id.posterView);
      //  TextView displayDate = (TextView) rootView.findViewById(R.id.dateView);

        //displayDate.setText(getDate(date));
        preview.setImageBitmap(poster);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(poster);
            }
        });
        return rootView;
    }
}