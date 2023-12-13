package com.l20291033.mycountries.paises.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.l20291033.mycountries.R;
import com.l20291033.mycountries.paises.activities.DetailCountryActivity;
import com.l20291033.mycountries.services.restcountries.models.Country;

import java.util.List;

public class PaisAdapter extends RecyclerView.Adapter<PaisViewHolder> {

    private Context context;
    private List<Country> countries;
    private int lastAnimationItem = -1;

    public PaisAdapter(Context context, List<Country> countries){
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public PaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_recycler_item_layout, parent, false);
        return new PaisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder holder, int position) {
        Country pais = countries.get(position);
        try{
            Glide.with(context).load(pais.getFlags().getPNG()).into(holder.img);
            holder.pais.setText(pais.getName().getOfficial());
            holder.population.setText(String.format("Poblacio: %,d", pais.getPopulation()));
            holder.capital.setText(pais.getCapital().get(0));

            // Agregar el onClickListerne
            holder.itemView.setOnClickListener((view)->{
                Intent intent = new Intent(context, DetailCountryActivity.class);
                intent.putExtra("cca3", pais.getCca3());
                context.startActivity(intent);
            });

            setAnimation(holder.itemView, position);
        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAnimation(View view, int pos){
        if(pos > lastAnimationItem){
            // Creando animacion
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation);
            view.startAnimation(animation);
            lastAnimationItem = pos;
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
