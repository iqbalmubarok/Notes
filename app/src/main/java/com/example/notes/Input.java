package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Input extends AppCompatActivity {

    EditText edtJudul, edtDeskripsi;
    ImageButton btnBack, btnDelete;
    TextView txtBar;
    Button btnSubmit;
    Context context;
    Catatan updated;
    String aksi, judul = "simpan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getSupportActionBar().hide();
        context = this;

        aksi = getIntent().getStringExtra("UPDATE_ACTION");
        updated = getIntent().getParcelableExtra("UPDATE_INTENT");
        if (aksi == null){
            aksi = "simpan";
        }else {
            judul = String.valueOf(updated.getJudul());
        }

        edtJudul = findViewById(R.id.judulInput);
        edtDeskripsi = findViewById(R.id.deskripsiInput);
        txtBar = findViewById(R.id.txtBar);
        btnBack = findViewById(R.id.btnKembali);
        btnDelete = findViewById(R.id.btnHapus);
        btnSubmit = findViewById(R.id.btnSimpan);

        btnDelete.setVisibility(View.GONE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(Input.this, MainActivity.class);
                context.startActivity(kembali);
            }
        });

        if (aksi.equals("update")){
            btnSubmit.setText("update");
            txtBar.setText("Update Data");
            btnDelete.setVisibility(View.VISIBLE);
            edtJudul.setText(judul);
            edtJudul.setFocusable(false);
            edtDeskripsi.setText(updated.getDeskripsi());

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper db = new DatabaseHelper(context);
                    db.delete(updated.getJudul());
                    Toast.makeText(context,"Data "+updated.getJudul()+" Terhapus", Toast.LENGTH_LONG).show();

                    Intent kembali = new Intent(Input.this, MainActivity.class);
                    context.startActivity(kembali);
                    finish();
                }
            });

            Log.d("test", updated.getDeskripsi());
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                Catatan catatan = new Catatan();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm", Locale.US);
                String waktu = sdf.format(new Date());
                String label = btnSubmit.getText().toString();
                if (label.equals("simpan")){
                    catatan.setJudul(edtJudul.getText().toString());
                    catatan.setDeskripsi(edtDeskripsi.getText().toString());
                    catatan.setWaktu(waktu);
                    db.insert(catatan);
                    Intent move = new Intent(context, MainActivity.class);
                    context.startActivity(move);
                }
                if (label.equals("update")){
                    catatan.setJudul(edtJudul.getText().toString());
                    catatan.setDeskripsi(edtDeskripsi.getText().toString());
                    catatan.setWaktu(waktu);
                    db.update(catatan);
                    Intent move = new Intent(context, MainActivity.class);
                    context.startActivity(move);
                }
            }
        });
    }
}
