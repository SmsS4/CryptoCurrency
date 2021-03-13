package com.example.homework1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework1.api.CoinMarketCapApi;
import com.example.homework1.cryptodata.CryptoData;
import com.example.homework1.ohlcdraw.OhlcHistoryActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int MESSAGE_UPDATE_ROW = 1;
    public static final int MESSAGE_NETWORK_ERROR = 2;

    private List<CryptoData> coinsList;
    private RecyclerView coinsListView;
    private RecyclerView.LayoutManager coinsListLayoutManager;
    private CoinsListAdapter coinsListAdapter;

    protected void ShowOhlcChart(String coin) {
        /*
         Change activity to OhlcChart
         */
        Intent intent = new Intent(MainActivity.this, OhlcHistoryActivity.class);
        intent.putExtra("id", coin);
        startActivity(intent);
        // finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Next lines are for network permissions. don't delete it
        */
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.INTERNET},
                1);

        coinsList = new ArrayList<>();
        try {
            coinsList = CoinMarketCapApi.getData(1, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initCoinsListView();
    }

    private void initCoinsListView() {
        coinsListView = findViewById(R.id.coins_list_recycler_view);
        coinsListLayoutManager = new LinearLayoutManager(this);
        coinsListView.setLayoutManager(coinsListLayoutManager);

        coinsListAdapter = new CoinsListAdapter(coinsList, getFilesDir());
        coinsListView.setAdapter(coinsListAdapter);

        coinsListView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, coinsListView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                ShowOhlcChart(coinsList.get(position).getSymbol());
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                ShowOhlcChart(coinsList.get(position).getSymbol());
                            }
                        }
                )
        );
    }
}