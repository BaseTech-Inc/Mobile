package com.example.tupa_mobile.Graph;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class GraphDayItemAdapter extends RecyclerView.Adapter<GraphDayItemAdapter.GraphDayHolder>{

    private Context context;
    private ArrayList<OpenDaily> dayItems;

    public GraphDayItemAdapter(Context context, ArrayList<OpenDaily> dayItems) {
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
        OpenDaily graphDayItem = dayItems.get(position);
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
            txtHumidity = itemView.findViewById(R.id.graphHumidityLabel);
            txtChanceOfRain = itemView.findViewById(R.id.graphRainLabel);
            imgGraph = itemView.findViewById(R.id.imgGraphItem);
        }

        public void setDetails(OpenDaily graphDayItem) {

            String icon = graphDayItem.getWeather().get(0).getIcon();
            String name = "";

            if(icon.contains("d")){
                name = getImageName(icon.split("d")[0]);
                name += "_day";
            }else if (icon.contains("n")){
                name = getImageName(icon.split("n")[0]);
                name += "_night";
            }

            Resources resources = context.getResources();
            int resID = resources.getIdentifier(name , "drawable", context.getPackageName());

            txtWeekDay.setText(graphDayItem.getDtFormatted());
            txtHumidity.setText(String.valueOf(graphDayItem.getHumidity()));
            txtChanceOfRain.setText(String.valueOf(graphDayItem.getRain()));
            imgGraph.setImageResource(resID);
        }

        private String getImageName(String iconNumber)
        {
            switch (iconNumber)
            {
                case "01": return "clear_sky";
                case "02": return "few_clouds";
                case "03":
                case "04":
                    return "scattered_clouds";
                case "09":
                case "10":
                    return "rain";
                case "11": return "thunderstorm";
                case "13": return "snow";
                default: return "clear_sky";
            }
        }
    }

}
