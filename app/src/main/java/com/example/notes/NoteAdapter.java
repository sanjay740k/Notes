package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        View view = inflater.inflate(R.layout.note_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.date.setText(note.getDate());
        holder.time.setText(note.getTime());

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
}
