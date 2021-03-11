package com.example.homework1.datagetters.cryptolist;

import com.example.homework1.cryptodata.CryptoData;

import java.util.List;

public class UpdateCoinsListObj {
    public List<CryptoData> coins;
    public int start, limit;

    public UpdateCoinsListObj(List<CryptoData> coins, int start, int limit) {
        this.coins = coins;
        this.start = start;
        this.limit = limit;
    }
}
