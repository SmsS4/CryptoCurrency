package com.example.homework1.ohldata;

import com.example.homework1.ohldata.Candle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OhlcData implements Serializable {
    /**
     * OHLC data model from CoinIoApi
     */
    List<Candle> candles;

    public OhlcData(List<Candle> candles) {
        if (candles == null)
            candles = new ArrayList<>();
        this.candles = candles;
    }

    public List<Candle> getCandles() {
        return this.candles;
    }
}
