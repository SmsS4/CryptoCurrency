package com.example.homework1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit api ;

    public static Retrofit getApi() {
        if(api == null){
            api = new retrofit2.Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://localhost/")
                    .build();
        }
        return api;
    }

}
