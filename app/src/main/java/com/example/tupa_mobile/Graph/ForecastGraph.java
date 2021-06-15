package com.example.tupa_mobile.Graph;

import android.content.Context;
import android.graphics.Color;
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
    private LineDataSet lineDataSet, lineDataSet2;
    private LineData lineData;

    public void createGraph(LineChart forecastChart, Context context){

        lineList = new ArrayList<>();
        lineList.add(new Entry(1,18));
        lineList.add(new Entry(2,25));
        lineList.add(new Entry(3,22));
        lineList.add(new Entry(4,24));
        lineList.add(new Entry(5,26));
        lineList.add(new Entry(6,21));
        lineList.add(new Entry(7,25));
        lineList.add(new Entry(8,24));

        lineList2 = new ArrayList<>();
        lineList2.add(new Entry(1,10));
        lineList2.add(new Entry(2,7));
        lineList2.add(new Entry(3,12));
        lineList2.add(new Entry(4,11));
        lineList2.add(new Entry(5,9));
        lineList2.add(new Entry(6,10));
        lineList2.add(new Entry(7,11));
        lineList2.add(new Entry(8,5));

        lineDataSet = new LineDataSet(lineList, "Max Temperatures");
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawVerticalHighlightIndicator(false);

        lineDataSet2 = new LineDataSet(lineList2, "Min Temperatures");
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setDrawVerticalHighlightIndicator(false);
        lineDataSet2.setColor(Color.BLUE);

        lineData = new LineData(lineDataSet, lineDataSet2);
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
