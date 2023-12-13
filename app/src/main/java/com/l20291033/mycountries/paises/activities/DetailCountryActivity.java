package com.l20291033.mycountries.paises.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.l20291033.mycountries.R;
import com.l20291033.mycountries.databinding.ActivityDetailCountryBinding;
import com.l20291033.mycountries.databinding.ActivityMainBinding;
import com.l20291033.mycountries.services.restcountries.api.RestCointriesApi;
import com.l20291033.mycountries.services.restcountries.client.RestCountriClient;
import com.l20291033.mycountries.services.restcountries.models.Country;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailCountryActivity extends AppCompatActivity {
    private RestCointriesApi restCointriesApi;
    private CompositeDisposable compositeDisposable;
    private Country country;
    private ActivityDetailCountryBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityDetailCountryBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        initComponents();
    }

    private void initComponents() {
        String cca3 = getIntent().getStringExtra("cca3");
        // Iniciar APi
        restCointriesApi = RestCountriClient.get_instance().create(RestCointriesApi.class);

        // Iniciar Bote basura
        compositeDisposable = new CompositeDisposable();

        // Solicitar la informacion del pais con el codigo cca3

        fetchCountriDataByCca3(cca3);
    }

    private void fetchCountriDataByCca3(String cca3) {
        compositeDisposable.add(
                restCointriesApi.getCountryByCca3(cca3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::dataCountri, this::error)
        );
    }

    private void error(Throwable throwable) {
    }

    private void dataCountri(Country country) {

        Glide.with(this).load(country.getFlags().getPNG()).into(viewBinding.DetailsImg);
        viewBinding.DetailsName.setText(country.getName().getOfficial());
        viewBinding.DetailsCapital.setText(country.getCapital().get(0));
        viewBinding.DetailsPoblacion.setText(String.valueOf(country.getPopulation()));

        setTitle(country.getFlag()+"\t"+viewBinding.DetailsName.getText().toString());
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}