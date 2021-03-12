package com.example.homework1.api;


import com.example.homework1.cryptodata.CryptoDataList;
import com.example.homework1.ohldata.Candle;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    @Headers(
            {
                    "Accept: application/json",
                    "X-CMC_PRO_API_KEY: 5a25bbab-0deb-4f4a-811a-7fd5d5d118e8"
            }
    )
    @GET("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
    Call<CryptoDataList> getCryptoData(@Query("start") int start, @Query("limit") int limit);


    @Streaming
    @GET
    Call<ResponseBody> getLogo(@Url String url);

    @Headers(
            {
                    "X-CoinAPI-Key: F0DD8307-88C9-4463-922D-D1FB524B6AA9"
            }
    )
    @GET("https://rest.coinapi.io/v1/ohlcv/{id}/USD/history")
    Call<List<Candle>> getOHLCData(
            @Path("id") String id,
            @Query("period_id") String periodId, // 1DAY
            @Query("time_start") String timeStart // 2016-01-01T00:00:00
    );
}
