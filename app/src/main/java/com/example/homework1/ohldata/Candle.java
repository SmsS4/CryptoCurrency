package com.example.homework1.ohldata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Candle implements Serializable{
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
    private Float priceOpen;
    @SerializedName("price_high")
    @Expose
    private Float priceHigh;
    @SerializedName("price_low")
    @Expose
    private Float priceLow;
    @SerializedName("price_close")
    @Expose
    private Float priceClose;
    @SerializedName("volume_traded")
    @Expose
    private Float volumeTraded;
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

    public Float getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(Float priceOpen) {
        this.priceOpen = priceOpen;
    }

    public Float getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(Float priceHigh) {
        this.priceHigh = priceHigh;
    }

    public Float getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(Float priceLow) {
        this.priceLow = priceLow;
    }

    public Float getPriceClose() {
        return priceClose;
    }

    public void setPriceClose(Float priceClose) {
        this.priceClose = priceClose;
    }

    public Float getVolumeTraded() {
        return volumeTraded;
    }

    public void setVolumeTraded(Float volumeTraded) {
        this.volumeTraded = volumeTraded;
    }

    public Integer getTradesCount() {
        return tradesCount;
    }

    public void setTradesCount(Integer tradesCount) {
        this.tradesCount = tradesCount;
    }

}

