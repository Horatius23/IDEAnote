package com.avcia.ideanote.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.avcia.ideanote.Models.Notlar;

import java.util.List;

@Dao
public interface MainDAO {
    //FIX ERRORS!
    @Insert(onConflict = REPLACE)
    void insert(Notlar notlar);
    @Query("SELECT * FROM notlar ORDER BY id DESC")
    List<Notlar> getAll();
    @Query("UPDATE notlar SET baslik = :baslik, notlar = :notlar WHERE ID = :id")
    void update(int id, String baslik, String notlar);
    @Delete
    void delete(Notlar notlar);
    @Query("UPDATE notlar SET sabitlendi = :pin WHERE ID = :id")
    void pin(int id, boolean pin);
}
