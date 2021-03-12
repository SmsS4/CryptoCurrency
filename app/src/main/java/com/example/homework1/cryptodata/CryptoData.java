package com.example.homework1.cryptodata;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CryptoData {
    /**
     * Response model of CoinMarketCapApi
     * Getter Methods:
     * + getPrice: price base on USD
     * + getPercentChange1h
     * + getPercentChange24h
     * + getPercentChange7d
     * + getName
     * + getSymbol
     * + getLastUpdated: returns time when data is updated
     * + getLogo: Bitmap logo of crpyto
     */
    private Bitmap logo;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("quote")
    @Expose
    private Quote quote;

    public Bitmap getLogo() {
        return this.logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Double getPrice() {
        return getQuote().getUSD().getPrice();
    }

    public Double getPercentChange1h() {
        return getQuote().getUSD().getPercentChange1h();
    }

    public Double getPercentChange24h() {
        return getQuote().getUSD().getPercentChange24h();
    }

    public Double getPercentChange7d() {
        return getQuote().getUSD().getPercentChange7d();
    }
}
