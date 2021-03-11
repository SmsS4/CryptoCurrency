package com.example.homework1.datagetters.ohlc;

import android.os.Handler;
import android.os.Message;

import com.example.homework1.TimeStart;
import com.example.homework1.api.CoinIoApi;
import com.example.homework1.ohlcdraw.OhlcHistoryActivity;
import com.example.homework1.ohldata.OhlcData;

import java.io.File;

public class OhlcDataGetter implements Runnable {
    private final Handler handler;
    private final String coin;
    private final TimeStart length;
    private final OhlcDataStorage storage;

    /**
     * @param handler   the handler which the messages will be send to
     * @param coin      short name of the coin, e.g. "BTC"
     * @param timeStart WEEK or MONTH; better to set
     * @param filesDir  files directory of the storage, set to `filesDir()` in the activities
     */
    public OhlcDataGetter(Handler handler, String coin, TimeStart timeStart, File filesDir) {
        this.handler = handler;
        this.coin = coin;
        this.length = timeStart;
        this.storage = new OhlcDataStorage(filesDir);
    }

    /**
     * The main function of the thread
     */
    public void run() {
        OhlcData ohlcData = storage.loadData(coin, length);
        if (ohlcData != null)
            sendMessage(ohlcData, false);
        ohlcData = CoinIoApi.getOhlcData(coin, length);
        sendMessage(ohlcData, true);
        storage.storeData(coin, length, ohlcData);
    }

    /**
     * Sends an update to the handler
     *
     * @param data  the data that will be sent
     * @param fresh shows whether the data's been cached or it's received from the network
     */
    private void sendMessage(OhlcData data, boolean fresh) {
        Message msg = new Message();
        msg.what = OhlcHistoryActivity.MESSAGE_TRANSFER_OHLC_DATA;
        msg.obj = new UpdateOhlcDataObj(coin, length, data, fresh);
        handler.handleMessage(msg);
    }
}
