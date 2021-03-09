package com.example.homework1.ohldata;

import com.example.homework1.ohldata.Candle;

import java.util.List;

public class OhlcData {
    List<Candle> candles;

    public OhlcData(List<Candle> candles) {
        this.candles =candles;
    }

    public List<Candle> getCandles() {
        return this.candles;
    }
}
