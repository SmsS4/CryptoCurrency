package com.example.homework1.ohlcdraw;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework1.MainActivity;
import com.example.homework1.R;
import com.example.homework1.datagetters.cryptolist.UpdateCoinsListObj;
import com.example.homework1.ohldata.TimeStart;
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
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OhlcHistoryActivity extends AppCompatActivity {
    public static final int MESSAGE_TRANSFER_OHLC_DATA = 1;
    public static final int MESSAGE_NETWORK_ERROR = 2;
    public static final int MESSAGE_TOO_MANY_REQUESTS = 3;

    public OnTouchOhlc getOnTouchOhlc() {
        return onTouchOhlc;
    }

    public void setOnTouchOhlc(OnTouchOhlc onTouchOhlc) {
        this.onTouchOhlc = onTouchOhlc;
    }

    public CandleStickChart getCandleStickChart() {
        return candleStickChart;
    }

    public void setCandleStickChart(CandleStickChart candleStickChart) {
        this.candleStickChart = candleStickChart;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setTimeStartToShow(TimeStart timeStartToShow) {
        this.timeStartToShow = timeStartToShow;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    private OnTouchOhlc onTouchOhlc;
    private CandleStickChart candleStickChart;
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler handler;
    private String id;
    private ProgressBar progressBar;
    private TimeStart timeStartToShow;
    private int requests = 0;

    public TimeStart getTimeStartToShow() {
        return timeStartToShow;
    }

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
        if (getRequests() == 0)
            getProgressBar().setVisibility(View.INVISIBLE);
        else
            getProgressBar().setVisibility(View.VISIBLE);
    }

    private static class UpdateListHandler extends Handler {
        private final WeakReference<OhlcHistoryActivity> target;

        UpdateListHandler(Looper looper, OhlcHistoryActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            OhlcHistoryActivity target = this.target.get();
            switch (msg.what) {
                case OhlcHistoryActivity.MESSAGE_TRANSFER_OHLC_DATA:
                    UpdateOhlcDataObj updateOhlcDataObj = ((UpdateOhlcDataObj) msg.obj);
                    OhlcData ohlcData = updateOhlcDataObj.getData();
                    if (updateOhlcDataObj.isFresh()) {
                        target.requestDone();
                    }
                    if (updateOhlcDataObj.getLength() != target.getTimeStartToShow())
                        /// ignore old data
                        return;
                    target.setCandleDataSet(target.getCandles(ohlcData));
                    break;
                case OhlcHistoryActivity.MESSAGE_NETWORK_ERROR:
                    target.requestDone();
                    target.toast("Network error");
                    break;
                case OhlcHistoryActivity.MESSAGE_TOO_MANY_REQUESTS:
                    target.requestDone();
                    target.toast("Too many requests");
                    break;
            }
        }
    }

    private void setFields() {
        setProgressBar(findViewById(R.id.progressBar));
        getProgressBar().setVisibility(View.INVISIBLE);
        setId(getIntent().getStringExtra("id"));
        /*
         * Activity to show ohlc chart
         */
        TextView textView = findViewById(R.id.textView);
        setOnTouchOhlc(new OnTouchOhlc(textView));
        setCandleStickChart(findViewById(R.id.candle_stick_chart));
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        setThreadPoolExecutor(new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, queue
        ));
        setHandler(new OhlcHistoryActivity.UpdateListHandler(Looper.getMainLooper(), this));

    }

    private void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void getData(TimeStart timeStart) {
        setTimeStartToShow(timeStart);
        requests += 1;
        getProgressBar().setVisibility(View.VISIBLE);
        getThreadPoolExecutor().execute(new OhlcDataGetter(getHandler(), getId(), timeStart, getFilesDir()));
    }

    private void setButtons() {
        /// set button to finish activity
        findViewById(R.id.back_button_ohlc).setOnClickListener(v -> finish());

        findViewById(R.id.days_7).setOnClickListener(v -> getData(TimeStart.WEEK));
        findViewById(R.id.days_30).setOnClickListener(v -> getData(TimeStart.MONTH));

    }

    private ArrayList<CandleEntry> getCandles(OhlcData ohlcData) {
        ArrayList<CandleEntry> yValsCandleStick = new ArrayList<>();
        int idx = 1;
        for (Candle candle : ohlcData.getCandles()) {
            yValsCandleStick.add(new CryptoCandleEntry(idx, candle.getPriceHigh(), candle.getPriceLow(), candle.getPriceOpen(), candle.getPriceClose()));
            idx += 1;
        }
        return yValsCandleStick;
    }

    private void setCandleStickChartConfig() {
        CandleStickChart candleStickChart = getCandleStickChart();
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
        CandleStickChart candleStickChart = getCandleStickChart();
        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setDrawGridLines(false);// disable x axis grid lines
        rightAxis.setTextColor(Color.BLACK);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawLabels(true);
    }

    private void setCandleDataSet(ArrayList<CandleEntry> candles) {
        if (candles.isEmpty()) {
            toast("There is no candle to show :(");
            return;
        }
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
        CandleStickChart candleStickChart = getCandleStickChart();
        candleStickChart.setData(new CandleData(set1));
        candleStickChart.invalidate();
    }
}