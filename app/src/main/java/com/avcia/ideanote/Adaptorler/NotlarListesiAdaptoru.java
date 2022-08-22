package com.avcia.ideanote.Adaptorler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avcia.ideanote.Models.Notlar;
import com.avcia.ideanote.NotlarClickLlistener;
import com.avcia.ideanote.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NotlarListesiAdaptoru extends RecyclerView.Adapter<NotlarViewHolder>{
    Context context;
    List<Notlar> list;
    NotlarClickLlistener listener;

    public NotlarListesiAdaptoru(Context context, List<Notlar> list, NotlarClickLlistener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotlarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotlarViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.notlar_liste,
                parent,
                false));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    @Override
    public void onBindViewHolder(@NonNull NotlarViewHolder holder, int position) {
        holder.textview_baslik.setText(list.get(position).getBaslik());
        holder.textview_baslik.setSelected(true);
        holder.textview_notlar.setText(list.get(position).getNotlar());
        holder.textview_tarih.setText(list.get(position).getTarih());
        holder.textview_tarih.setSelected(true);

        if (list.get(position).isSabitlendi(){
            holder.imageview_pin.setImageResource(R.drawable.ic_round_push_pin_24);
        }
        else {
            holder.imageview_pin.setImageResource(0);
        }
    }

    private int getColor(){
        List<Integer> colorCode = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class NotlarViewHolder extends RecyclerView.ViewHolder {
    CardView notlar_container;
    TextView textview_notlar, textview_baslik, textview_tarih;
    ImageView imageview_pin;


    public NotlarViewHolder(@NonNull View itemView) {
        super(itemView);
        notlar_container = itemView.findViewById(R.id.notlar_container);
        textview_notlar = itemView.findViewById(R.id.textview_notlar);
        textview_baslik = itemView.findViewById(R.id.textview_baslik);
        textview_tarih = itemView.findViewById(R.id.textview_tarih);
        imageview_pin = itemView.findViewById(R.id.imageview_pin);
    }
}