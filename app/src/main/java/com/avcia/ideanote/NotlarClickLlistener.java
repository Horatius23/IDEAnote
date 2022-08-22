package com.avcia.ideanote;

import androidx.cardview.widget.CardView;

import com.avcia.ideanote.Models.Notlar;

public interface NotlarClickLlistener {
   void onClick(Notlar notlar);
   void uzunBasili(Notlar notlar, CardView cardview);

}
