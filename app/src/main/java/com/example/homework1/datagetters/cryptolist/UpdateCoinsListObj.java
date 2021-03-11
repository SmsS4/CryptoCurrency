package com.example.homework1.datagetters.cryptolist;

import com.example.homework1.cryptodata.CryptoData;

import java.util.List;

public class UpdateCoinsListObj {
    private List<CryptoData> coins;
    private int start, limit;

    public UpdateCoinsListObj(List<CryptoData> coins, int start, int limit) {
        this.coins = coins;
        this.start = start;
        this.limit = limit;
    }

    public List<CryptoData> getCoins() {
        return coins;
    }

    public void setCoins(List<CryptoData> coins) {
        this.coins = coins;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
