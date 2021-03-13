package com.example.homework1.ohlcdraw;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework1.R;
import com.example.homework1.TimeStart;
import com.example.homework1.datagetters.ohlc.OhlcDataGetter;
import com.example.homework1.datagetters.ohlc.UpdateOhlcDataObj;
import com.example.homework1.ohldata.Candle;
import com.example.homework1.ohldata.OhlcData;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OhlcHistoryActivity extends AppCompatActivity {
    public static final int MESSAGE_TRANSFER_OHLC_DATA = 1;
    public static final int MESSAGE_NETWORK_ERROR = 2;

    /*
     * Activity to show ohlc chart
     */
    private TextView textView;
    private OnTouchOhlc onTouchOhlc;
    private CandleStickChart candleStickChart;
    private LinkedBlockingQueue<Runnable> queue;
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler handler;
    private String id;
    private ProgressBar progressBar;
    private TimeStart timeStartToShow;
    private int requests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohlc_history_activity);
        setFields();
        setButtons();
        setCandleStickChartConfig();
        setAxisConfig();
    }

    private void requestDone() {
        requests -= 1;
        if (requests == 0)
            progressBar.setVisibility(View.INVISIBLE);
        else
            progressBar.setVisibility(View.VISIBLE);
    }

    private void setFields() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        id = getIntent().getStringExtra("id");
        textView = findViewById(R.id.textView);
        onTouchOhlc = new OnTouchOhlc(textView);
        candleStickChart = findViewById(R.id.candle_stick_chart);
        queue = new LinkedBlockingQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, queue
        );
        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case OhlcHistoryActivity.MESSAGE_TRANSFER_OHLC_DATA:
                        UpdateOhlcDataObj updateOhlcDataObj = ((UpdateOhlcDataObj) msg.obj);
                        OhlcData ohlcData = updateOhlcDataObj.getData();

                        if (updateOhlcDataObj.isFresh()) {
                            requestDone();
                        }

                        if (updateOhlcDataObj.getLength() != timeStartToShow)
                            /// ignore old data
                            return;
                        setCandleDataSet(getCandles(ohlcData));
                        break;

                }
            }
        };

    }

    private void getData(TimeStart timeStart) {
        timeStartToShow = timeStart;
        requests += 1;
        progressBar.setVisibility(View.VISIBLE);
        threadPoolExecutor.execute(new OhlcDataGetter(handler, id, timeStart, getFilesDir()));
    }

    private void setButtons() {
        /// set button to finish activity
        findViewById(R.id.back_button_ohlc).setOnClickListener(v -> finish());

        findViewById(R.id.days_7).setOnClickListener(v -> getData(TimeStart.WEEK));
        findViewById(R.id.days_30).setOnClickListener(v -> getData(TimeStart.MONTH));

    }

    private ArrayList<CandleEntry> getCandles(OhlcData ohlcData) {
        ArrayList<CandleEntry> yValsCandleStick = new ArrayList<>();
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
        candleStickChart.setNoDataText("Press WEEK or MONTH to show data");
        candleStickChart.setNoDataTextColor(Color.RED);
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
        candleStickChart.invalidate();
    }
}