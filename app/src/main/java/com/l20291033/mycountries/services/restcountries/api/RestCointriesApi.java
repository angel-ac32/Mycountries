package com.l20291033.mycountries.services.restcountries.api;

import com.l20291033.mycountries.services.restcountries.models.Country;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestCointriesApi {

    @GET("region/{region}")
    Observable<List<Country>> getCountriesByRegion(
            @Path("region") String continente,
            @Query("fields") String campos
    );

    @GET("name/{name}?fields=cca3,name,flag,flags,population,capital")
    Observable<List<Country>> searchCountriesByName(@Path("name")String name);

    @GET("alpha/{code}?fields=cca3,name,flag,flags,population,capital")
    Observable<Country> getCountryByCca3(@Path("code") String cca3);

}
