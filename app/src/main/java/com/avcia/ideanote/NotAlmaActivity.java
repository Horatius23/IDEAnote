package com.avcia.ideanote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avcia.ideanote.Models.Notlar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotAlmaActivity extends AppCompatActivity {
    EditText editText_baslik;
    EditText editText_notlar;
    ImageView imageView_save;
    Notlar notlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_alma);
        imageView_save = findViewById(R.id.imageView_save);
        editText_baslik = findViewById(R.id.editText_baslik);
        editText_notlar = findViewById(R.id.editText_notlar);
        //lamdaya çevrildi
        imageView_save.setOnClickListener(v -> {
            String baslik = editText_baslik.getText().toString();
            String betimleme = editText_notlar.getText().toString();
            if (betimleme.isEmpty()){
                Toast.makeText(NotAlmaActivity.this, "Lütfen Bir Not Ekleyin", Toast.LENGTH_SHORT).show();
                return;
            }
            String pattern = "EEE MMM yyyy HH:mm:ss";
            SimpleDateFormat simpleTarih =new SimpleDateFormat(pattern,
                    new Locale("tr", "TR"));
            String date = simpleTarih.format(new Date());
            System.out.println(date);

            notlar = new Notlar();
            notlar.setNotlar(betimleme);
            notlar.setTarih(simpleTarih.format(date));

            Intent intent = new Intent();
            intent.putExtra("Not", notlar);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}