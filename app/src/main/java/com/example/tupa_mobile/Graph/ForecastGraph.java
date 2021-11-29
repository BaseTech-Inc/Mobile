package com.example.tupa_mobile.Graph;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import com.example.tupa_mobile.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ForecastGraph {

    private ArrayList<Entry> lineList, lineList2;
    private LineDataSet lineDataSetMax, lineDataSetMin;
    private LineData lineData;

    public void createGraph(LineChart forecastChart, Context context, ArrayList<Entry> MaxTemp, ArrayList<Entry> MinTemp){

        lineDataSetMax = new LineDataSet(MaxTemp, "Max Temperatures");
        lineDataSetMax.setDrawHorizontalHighlightIndicator(false);
        lineDataSetMax.setDrawVerticalHighlightIndicator(false);

        lineDataSetMin = new LineDataSet(MinTemp, "Min Temperatures");
        lineDataSetMin.setDrawHorizontalHighlightIndicator(false);
        lineDataSetMin.setDrawVerticalHighlightIndicator(false);
        lineDataSetMin.setColor(Color.BLUE);

        lineData = new LineData(lineDataSetMax, lineDataSetMin);
        lineData.setValueTextSize(12);

        forecastChart.setData(lineData);

        forecastChart.setNoDataText("We couldn't get the forecast");
        forecastChart.setDragEnabled(false);
        forecastChart.setScaleEnabled(false);
        forecastChart.getDescription().setEnabled(false);
        forecastChart.setDrawMarkers(false);
        forecastChart.setDrawBorders(false);

        XAxis xAxis = forecastChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        YAxis yAxisLeft = forecastChart.getAxisLeft();
        YAxis yAxisRight = forecastChart.getAxisRight();

        yAxisLeft.setEnabled(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawGridLinesBehindData(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawLabels(false);

        yAxisRight.setEnabled(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLinesBehindData(false);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawLabels(false);

        IMarker marker = new CustomGraphMarker(context, R.layout.marker_layout);
        forecastChart.setMarker(marker);

        forecastChart.getLegend().setEnabled(false);
    }
}
