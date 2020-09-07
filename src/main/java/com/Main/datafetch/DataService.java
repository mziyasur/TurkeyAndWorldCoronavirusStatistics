package com.Main.datafetch;

import com.Main.datafetch.model.CountryData;
import com.Main.datafetch.model.CovidDataModel;
import com.Main.datafetch.model.GlobalData;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;

public class DataService {

    public CovidDataModel getData(String countryName){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://coronavirus-19-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidAPI covidAPI = retrofit.create(CovidAPI.class);

        CompletableFuture<GlobalData> callbackglobal = new CompletableFuture<>();

        covidAPI.getGlobalData()
                .enqueue(new Callback<GlobalData>() {
                             @Override
                             public void onResponse(Call<GlobalData> call, Response<GlobalData> response) {
                                 callbackglobal.complete(response.body());
                             }


                             @Override
                             public void onFailure(Call<GlobalData> call, Throwable t) {
                                 callbackglobal.completeExceptionally(t);
                             }
                         });

        CompletableFuture<CountryData> callbackcountry = new CompletableFuture<>();

        covidAPI.getcountryData(countryName)
                .enqueue(new Callback<CountryData>() {
                    @Override
                    public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                        callbackcountry.complete(response.body());
                    }

                    @Override
                    public void onFailure(Call<CountryData> call, Throwable t) {
                        callbackcountry.completeExceptionally(t);
                    }
                });

        GlobalData globalData = callbackglobal.join();
        CountryData countryData = callbackcountry.join();

    return new CovidDataModel(globalData,countryData);
    }
}
