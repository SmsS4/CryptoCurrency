package com.example.homework1.datagetters.ohlc;

import com.example.homework1.ohldata.OhlcData;

public class UpdateOhlcDataObj {
    private OhlcData data;

    public UpdateOhlcDataObj(OhlcData data) {
        this.data = data;
    }

    public OhlcData getData() {
        return data;
    }

    public void setData(OhlcData data) {
        this.data = data;
    }
}
