package com.avcia.ideanote.Database;

import android.content.*;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.avcia.ideanote.Models.Notlar;

@Database(entities = Notlar.class,version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private  static String DATABASE_NAME = "IDEAnote";

    public synchronized static RoomDB getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract MainDAO mainDAO();
}
