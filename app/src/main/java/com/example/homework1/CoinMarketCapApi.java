package com.example.homework1;


import com.example.homework1.api.ApiService;
import com.example.homework1.api.RetrofitInstance;
import com.example.homework1.cryptodata.CryptoData;
import com.example.homework1.cryptodata.CryptoDataList;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinMarketCapApi {
    /** 30 */
    static List<CryptoData> getData(String name) {
        ApiService service = RetrofitInstance.getApi().create(ApiService.class);
        Call<CryptoDataList> call = service.getCryptoData(1, 10);
        try
        {
            Response<CryptoDataList> response = call.execute();
            CryptoDataList apiResponse = response.body();
            return apiResponse.getData();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new ArrayList<CryptoData>();

    }
}
