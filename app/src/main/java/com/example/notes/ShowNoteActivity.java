package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowNoteActivity extends AppCompatActivity {

    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        TextView title = findViewById(R.id.snoteTitle);
        TextView description = findViewById(R.id.snoteDescription);
        ImageView imageView = findViewById(R.id.simage);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString("title"));
            description.setText(extras.getString("desc"));
            image = extras.getString("image");
            if(!image.isEmpty()){
                imageView.setImageBitmap(decodeBase64(image));
            }
        }
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}