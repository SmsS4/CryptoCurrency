package com.example.homework1.api;



import com.example.homework1.cryptodata.CryptoDataList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @Headers(
            {
                    "Accept: application/json",
                    "X-CMC_PRO_API_KEY: 5a25bbab-0deb-4f4a-811a-7fd5d5d118e8"
            }
    )
    @GET("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
    Call<CryptoDataList> getCryptoData(@Query("start") int start, @Query("limit") int limit);

//    @Headers(
//            {
//                    "Accept: application/json",
//                    "X-CMC_PRO_API_KEY: 5a25bbab-0deb-4f4a-811a-7fd5d5d118e8"
//            }
//    )
//    @GET("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
//    Call<CryptoDataList> getCryptoData(@Query("start") int start, @Query("limit") int limit);
//
//    @Headers(
//            {
//                    "Accept: application/json",
//                    "X-CMC_PRO_API_KEY: 5a25bbab-0deb-4f4a-811a-7fd5d5d118e8"
//            }
//    )
//    @POST("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
//    @FormUrlEncoded
//    Call<CryptoDataList> getCryptoData(@Field("start") int start, @Field("limit") int limit);-
}
