package com.example.homework1.api;

import com.example.homework1.ohldata.TimeStart;
import com.example.homework1.exceptions.TooManyRequests;
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
            cal.add(Calendar.DATE, -29);
        else
            cal.add(Calendar.DATE, -6);
        return dateFormat.format(cal.getTime());
    }

    static private final String TIME_FRAME = "1DAY";

    public static OhlcData getOhlcData(String id, TimeStart timeStart) throws Exception {
        /*
         id is CryptoData.symbol
         */
        ApiService service = RetrofitInstance.getApi().create(ApiService.class);
        Call<List<Candle>> call = service.getOHLCData(
                id,
                TIME_FRAME,
                getDate(timeStart) + "T00:00:00"
        );
        Response<List<Candle>> response = call.execute();
        List<Candle> apiResponse = response.body();
        if (response.code() != 200) { //429
            System.out.println(response.code());
            throw new TooManyRequests();
        }
        System.out.println("result is ready");
        return new OhlcData(apiResponse);
    }
}
