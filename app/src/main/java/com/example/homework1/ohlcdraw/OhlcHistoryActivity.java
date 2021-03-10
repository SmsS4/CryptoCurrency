/**
 * 120
 */
package com.example.homework1.ohlcdraw;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework1.R;
import com.example.homework1.ohldata.Candle;
import com.example.homework1.ohldata.OhlcData;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;

public class OhlcHistoryActivity extends AppCompatActivity {
    /**
     * Activity to show ohlc chart
     */
    private TextView textView;
    private OnTouchOhlc onTouchOhlc;
    private CandleStickChart candleStickChart;
    private OhlcData ohlcData;

    private void setFields() {
        textView = findViewById(R.id.textView);
        onTouchOhlc = new OnTouchOhlc(textView);
        candleStickChart = findViewById(R.id.candle_stick_chart);
    }

    private void setBackButton() {
        /// set button to finish activity
        Button back = findViewById(R.id.back_button_ohlc);
        back.setOnClickListener(v -> finish());
    }

    private void setData() {
        /// get data as ohlc_data param
        Intent i = getIntent();
        ohlcData = (OhlcData) i.getSerializableExtra("ohlc_data");
    }

    private ArrayList<CandleEntry> getCandles() {
        ArrayList<CandleEntry> yValsCandleStick = new ArrayList<CandleEntry>();
        int idx = 0;
        for (Candle candle : ohlcData.getCandles()) {
            yValsCandleStick.add(new CryptoCandleEntry(idx, candle.getPriceHigh(), candle.getPriceLow(), candle.getPriceOpen(), candle.getPriceClose()));
            idx += 1;
        }
        return yValsCandleStick;
    }

    private void setCandleStickChartConfig() {
        candleStickChart.setHighlightPerDragEnabled(true);
        candleStickChart.setDrawBorders(true);
        candleStickChart.setTouchEnabled(true);
        candleStickChart.setBorderColor(Color.rgb(221, 221, 221));
        candleStickChart.setNoDataText("There is no candle");
        candleStickChart.setMarker(onTouchOhlc);
        candleStickChart.requestDisallowInterceptTouchEvent(true);
        candleStickChart.getLegend().setEnabled(false);
    }

    private void setAxisConfig() {
        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setDrawGridLines(false);// disable x axis grid lines
        xAxis.setDrawLabels(false);
        rightAxis.setTextColor(Color.BLACK);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);
    }

    private void setCandleDataSet(ArrayList<CandleEntry> candles) {
        CandleDataSet set1 = new CandleDataSet(candles, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.rgb(192, 192, 192));
        set1.setShadowWidth(1.2f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.GREEN);
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.BLUE);
        set1.setDrawValues(false);
        candleStickChart.setData(new CandleData(set1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohlc_history_aactivity);
        setFields();
        setData();
        setBackButton();
        setCandleStickChartConfig();
        setAxisConfig();
        setCandleDataSet(getCandles());
        candleStickChart.invalidate();
    }
}