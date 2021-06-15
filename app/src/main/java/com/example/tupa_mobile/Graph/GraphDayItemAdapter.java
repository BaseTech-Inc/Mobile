package com.example.tupa_mobile.Graph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class GraphDayItemAdapter extends RecyclerView.Adapter<GraphDayItemAdapter.GraphDayHolder>{

    private Context context;
    private ArrayList<GraphDayItem> dayItems;

    public GraphDayItemAdapter(Context context, ArrayList<GraphDayItem> dayItems) {
        this.context = context;
        this.dayItems = dayItems;
    }

    @NonNull
    @Override
    public GraphDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.graph_day_item, parent,false);
        return new GraphDayItemAdapter.GraphDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GraphDayHolder holder, int position) {
        GraphDayItem graphDayItem = dayItems.get(position);
        holder.setDetails(graphDayItem);
    }

    @Override
    public int getItemCount() {
        return this.dayItems.size();
    }

    public class GraphDayHolder extends RecyclerView.ViewHolder{

        TextView txtWeekDay, txtDate, txtHumidity, txtChanceOfRain;
        ImageView imgGraph;

        public GraphDayHolder(@NonNull View itemView) {
            super(itemView);

            txtWeekDay = itemView.findViewById(R.id.graphDayLabel);
            txtDate = itemView.findViewById(R.id.graphDayNumber);
            txtHumidity = itemView.findViewById(R.id.graphHumidityLabel);
            txtChanceOfRain = itemView.findViewById(R.id.graphRainLabel);
            imgGraph = itemView.findViewById(R.id.imgGraphItem);
        }

        public void setDetails(GraphDayItem graphDayItem) {

            txtWeekDay.setText(graphDayItem.getWeekDay());
            txtDate.setText(graphDayItem.getDate());
            txtHumidity.setText(graphDayItem.getHumidity());
            txtChanceOfRain.setText(graphDayItem.getChanceOfRain());
            imgGraph.setImageResource(R.drawable.night_clear);
        }
    }

}
