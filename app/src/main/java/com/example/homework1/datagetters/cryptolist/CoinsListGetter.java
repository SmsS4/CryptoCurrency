package com.example.homework1.datagetters.cryptolist;

import android.os.Handler;
import android.os.Message;

import com.example.homework1.MainActivity;
import com.example.homework1.api.CoinMarketCapApi;
import com.example.homework1.cryptodata.CryptoData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoinsListGetter implements Runnable {
    private final Handler handler;
    private final int startIdx, limit;
    private final CoinsListStorage storage;
    private final CoinAvatarsStorage avatarsStorage;

    CoinsListGetter(Handler handler, int start, int limit, File filesDir) {
        this.handler = handler;
        this.startIdx = start;
        this.limit = limit;
        this.storage = new CoinsListStorage(filesDir);
        this.avatarsStorage = new CoinAvatarsStorage(filesDir);
    }

    @Override
    public void run() {
        List<CryptoData> cryptoList = readFromStorage();
        if (!cryptoList.isEmpty())
            sendMessage(cryptoList, false);
        try {
            cryptoList = CoinMarketCapApi.getData(this.startIdx, this.limit);
            for (CryptoData crypto : cryptoList)
                avatarsStorage.loadAvatar(crypto.getId());
            sendMessage(cryptoList, true);
            writeToStorage(cryptoList);
        } catch (Exception e) {
            /// network/too many requests error
        }
    }

    private List<CryptoData> readFromStorage() {
        List<CryptoData> cryptoList = new ArrayList<>();
        for (int idx = 0; idx < limit; idx++) {
            CryptoData idxRow = storage.loadData(startIdx + idx);
            if (idxRow != null)
                cryptoList.add(idxRow);
        }
        return cryptoList;
    }

    private void writeToStorage(List<CryptoData> cryptoList) {
        for (int idx = 0; idx < cryptoList.size(); idx++)
            storage.storeData(startIdx + idx, cryptoList.get(idx));
    }

    private void sendMessage(List<CryptoData> data, boolean fresh) {
        Message msg = new Message();
        msg.what = MainActivity.MESSAGE_UPDATE_ROW;
        msg.obj = new UpdateCoinsListObj(data, this.startIdx, this.limit, true);
        handler.handleMessage(msg);
    }
}
