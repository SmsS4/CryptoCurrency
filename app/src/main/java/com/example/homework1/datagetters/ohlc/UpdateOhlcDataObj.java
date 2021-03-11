package com.example.homework1.datagetters.ohlc;

import com.example.homework1.ohldata.OhlcData;

public class UpdateOhlcDataObj {
    private final OhlcData data;
    private final boolean fresh;

    public UpdateOhlcDataObj(OhlcData data, boolean fresh) {
        this.data = data;
        this.fresh = fresh;
    }

    public OhlcData getData() {
        return data;
    }

    public boolean isFresh() {
        return fresh;
    }
}
