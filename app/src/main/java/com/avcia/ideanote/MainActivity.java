package com.avcia.ideanote;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
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
        fab_add.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotAlmaActivity.class);
            startActivityForResult(intent, 401);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==401){
            if (resultCode== Activity.RESULT_OK){
                Notlar yeni_notlar = (Notlar) data.getSerializableExtra("Not");
                database.mainDAO().insert(yeni_notlar);
                notlar.clear();
                notlar.addAll(database.mainDAO().getAll());
                notlarListesiAdaptoru.notifyDataSetChanged();
            }}
        else if (requestCode==402){
            if (resultCode==Activity.RESULT_OK){
                Notlar yeni_notlar = (Notlar) data.getSerializableExtra("Not");
                database.mainDAO().update(yeni_notlar.getId(),
                                          yeni_notlar.getBaslik(),
                                          yeni_notlar.getNotlar());
                notlar.clear();
                notlar.addAll(database.mainDAO().getAll());
                notlarListesiAdaptoru.notifyDataSetChanged();
            }}}

    private void updateRecycler(List<Notlar> notlar) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notlarListesiAdaptoru = new NotlarListesiAdaptoru(MainActivity.this, notlar, notlarClickLlistener);
        recyclerView.setAdapter(notlarListesiAdaptoru);
    }
    private final NotlarClickLlistener notlarClickLlistener = new NotlarClickLlistener() {
        @Override
        public void onClick(Notlar notlar) {
            Intent intent = new Intent(MainActivity.this,
                    NotAlmaActivity.class);
            intent.putExtra("Eski_Not", notlar);
            startActivityForResult(intent, 402);
        }

        @Override
        public void uzunBasili(Notlar notlar, CardView cardview) {

        }
    };


}