package com.example.homework1.datagetters.cryptolist;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.homework1.api.CoinMarketCapApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class CoinAvatarsStorage {
    private static final String AVATARS_DIR_NAME = "avatars";
    private static final Object storageLock = new Object();
    private static final int BITCOIN_ID = 1;

    private final File directory;

    public CoinAvatarsStorage(File root) {
        this.directory = new File(root, AVATARS_DIR_NAME);
        synchronized (storageLock) {
            if (!directory.exists() && !directory.mkdir())
                Log.e("Storage", "Could not create coins list storage directory.");
        }
    }

    private File getImageFile(int coin_id) {
        return new File(directory, String.format(Locale.getDefault(), "%d.png", coin_id));
    }

    public String loadAvatar(int coin_id) {
        File imageFile = getImageFile(coin_id);
        synchronized (storageLock) {
            if (!imageFile.exists()) {
                Bitmap bmp = CoinMarketCapApi.getLogo(coin_id);
                if (bmp == null)
                    return getImageFile(BITCOIN_ID).getAbsolutePath();

                try (FileOutputStream out = new FileOutputStream(imageFile.getAbsolutePath())) {
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageFile.getAbsolutePath();
    }
}
