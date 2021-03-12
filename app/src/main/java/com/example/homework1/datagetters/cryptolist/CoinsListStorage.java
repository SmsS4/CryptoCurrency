package com.example.homework1.datagetters.cryptolist;

import android.util.Log;

import com.example.homework1.cryptodata.CryptoData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class CoinsListStorage {
    private static final String COINS_LIST_DIR_NAME = "coins_list";
    private static final Object storageLock = new Object();

    private final File directory;

    public CoinsListStorage(File root) {
        this.directory = new File(root, COINS_LIST_DIR_NAME);
        synchronized (storageLock) {
            if (!directory.exists() && !directory.mkdir())
                Log.e("Storage", "Could not create OHLC storage directory.");
        }
    }

    private String getRowFileAddress(int row) {
        return String.format(Locale.getDefault(), "%s/%d.json",
                directory.getAbsolutePath(), row);
    }

    public void storeData(int row, CryptoData data) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        String rowFileAddress = getRowFileAddress(row);
        synchronized (storageLock) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(rowFileAddress));
                writer.write(jsonData);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasData(int row) {
        File file = new File(getRowFileAddress(row));
        return file.exists();
    }

    public CryptoData loadData(int row) {
        synchronized (storageLock) {
            if (!hasData(row))
                return null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(getRowFileAddress(row)));
                String jsonData = reader.readLine();
                Gson gson = new Gson();
                return gson.fromJson(jsonData, CryptoData.class);
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
