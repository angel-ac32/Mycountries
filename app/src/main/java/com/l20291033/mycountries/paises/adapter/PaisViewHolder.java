package com.l20291033.mycountries.paises.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.l20291033.mycountries.R;

public class PaisViewHolder extends RecyclerView.ViewHolder {

    TextView capital, population, pais;

    ShapeableImageView img;

    public PaisViewHolder(@NonNull View view) {
        super(view);
        pais = view.findViewById(R.id.countryItemTvCountryName);
        capital = view.findViewById(R.id.countryItemTvCountryCapital);
        population = view.findViewById(R.id.countryItemTvCountryPopulationLabel);
        img = view.findViewById(R.id.countryItemSivCountryimg);
    }
}
