/**
 * 180
 */
package com.example.homework1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.homework1.api.CoinIoApi;
import com.example.homework1.ohlcdraw.OhlcHistoryActivity;
import com.example.homework1.ohldata.OhlcData;

public class MainActivity extends AppCompatActivity {
    public static final int MESSAGE_UPDATE_ROW = 1;

    protected void ShowOhlcChart(OhlcData d) {
        /**
         Change activity to OhlcChart
         * */
        Intent intent = new Intent(MainActivity.this, OhlcHistoryActivity.class);
        Bundle b = new Bundle();
        intent.putExtra("ohlc_data", d); //Your id
        startActivity(intent);
        // finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Next lines are for network permisions. don't delete it
        */
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.INTERNET},
                1);


        OhlcData d = CoinIoApi.getOhlcData("BTC", TimeStart.MONTH);
        ShowOhlcChart(d);
        ShowOhlcChart(d);
    }
}