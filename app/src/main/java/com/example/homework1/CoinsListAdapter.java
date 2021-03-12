package com.example.homework1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homework1.cryptodata.CryptoData;
import com.example.homework1.datagetters.cryptolist.CoinAvatarsStorage;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class CoinsListAdapter extends RecyclerView.Adapter<CoinsListAdapter.ViewHolder> {
    private final List<CryptoData> dataSet;
    private final CoinAvatarsStorage picsStorage;

    public CoinsListAdapter(List<CryptoData> dataSet, File filesDir) {
        this.dataSet = dataSet;
        this.picsStorage = new CoinAvatarsStorage(filesDir);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_row_item, parent, false);
        return new ViewHolder(view, picsStorage, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateFields(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, symbol, price, hourlyDiff, dailyDiff, weeklyDiff;
        private final ImageView coinAvatar;
        private final Context context;
        private final CoinAvatarsStorage picsStorage;

        public ViewHolder(@NonNull View itemView, CoinAvatarsStorage picsStorage, Context context) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);
            price = itemView.findViewById(R.id.price);
            hourlyDiff = itemView.findViewById(R.id.hourly_diff);
            dailyDiff = itemView.findViewById(R.id.daily_diff);
            weeklyDiff = itemView.findViewById(R.id.weekly_diff);
            coinAvatar = itemView.findViewById(R.id.coin_avatar);
            this.context = context;
            this.picsStorage = picsStorage;
        }

        public void updateFields(CryptoData data) {
            name.setText(data.getName());
            symbol.setText(data.getSymbol());
            price.setText(String.format(Locale.getDefault(), "Price: %s",
                    formatPrice(data.getPrice())));
            hourlyDiff.setText(String.format(Locale.getDefault(), "1 Hour: %s",
                    formatDiffPercent(data.getPercentChange1h())));
            dailyDiff.setText(String.format(Locale.getDefault(), "24 Hours: %s",
                    formatDiffPercent(data.getPercentChange24h())));
            weeklyDiff.setText(String.format(Locale.getDefault(), "7 Days: %s",
                    formatDiffPercent(data.getPercentChange7d())));

            Glide.with(context).load(picsStorage.loadAvatar(data.getId())).into(coinAvatar);
        }

        private String formatDiffPercent(Double value) {
            if (value < 0)
                return String.format(Locale.getDefault(), "%.2f%%", value);
            return String.format(Locale.getDefault(), "+%.2f%%", value);
        }

        private String formatPrice(Double value) {
            return String.format(Locale.getDefault(), "%.2f$", value);
        }
    }
}
