package com.example.homework1.datagetters.cryptolist;

import com.example.homework1.cryptodata.CryptoData;

import java.util.List;

public class UpdateCoinsListObj {
    private final List<CryptoData> coins;
    private final int start, limit;
    private final boolean fresh;

    public UpdateCoinsListObj(List<CryptoData> coins, int start, int limit, boolean fresh) {
        this.coins = coins;
        this.start = start;
        this.limit = limit;
        this.fresh = fresh;
    }

    public List<CryptoData> getCoins() {
        return coins;
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isFresh() {
        return fresh;
    }
}
