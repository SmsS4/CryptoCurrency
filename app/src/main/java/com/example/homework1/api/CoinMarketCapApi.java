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
    static private int LIMIT = 10;

    private static Bitmap getLogo(int id) {
        /**
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
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    return bmp;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    static List<CryptoData> getData(int start) {
        /**
         Get data of 10 coin from start
         1 <= start
         */
        ApiService service = RetrofitInstance.getApi().create(ApiService.class);
        Call<CryptoDataList> call = service.getCryptoData(start, CoinMarketCapApi.LIMIT);
        try {
            Response<CryptoDataList> response = call.execute();
            CryptoDataList apiResponse = response.body();
            for (CryptoData cd : apiResponse.getData()) {
                cd.setLogo(getLogo(cd.getId()));
            }
            return apiResponse.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<CryptoData>();

    }
}
