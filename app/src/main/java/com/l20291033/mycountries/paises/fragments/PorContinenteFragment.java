package com.l20291033.mycountries.paises.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.l20291033.mycountries.R;
import com.l20291033.mycountries.databinding.FragmentPorContinenteBinding;
import com.l20291033.mycountries.paises.adapter.PaisAdapter;
import com.l20291033.mycountries.services.restcountries.api.RestCointriesApi;
import com.l20291033.mycountries.services.restcountries.client.RestCountriClient;
import com.l20291033.mycountries.services.restcountries.models.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PorContinenteFragment extends Fragment {

    private FragmentPorContinenteBinding viewBinding;

    private RestCointriesApi restCointriesApi;
    private CompositeDisposable compositeDisposable;
    private PaisAdapter adapter;

    public PorContinenteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentPorContinenteBinding.inflate(inflater, container, false);

        initComponents();

        return viewBinding.getRoot();
    }

    private void initComponents(){

        // Construir nuestros objetos de conexion a la API
        restCointriesApi = RestCountriClient.get_instance().create(RestCointriesApi.class);
        // Preparar nuestro bots de basura
        compositeDisposable = new CompositeDisposable();

        // Llenar de informacion el spinner;
            // crear  un adaptador
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.continentes,
                android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.porContinenteSpiContinentes.setAdapter(adapter);

        // Configurar para obtener el valor seleccionado del spinner
        viewBinding.porContinenteSpiContinentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PorContinenteFragment.this.onItemSelected(adapterView, i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Seleccione un elemento", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el reclicler view
        viewBinding.porContinenteRVPaises.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Configurar el comportamineto del FabClear
        viewBinding.porContinenteFabClear.setOnClickListener((view)->{
            viewBinding.porContinenteSpiContinentes.setSelection(0);
        });

    }

    private void onItemSelected(AdapterView<?> parent, int position){
        if(position == 0){
            setRecyclerViewAdpater(new ArrayList<>());
            return;
        }

        String continente = (String) parent.getAdapter().getItem(position);
        //Toast.makeText(getContext(), "Elemento seleccionado: "+continente, Toast.LENGTH_SHORT).show();
        fetchCountriesByRegion(continente);
    }

    private void fetchCountriesByRegion(String continente) {
        compositeDisposable.add(
                restCointriesApi.getCountriesByRegion(continente, RestCountriClient.FIELDS_VALUES)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setRecyclerViewAdpater, this::error)
        );
    }

    private void error(Throwable throwable) {
        setRecyclerViewAdpater(new ArrayList<>());
    }

    private void setRecyclerViewAdpater(List<Country> countries) {
        // Creando el adapter
        adapter = new PaisAdapter(getContext(), countries);
        // Enviando la informacion al recicler views
        viewBinding.porContinenteRVPaises.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}