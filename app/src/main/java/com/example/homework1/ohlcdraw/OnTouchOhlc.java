package com.example.homework1.ohlcdraw;

import android.graphics.Canvas;
import android.widget.TextView;

import com.example.homework1.ohlcdraw.CryptoCandleEntry;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class OnTouchOhlc implements IMarker {
    private TextView textView;

    OnTouchOhlc(TextView textView) {
        this.textView = textView;
    }

    public MPPointF getOffset() {
        return new MPPointF(0, 0);
    }

    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return new MPPointF(0, 0);
    }

    public void refreshContent(Entry e, Highlight highlight) {
        textView.setText("" + ((CryptoCandleEntry) e).toString());
    }


    public void draw(Canvas canvas, float posX, float posY) {

    }
}
