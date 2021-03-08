package com.example.homework1.cryptodata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CryptoDataList {

    @SerializedName("data")
    @Expose
    private List<CryptoData> data;

    public List<CryptoData> getData() {
        return data;
    }

    public void setData(List<CryptoData> data) {
        this.data = data;
    }
}

