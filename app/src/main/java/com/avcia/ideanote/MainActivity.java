package com.avcia.ideanote;

import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.avcia.ideanote.Adaptorler.NotlarListesiAdaptoru;
import com.avcia.ideanote.Database.RoomDB;
import com.avcia.ideanote.Models.Notlar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotlarListesiAdaptoru notlarListesiAdaptoru;
    List<Notlar> notlar = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        database = RoomDB.getInstance(this);
        notlar = database.mainDAO().getAll();
        updateRecycler(notlar);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotAlmaActivity.class);
                startActivityForResult(intent, 101);
            }
        });

    }

    private void updateRecycler(List<Notlar> notlar) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notlarListesiAdaptoru = new NotlarListesiAdaptoru(MainActivity.this, notlar, notlarClickLlistener);
        recyclerView.setAdapter(notlarListesiAdaptoru);
    }
    private final NotlarClickLlistener notlarClickLlistener = new NotlarClickLlistener() {
        @Override
        public void onClick(Notlar notlar) {

        }

        @Override
        public void uzunBasili(Notlar notlar, CardView cardview) {

        }
    };
}