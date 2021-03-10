package com.example.homework1.ohlcdraw;

import com.github.mikephil.charting.data.CandleEntry;

import java.text.MessageFormat;

public class CryptoCandleEntry extends CandleEntry {
    /**
     * Add toString method
     */
    public CryptoCandleEntry(float x, float shadowH, float shadowL, float open, float close) {
        super(x, shadowH, shadowL, open, close);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Low: {0}\nHigh: {1}\nOpen: {2}\nClose: {3}",
                this.getLow(), this.getHigh(), this.getOpen(), this.getClose()
        );
    }
}
