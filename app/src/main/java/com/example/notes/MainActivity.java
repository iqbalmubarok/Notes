package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerDataAdapter.OnUserActionListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Catatan> listCatatan;
    Context context;

    FloatingActionButton tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Notes");
        context = this;

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        setupRecyclerview();

        tambah = findViewById(R.id.btnTambahData);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambahData = new Intent(context, Input.class);
                startActivity(tambahData);
            }
        });
    }

    public void setupRecyclerview(){
        DatabaseHelper db = new DatabaseHelper(this);
        listCatatan = db.selectNotesData();
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(this, listCatatan, this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed(){
        Intent move = new Intent(this, MainActivity.class);
        startActivity(move);
    }

    @Override
    public void onUserAction(final Catatan currentCatatan){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Choose Options")
                .setPositiveButton("Edit Data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent editData = new Intent(context, Input.class);
                        editData.putExtra("UPDATE_INTENT", currentCatatan);
                        editData.putExtra("UPDATE_ACTION", "update");
                        context.startActivity(editData);
                    }
                })
                .setNegativeButton("Delete Data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.delete(currentCatatan.getJudul());
                        setupRecyclerview();
                        Toast.makeText(context,"Data "+currentCatatan.getJudul()+" Terhapus", Toast.LENGTH_LONG).show();
                        finish();
                        onBackPressed();
                    }
                });
        AlertDialog dialog1 = dialog.create();
        dialog1.show();
    }
}
