package com.l20291033.mycountries.services.restcountries.client;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestCountriClient {
    private static final String BASE_URL = "https://restcountries.com/v3.1/";
    public static final String FIELDS_VALUES = "cca3,name,flag,flags,population,capital";

    public static Retrofit _instance;

    public static Retrofit get_instance(){
        if(_instance == null)
            _instance = new Retrofit.Builder()
                    .baseUrl(RestCountriClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return _instance;
    }

}
