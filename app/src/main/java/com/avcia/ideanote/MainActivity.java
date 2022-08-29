package com.avcia.ideanote;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avcia.ideanote.Adaptorler.NotlarListesiAdaptoru;
import com.avcia.ideanote.Database.RoomDB;
import com.avcia.ideanote.Models.Notlar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    RecyclerView recyclerView;
    NotlarListesiAdaptoru notlarListesiAdaptoru;
    List<Notlar> notlar = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;
    Notlar seciliNot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView_home =findViewById(R.id.searchView_home);
        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        database = RoomDB.getInstance(this);
        notlar = database.mainDAO().getAll();
        updateRecycler(notlar);
        fab_add.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotAlmaActivity.class);
            startActivityForResult(intent, 401);
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String yeniText) {
                filter(yeniText);
                return true;
            }
        });
    }

    private void filter(String yeniText) {
        List<Notlar>  filtrelenmisListe = new ArrayList<>();
        for (Notlar tekNot : notlar){
            if (tekNot.getBaslik().toLowerCase().contains(yeniText.toLowerCase())
            || tekNot.getNotlar().toLowerCase().contains(yeniText.toLowerCase()))
            {
                filtrelenmisListe.add(tekNot);
            }
        }
        notlarListesiAdaptoru.filterListe(filtrelenmisListe);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==401){
            if (resultCode== Activity.RESULT_OK){
                Notlar yeni_notlar = (Notlar) Objects.requireNonNull(data).getSerializableExtra("Not");
                database.mainDAO().insert(yeni_notlar);
                notlar.clear();
                notlar.addAll(database.mainDAO().getAll());
                notlarListesiAdaptoru.notifyDataSetChanged();
            }}
        else if (requestCode==402){
            if (resultCode==Activity.RESULT_OK){
                Notlar yeni_notlar = (Notlar) Objects.requireNonNull(data).getSerializableExtra("Not");
                database.mainDAO().update(yeni_notlar.getId(), //PİN ID || id???
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
        public void uzunBasili(Notlar notlar, CardView cardview)
        {
            seciliNot = new Notlar();
            seciliNot = notlar;
            popUpGoster(cardview);
        }
    };

    private void popUpGoster(CardView cardview)
    {
        PopupMenu popupMenu = new PopupMenu(this, cardview);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.pin:
                if (seciliNot.isSabitlendi())
                {                                   //PİN ID || id???
                    database.mainDAO().pin(seciliNot.getId(), false);
                    Toast.makeText(MainActivity.this,
                            "Çıkarıldı!",
                            Toast.LENGTH_SHORT).show();
                }
                else {database.mainDAO().pin(seciliNot.getId(), true);
                Toast.makeText(MainActivity.this,
                        "Sabitlendi!",
                        Toast.LENGTH_SHORT).show();
                     }
                notlar.clear();
                notlar.addAll(database.mainDAO().getAll());
                notlarListesiAdaptoru.notifyDataSetChanged();
                return true;

            case R.id.sil:
                database.mainDAO().delete(seciliNot);
                notlar.remove(seciliNot);
                notlarListesiAdaptoru.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,
                        "Not Silindi!",
                        Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;

        }
    }
}