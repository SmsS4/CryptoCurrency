package com.example.homework1.datagetters.ohlc;

import android.util.Log;

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

public class OhlcDataStorage {
    private static final String OHLC_DIR_NAME = "ohlc";
    private static final Object storageLock = new Object();

    private final File directory;

    public OhlcDataStorage(File root) {
        directory = new File(root, OHLC_DIR_NAME);
        if (!Arrays.asList(root.list()).contains(OHLC_DIR_NAME))
            if (!directory.mkdir())
                Log.e("Storage", "Could not create OHLC storage directory.");
    }

    private String getCoinFileAddress(String coin) {
        return String.format("%s/%s.json", directory.getAbsolutePath(), coin);
    }

    public void storeData(String coin, OhlcData data) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        String coinFileAddress = getCoinFileAddress(coin);
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

    private boolean hasData(String coin) {
        File file = new File(getCoinFileAddress(coin));
        return file.exists();
    }

    public OhlcData loadData(String coin) {
        synchronized (storageLock) {
            if (!hasData(coin))
                return null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(getCoinFileAddress(coin)));
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
