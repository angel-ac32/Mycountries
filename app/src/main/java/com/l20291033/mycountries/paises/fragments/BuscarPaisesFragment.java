package com.l20291033.mycountries.paises.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.l20291033.mycountries.R;
import com.l20291033.mycountries.databinding.FragmentBuscarPaisesBinding;
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

public class BuscarPaisesFragment extends Fragment {

    private FragmentBuscarPaisesBinding viewBinding;
    private RestCointriesApi restCointriesApi;
    private CompositeDisposable compositeDisposable;
    private PaisAdapter adapter;
    public BuscarPaisesFragment() {
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
        viewBinding = FragmentBuscarPaisesBinding.inflate(inflater, container, false);

        initComponents();

        return viewBinding.getRoot();
    }

    private void initComponents(){
        // inicializando la api
        restCointriesApi = RestCountriClient.get_instance().create(RestCointriesApi.class);
        compositeDisposable = new CompositeDisposable();

        viewBinding.buscarPaisTilBuscar.setEndIconOnClickListener(this::onEndIconClick);
        viewBinding.buscarPaisTilBuscar.getEditText().setOnEditorActionListener(this::onTilBuscarEnter);

        // Configurando el recycler view
        viewBinding.buscarPaisRVPaises.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Configurar el Fab
        viewBinding.porContinenteFabClear.setOnClickListener((view)->{
            viewBinding.buscarPaisTilBuscar.getEditText().setText("");
            setRecycleViewAdapter(new ArrayList<>());
        });
    }

    private boolean onTilBuscarEnter(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_SEARCH){
            onEndIconClick(textView);
            return true;
        }
        return false;
    }

    private void onEndIconClick(View view) {
        // Cerrar el teclado virtual de android;
        InputMethodManager imp = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imp.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String input = viewBinding.buscarPaisTilBuscar.getEditText().getText().toString();
        //Toast.makeText(getContext(), "Input: "+input, Toast.LENGTH_SHORT).show();
        fetchCountriesData(input);

    }

    private void fetchCountriesData(String nombre) {
        compositeDisposable.add(
                restCointriesApi.searchCountriesByName(nombre)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setRecycleViewAdapter, this::error)
        );

    }

    private void error(Throwable throwable) {
        Toast.makeText(getContext(), "No se encontraron coincidencias con ese termino",
                Toast.LENGTH_SHORT).show();
        setRecycleViewAdapter(new ArrayList<>());
    }

    private void setRecycleViewAdapter(List<Country> countries) {
        adapter = new PaisAdapter(getContext(), countries);
        viewBinding.buscarPaisRVPaises.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}