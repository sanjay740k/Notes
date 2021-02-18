package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title, description;
    String noteTitle, noteDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.noteTitle);
        description = findViewById(R.id.noteDescription);
        Button saveInfo = findViewById(R.id.save);

        saveInfo.setOnClickListener(v -> {
            SavesInfo();
        });
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("title", noteTitle);
            intent.putExtra("desc", noteDesc);
            startActivity(intent);
        }
    }
}