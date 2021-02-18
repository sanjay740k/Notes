package com.example.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ListViewHolder> {

    private final Context context;
    private final List<Note> notes;
    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.note_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.date.setText(note.getDate());
        holder.time.setText(note.getTime());
        holder.image = note.getImage();

        holder.setListener(note.getId());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, time, date;
        public ImageView deleteItem;
        int position;
        public String image;

        public ListViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDesc);
            date = itemView.findViewById(R.id.textViewDate);
            time = itemView.findViewById(R.id.textViewTime);
            deleteItem = itemView.findViewById(R.id.deleteButoon);

            itemView.setOnClickListener(v -> {

                Intent intent = new Intent(context, ShowNoteActivity.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("desc", description.getText().toString());
                intent.putExtra("image", image);
                context.startActivity(intent);
            });
        }

        public void setListener(String id){
            deleteItem.setOnClickListener(v -> {
                SharedPreferences sharedPreferences = context.getSharedPreferences("sharedNotes", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(id).apply();
                position = getAdapterPosition();
                removeItemFromList(position);
            });
        }
    }

    private void removeItemFromList(int position) {

        notes.remove(position);
        notifyItemRemoved(position);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
