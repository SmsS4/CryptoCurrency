package com.example.homework1.api;

import com.example.homework1.TimeStart;
import com.example.homework1.api.ApiService;
import com.example.homework1.api.RetrofitInstance;
import com.example.homework1.ohldata.Candle;
import com.example.homework1.ohldata.OhlcData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class CoinIoApi {

    static private String getDate(TimeStart timeStart) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (timeStart == TimeStart.MONTH)
            cal.add(Calendar.DATE, -30);
        else
            cal.add(Calendar.DATE, -7);
        return dateFormat.format(cal.getTime());
    }

    static private String TIME_FRAME = "1DAY";

    public static OhlcData getOhlcData(String id, TimeStart timeStart) {
        /**
         id is CryptoData.symbol
         */

        ApiService service = RetrofitInstance.getApi().create(ApiService.class);
        Call<List<Candle>> call = service.getOHLCData(
                id,
                TIME_FRAME,
                getDate(timeStart) + "T00:00:00"
        );
        try {
            Response<List<Candle>> response = call.execute();
            List<Candle> apiResponse = response.body();
            return new OhlcData(apiResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
