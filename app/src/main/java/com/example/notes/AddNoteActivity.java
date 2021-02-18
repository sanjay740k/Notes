package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title, description;
    private TextView chooseImage;
    String noteTitle, noteDesc;
    private ImageView imgView;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.noteTitle);
        description = findViewById(R.id.noteDescription);
        chooseImage = findViewById(R.id.noteImage);
        imgView = findViewById(R.id.simage);
        Button saveInfo = findViewById(R.id.save);

        chooseImage.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            startActivityForResult(gallery, RESULT_LOAD_IMAGE);
        });

        saveInfo.setOnClickListener(v -> SavesInfo());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            flag = true;
            Uri imageUri = data.getData();
            imgView.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Log.i("data", bitmap.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SavesInfo() {
        noteTitle = title.getText().toString().trim();
        noteDesc = description.getText().toString().trim();
        if(TextUtils.isEmpty(noteTitle)){
            Toast.makeText(AddNoteActivity.this ,"Please write title", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(noteDesc)){
            Toast.makeText(AddNoteActivity.this ,"Please write message", Toast.LENGTH_LONG).show();
        }
        else{
            String noteImage = "";
            if(flag)
            noteImage = encodeTobase64(bitmap);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("title", noteTitle);
            intent.putExtra("desc", noteDesc);
            intent.putExtra("flag", flag);
            if(!noteImage.isEmpty())
            intent.putExtra("image", noteImage);
            startActivity(intent);
        }
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}