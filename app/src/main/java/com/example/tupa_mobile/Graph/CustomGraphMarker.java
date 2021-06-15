package com.example.tupa_mobile.Graph;

import android.content.Context;
import android.widget.TextView;

import com.example.tupa_mobile.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomGraphMarker extends MarkerView {

    TextView tvMarker;

    public CustomGraphMarker(Context context, int layoutResource) {
        super(context, layoutResource);

        tvMarker = findViewById(R.id.txtMarker);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvMarker.setText("" + e.getY());

        super.refreshContent(e, highlight);
    }

    MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }
}
