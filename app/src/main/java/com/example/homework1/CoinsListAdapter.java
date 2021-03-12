package com.example.homework1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework1.cryptodata.CryptoData;

import java.util.List;
import java.util.Locale;

public class CoinsListAdapter extends RecyclerView.Adapter<CoinsListAdapter.ViewHolder> {
    List<CryptoData> dataSet;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_row_item, parent, false);
        return new ViewHolder(view);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            symbol = (TextView) itemView.findViewById(R.id.symbol);
            price = (TextView) itemView.findViewById(R.id.price);
            hourlyDiff = (TextView) itemView.findViewById(R.id.hourly_diff);
            dailyDiff = (TextView) itemView.findViewById(R.id.daily_diff);
            weeklyDiff = (TextView) itemView.findViewById(R.id.weekly_diff);
        }

        public void updateFields(CryptoData data) {
            name.setText(data.getName());
            symbol.setText(data.getSymbol());
            price.setText(String.format(Locale.getDefault(), "Price: %s$", data.getName()));
            hourlyDiff.setText(String.format(Locale.getDefault(), "1 Hour: %s",
                    formatDiffPercent(data.getPercentChange1h())));
            dailyDiff.setText(String.format(Locale.getDefault(), "24 Hours: %s",
                    formatDiffPercent(data.getPercentChange24h())));
            weeklyDiff.setText(String.format(Locale.getDefault(), "7 Days: %s",
                    formatDiffPercent(data.getPercentChange7d())));
        }

        private String formatDiffPercent(Double value) {
            if (value < 0)
                return String.format(Locale.getDefault(), "%.2f%%", value);
            return String.format(Locale.getDefault(), "+%.2f%%", value);
        }
    }
}
