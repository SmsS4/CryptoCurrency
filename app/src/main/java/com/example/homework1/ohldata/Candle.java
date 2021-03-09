package com.example.homework1.ohldata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Candle {
    /**
     * You probably need only price_open, price_close, price_high, price_low
     */
    @SerializedName("time_period_start")
    @Expose
    private String timePeriodStart;
    @SerializedName("time_period_end")
    @Expose
    private String timePeriodEnd;
    @SerializedName("time_open")
    @Expose
    private String timeOpen;
    @SerializedName("time_close")
    @Expose
    private String timeClose;
    @SerializedName("price_open")
    @Expose
    private Double priceOpen;
    @SerializedName("price_high")
    @Expose
    private Double priceHigh;
    @SerializedName("price_low")
    @Expose
    private Double priceLow;
    @SerializedName("price_close")
    @Expose
    private Double priceClose;
    @SerializedName("volume_traded")
    @Expose
    private Double volumeTraded;
    @SerializedName("trades_count")
    @Expose
    private Integer tradesCount;

    public String getTimePeriodStart() {
        return timePeriodStart;
    }

    public void setTimePeriodStart(String timePeriodStart) {
        this.timePeriodStart = timePeriodStart;
    }

    public String getTimePeriodEnd() {
        return timePeriodEnd;
    }

    public void setTimePeriodEnd(String timePeriodEnd) {
        this.timePeriodEnd = timePeriodEnd;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public Double getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(Double priceOpen) {
        this.priceOpen = priceOpen;
    }

    public Double getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(Double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public Double getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(Double priceLow) {
        this.priceLow = priceLow;
    }

    public Double getPriceClose() {
        return priceClose;
    }

    public void setPriceClose(Double priceClose) {
        this.priceClose = priceClose;
    }

    public Double getVolumeTraded() {
        return volumeTraded;
    }

    public void setVolumeTraded(Double volumeTraded) {
        this.volumeTraded = volumeTraded;
    }

    public Integer getTradesCount() {
        return tradesCount;
    }

    public void setTradesCount(Integer tradesCount) {
        this.tradesCount = tradesCount;
    }

}

