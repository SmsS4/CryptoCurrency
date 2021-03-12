package com.example.homework1.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.homework1.cryptodata.CryptoData;
import com.example.homework1.cryptodata.CryptoDataList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CoinMarketCapApi {
    private static Bitmap getLogo(int id) {
        /*
         Get logo from id
         */

        ApiService service = RetrofitInstance.getApi().create(ApiService.class);
        Call<ResponseBody> call = service.getLogo(
                "https://s2.coinmarketcap.com/static/img/coins/64x64/"
                        + id
                        + ".png"
        );

        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    // display the image data in a ImageView or save it
                    return BitmapFactory.decodeStream(response.body().byteStream());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<CryptoData> getData(int start, int limit) throws Exception {
        /*
         Get data of limit coin from start
         1 <= start
         */
        ApiService service = RetrofitInstance.getApi().create(ApiService.class);
        Call<CryptoDataList> call = service.getCryptoData(start, limit);
        Response<CryptoDataList> response = call.execute();
        CryptoDataList apiResponse = response.body();
        for (CryptoData cd : apiResponse.getData()) {
            cd.setLogo(getLogo(cd.getId()));
        }
        return apiResponse.getData();
    }
}
