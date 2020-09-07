package com.Main.datafetch;

import com.Main.datafetch.model.CountryData;
import com.Main.datafetch.model.GlobalData;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CovidAPI {
    @GET("https://coronavirus-19-api.herokuapp.com/all")
    Call<GlobalData> getGlobalData();

    @GET("https://coronavirus-19-api.herokuapp.com/countries/{countryName}")
    Call<CountryData> getcountryData(@Path(value = "countryName") String countryName);
}
