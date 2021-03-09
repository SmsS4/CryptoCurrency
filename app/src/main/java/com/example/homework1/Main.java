package com.example.homework1;

import com.example.homework1.api.CoinIoApi;
import com.example.homework1.ohldata.OhlcData;

public class Main {
    /**this is just for test*/
    public static void main(String[] args) {
        // List<CryptoData> cd = CoinMarketCapApi.getData(1);
        // System.out.println(cd.size());
        // System.out.println(cd.get(0).getName());

        OhlcData d = CoinIoApi.getOhlcData("BTC", TimeStart.WEEK);
        System.out.println(d.getCandles().size());
        System.out.println(d.getCandles().get(0).getPriceClose());
    }
}
