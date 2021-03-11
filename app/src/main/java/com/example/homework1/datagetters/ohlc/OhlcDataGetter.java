package com.example.homework1.datagetters.ohlc;

import android.os.Handler;
import android.os.Message;

import com.example.homework1.TimeStart;
import com.example.homework1.api.CoinIoApi;
import com.example.homework1.ohlcdraw.OhlcHistoryActivity;
import com.example.homework1.ohldata.OhlcData;

public class OhlcDataGetter implements Runnable {
    private final Handler handler;
    private final String coin;
    private final TimeStart length;

    public OhlcDataGetter(Handler handler, String coin, TimeStart timeStart) {
        this.handler = handler;
        this.coin = coin;
        this.length = timeStart;
    }

    public void run() {
        OhlcData ohlcData = CoinIoApi.getOhlcData(coin, length);
        Message msg = new Message();
        msg.what = OhlcHistoryActivity.MESSAGE_TRANSFER_OHLC_DATA;
        msg.obj = new UpdateOhlcDataObj(ohlcData, true);
        handler.handleMessage(msg);
    }
}
