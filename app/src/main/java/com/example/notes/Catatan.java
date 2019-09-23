package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Catatan implements Parcelable {
    String judul;
    String deskripsi;
    String waktu;

    public Catatan(){

    }


    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeString(this.waktu);
    }

    protected Catatan(Parcel in) {
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.waktu = in.readString();
    }

    public static final Parcelable.Creator<Catatan> CREATOR = new Parcelable.Creator<Catatan>() {
        @Override
        public Catatan createFromParcel(Parcel source) {
            return new Catatan(source);
        }

        @Override
        public Catatan[] newArray(int size) {
            return new Catatan[size];
        }
    };
}
