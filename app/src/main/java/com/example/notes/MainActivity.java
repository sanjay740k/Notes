package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Note> currnotes;
    public RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    String title, description, noteDate, noteTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent addListIntent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(addListIntent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currnotes = new ArrayList<>();
        recyclerView.setAdapter(new NoteAdapter(this, currnotes));

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedNotes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CurrentDateandTime();
            title = extras.getString("title");
            description = (extras.getString("desc"));
            String uniqueID = UUID.randomUUID().toString();
            Set<String> hash_Set = new HashSet<String>();

            hash_Set.add("a"+title);
            hash_Set.add("b"+description);
            hash_Set.add("c"+noteDate);
            hash_Set.add("d"+noteTime);
            editor.putStringSet(uniqueID, hash_Set);
            editor.apply();
        }

        Map<String, ?> prefsMap = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String  uniqueID = entry.getKey();
            Set<String> hash_Set = sharedPreferences.getStringSet(uniqueID, new HashSet<String>());

            for (String s : hash_Set) {
                if(s.charAt(0)=='a')title = s.substring(1);
                else if(s.charAt(0)=='b')description = s.substring(1);
                else if(s.charAt(0)=='c')noteDate = s.substring(1);
                else if(s.charAt(0)=='d')noteTime = s.substring(1);
            }
            currnotes.add(new Note(uniqueID, title, description, noteDate, noteTime));
        }
    }

    private void CurrentDateandTime() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat simpleDateFormatDate = new java.text.SimpleDateFormat("MMM dd, yyyy");
        noteDate = simpleDateFormatDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a");
        noteTime = simpleDateFormatTime.format(calendar.getTime());
    }
}