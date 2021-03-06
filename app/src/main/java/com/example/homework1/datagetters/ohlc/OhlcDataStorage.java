package com.example.homework1.datagetters.ohlc;

import android.util.Log;

import com.example.homework1.ohldata.TimeStart;
import com.example.homework1.ohldata.OhlcData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class OhlcDataStorage {
    private static final String OHLC_DIR_NAME = "ohlc";
    private static final long FRESH_TIME_THRESHOLD = (long) (0.25 * 60 * 60 * 1000);
    private static final Object storageLock = new Object();

    private final File directory;

    public OhlcDataStorage(File root) {
        directory = new File(root, OHLC_DIR_NAME);
        synchronized (storageLock) {
            if (!Arrays.asList(root.list()).contains(OHLC_DIR_NAME))
                if (!directory.mkdir())
                    Log.e("Storage", "Could not create OHLC storage directory.");
        }
    }

    private String getCoinFileAddress(String coin, TimeStart length) {
        return String.format("%s/%s_%s.json", directory.getAbsolutePath(), coin, length);
    }

    public void storeData(String coin, TimeStart length, OhlcData data) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        String coinFileAddress = getCoinFileAddress(coin, length);
        synchronized (storageLock) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(coinFileAddress));
                writer.write(jsonData);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasData(String coin, TimeStart length) {
        File file = new File(getCoinFileAddress(coin, length));
        return file.exists();
    }

    public boolean isFresh(String coin, TimeStart length) {
        File file = new File(getCoinFileAddress(coin, length));
        Date now = new Date();
        return now.getTime() - file.lastModified() <= FRESH_TIME_THRESHOLD;
    }

    public OhlcData loadData(String coin, TimeStart length) {
        synchronized (storageLock) {
            if (!hasData(coin, length))
                return null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(getCoinFileAddress(coin, length)));
                String jsonData = reader.readLine();
                Gson gson = new Gson();
                return gson.fromJson(jsonData, OhlcData.class);
            } catch (FileNotFoundException e) {
                // Control never reach here
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
