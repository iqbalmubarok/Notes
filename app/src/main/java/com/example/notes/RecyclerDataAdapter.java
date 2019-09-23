package com.example.notes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.NotesViewHolder> {

    Context context;
    OnUserActionListener listener;

    List<Catatan> listCatatanInfo;

    public interface OnUserActionListener{
        void onUserAction(Catatan catatan);
    }

    public RecyclerDataAdapter(Context context, List<Catatan> listCatatanInfo, OnUserActionListener listener){
        this.context = context;
        this.listCatatanInfo = listCatatanInfo;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerDataAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notes_row_item, parent, false);
        NotesViewHolder notesViewHolder = new NotesViewHolder(view);

        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDataAdapter.NotesViewHolder holder, final int position){
        final Catatan currentCatatan = listCatatanInfo.get(position);
        holder.txtJudul.setText(currentCatatan.getJudul());
        holder.txtDeskripsi.setText(currentCatatan.getDeskripsi());
        holder.txtWaktu.setText(currentCatatan.getWaktu());
        Log.d("sdsd1", currentCatatan.getJudul());
        Log.d("sdsd2", currentCatatan.getDeskripsi());
        Log.d("sdsd3", currentCatatan.getWaktu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserAction(currentCatatan);
            }
        });
    }

    @Override
    public int getItemCount(){
        return listCatatanInfo.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView txtJudul, txtDeskripsi, txtWaktu;

        public NotesViewHolder(@NonNull View itemView){
            super(itemView);

            txtJudul = itemView.findViewById(R.id.judulRow);
            txtDeskripsi = itemView.findViewById(R.id.deskripsiRow);
            txtWaktu = itemView.findViewById(R.id.waktuRow);
        }
    }
}
