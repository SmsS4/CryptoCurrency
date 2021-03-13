package com.example.homework1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework1.coinslist.CoinsListAdapter;
import com.example.homework1.coinslist.RecyclerItemClickListener;
import com.example.homework1.cryptodata.CryptoData;
import com.example.homework1.datagetters.cryptolist.CoinsListGetter;
import com.example.homework1.datagetters.cryptolist.UpdateCoinsListObj;
import com.example.homework1.ohlcdraw.OhlcHistoryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final int MESSAGE_UPDATE_ROW = 1;
    public static final int MESSAGE_NETWORK_ERROR = 2;

    ThreadPoolExecutor threadPoolExecutor;
    private List<CryptoData> coinsList;
    private RecyclerView coinsListView;
    private CoinsListAdapter coinsListAdapter;
    private boolean requestPending = false;
    private Handler handler;
    private ProgressBar progressBar;

    protected void showOhlcChart(String coin) {
        /*
         Change activity to OhlcChart
         */
        Intent intent = new Intent(MainActivity.this, OhlcHistoryActivity.class);
        intent.putExtra("id", coin);
        startActivity(intent);
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

        progressBar = findViewById(R.id.progressBar);

        initCoinsListView();
        initListeners();
        initMultithreadingUtils();

        loadMoreCoins(10);
    }

    private void initMultithreadingUtils() {
        handler = new UpdateListHandler(Looper.getMainLooper(), this);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, queue
        );
    }

    private void initCoinsListView() {
        coinsList = new ArrayList<>();
        coinsListView = findViewById(R.id.coins_list_recycler_view);
        RecyclerView.LayoutManager coinsListLayoutManager = new LinearLayoutManager(this);
        coinsListView.setLayoutManager(coinsListLayoutManager);

        coinsListAdapter = new CoinsListAdapter(coinsList, getFilesDir());
        coinsListView.setAdapter(coinsListAdapter);
    }

    private void initListeners() {
        FloatingActionButton loadMoreBtn = findViewById(R.id.extend_list_button);
        loadMoreBtn.setOnClickListener(v -> {
            if (!requestPending)
                loadMoreCoins(10);
        });

        FloatingActionButton refreshListBtn = findViewById(R.id.refresh_list_button);
        refreshListBtn.setOnClickListener(v -> {
            if (!requestPending)
                reloadCoins();
        });

        coinsListView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, coinsListView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                showOhlcChart(coinsList.get(position).getSymbol());
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                showOhlcChart(coinsList.get(position).getSymbol());
                            }
                        }
                )
        );
    }

    private void showNetworkErrorMessage() {
        final String errorMsg = "Network error, check connection!";
        Toast toast = Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void reloadCoins() {
        requestStart();
        int start = 1;
        int limit = coinsList.size();
        threadPoolExecutor.execute(new CoinsListGetter(handler, start, limit, getFilesDir()));
    }

    private void loadMoreCoins(int limit) {
        requestStart();
        int start = coinsList.size() + 1;
        threadPoolExecutor.execute(new CoinsListGetter(handler, start, limit, getFilesDir()));
    }

    private void requestStart() {
        requestPending = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    private void requestDone(boolean success) {
        if (!success)
            showNetworkErrorMessage();
        progressBar.setVisibility(View.INVISIBLE);
        requestPending = false;
    }

    private void updateList(int startIdx, List<CryptoData> coins) {
        for (int idx = 0; idx < coins.size(); idx++) {
            int currentIdx = startIdx + idx;
            if (currentIdx == coinsList.size()) {
                coinsList.add(coins.get(idx));
                coinsListAdapter.notifyItemInserted(currentIdx);
            } else {
                coinsList.set(currentIdx, coins.get(idx));
                coinsListAdapter.notifyItemChanged(currentIdx);
            }
        }
    }

    private static class UpdateListHandler extends Handler {
        private final WeakReference<MainActivity> target;

        UpdateListHandler(Looper looper, MainActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.what == MESSAGE_NETWORK_ERROR) {
                target.requestDone(false);
            } else {
                UpdateCoinsListObj obj = (UpdateCoinsListObj) msg.obj;
                target.updateList(obj.getStart() - 1, obj.getCoins());
                if (obj.isFresh())
                    target.requestDone(true);
            }
        }
    }
}