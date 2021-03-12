package com.example.homework1.datagetters.cryptolist;

import android.os.Handler;
import android.os.Message;

import com.example.homework1.MainActivity;
import com.example.homework1.api.CoinMarketCapApi;
import com.example.homework1.cryptodata.CryptoData;

import java.util.List;

public class CoinsListGetter implements Runnable {
    private final Handler handler;
    private final int startIdx, limit;

    CoinsListGetter(int start, int limit, Handler handler) {
        this.handler = handler;
        this.startIdx = start;
        this.limit = limit;
    }

    @Override
    public void run() {
        try{
            List<CryptoData> cryptoList = CoinMarketCapApi.getData(this.startIdx, this.limit);
            Message msg = new Message();
            msg.what = MainActivity.MESSAGE_UPDATE_ROW;
            msg.obj = new UpdateCoinsListObj(cryptoList, this.startIdx, this.limit, true);
            handler.handleMessage(msg);
        }catch (Exception e){
            /// network error
        }
    }
}
