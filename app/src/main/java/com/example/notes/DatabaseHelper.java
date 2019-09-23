package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Notes";
    private static final String TABLE_NAME = "tbl_catatan";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_DESKRIPSI = "deskripsi";
    private static final String KEY_WAKTU = "waktu";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createUserTable = "Create Table "+TABLE_NAME+"("+KEY_JUDUL+" TEXT PRIMARY KEY,"+KEY_DESKRIPSI+" TEXT,"+KEY_WAKTU+" TEXT"+")";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = ("drop table if exists " +TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(Catatan catatan){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, catatan.getJudul());
        values.put(KEY_DESKRIPSI, catatan.getDeskripsi());
        values.put(KEY_WAKTU, catatan.getWaktu());

        db.insert(TABLE_NAME, null, values);
    }

    public List<Catatan> selectNotesData(){
        ArrayList<Catatan> notesList = new ArrayList<Catatan>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_JUDUL,KEY_DESKRIPSI,KEY_WAKTU};

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()){
            String judul = c.getString(0);
            String deskripsi = c.getString(1);
            String waktu = c.getString(2);

            Catatan catatan = new Catatan();
            catatan.setJudul(judul);
            catatan.setDeskripsi(deskripsi);
            catatan.setWaktu(waktu);
            notesList.add(catatan);
        }
        return notesList;
    }

    public void delete(String judul){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_JUDUL+"='"+judul+"'";
        db.delete(TABLE_NAME, whereClause, null);
    }

    public void update(Catatan catatan){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, catatan.getJudul());
        values.put(KEY_DESKRIPSI, catatan.getDeskripsi());
        values.put(KEY_WAKTU, catatan.getWaktu());

        String whereClause = KEY_JUDUL+"='"+catatan.getJudul()+"'";
        db.update(TABLE_NAME, values, whereClause, null);
    }
}
