package com.example.homework1.datagetters.ohlc;

import com.example.homework1.ohldata.TimeStart;
import com.example.homework1.ohldata.OhlcData;

public class UpdateOhlcDataObj {
    private final String coin;
    private final TimeStart length;
    private final OhlcData data;
    private final boolean fresh;

    public UpdateOhlcDataObj(String coin, TimeStart length, OhlcData data, boolean fresh) {
        this.coin = coin;
        this.length = length;
        this.data = data;
        this.fresh = fresh;
    }

    public String getCoin() {
        return coin;
    }

    public TimeStart getLength() {
        return length;
    }

    public OhlcData getData() {
        return data;
    }

    public boolean isFresh() {
        return fresh;
    }
}
