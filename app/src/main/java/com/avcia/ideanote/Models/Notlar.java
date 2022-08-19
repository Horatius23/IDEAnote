package com.avcia.ideanote.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notlar")
public class Notlar implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int Id = 0;
    @ColumnInfo(name = "baslik")
    String baslik = "";
    @ColumnInfo(name = "notlar")
    String notlar = "";
    @ColumnInfo(name = "tarih")
    String tarih = "";
    @ColumnInfo(name = "sabitlendi")
    boolean sabitlendi = false;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getNotlar() {
        return notlar;
    }

    public void setNotlar(String notlar) {
        this.notlar = notlar;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public boolean isSabitlendi() {
        return sabitlendi;
    }

    public void setSabitlendi(boolean sabitlendi) {
        this.sabitlendi = sabitlendi;
    }
}
