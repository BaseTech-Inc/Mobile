package com.example.tupa_mobile.OpenWeather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.WeatherAPI.WeatherCondition;

import java.util.ArrayList;

public class OpenDailyAdapter extends RecyclerView.Adapter<OpenDailyAdapter.OpenDailyHolder> {

    private Context context;
    private ArrayList<OpenDaily> dailies;

    public OpenDailyAdapter(Context context, ArrayList<OpenDaily> dailies) {
        this.context = context;
        this.dailies = dailies;
    }

    @NonNull
    @Override
    public OpenDailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.open_forecast_item, parent,false);
        return new OpenDailyAdapter.OpenDailyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpenDailyHolder holder, int position) {
        OpenDaily openDaily = dailies.get(position);
        holder.setDetails(openDaily);
    }

    @Override
    public int getItemCount() {
        return this.dailies.size();
    }

    public class OpenDailyHolder extends RecyclerView.ViewHolder {

        TextView txtUpdateTime, txtTemperatures, txtHumidity;
        CardView clickableLayout;

        public OpenDailyHolder(@NonNull View itemView) {
            super(itemView);

            //initialize the textViews
            txtUpdateTime = itemView.findViewById(R.id.txtUpdateTime);
            txtTemperatures = itemView.findViewById(R.id.txtTemperatures);
            txtHumidity = itemView.findViewById(R.id.txtHumidity);

            clickableLayout = itemView.findViewById(R.id.clickableLayout);
        }

        void setDetails(OpenDaily openDaily){

            //assign textViews' values
            Temperature temp = openDaily.getTemp();
            ArrayList<OpenWeatherCondition> conditionList = openDaily.getWeather();
            OpenWeatherCondition condition = conditionList.get(0);
            FeelsLike feelsLike = openDaily.getFeels_like();

            txtUpdateTime.setText(openDaily.getDtFormatted());
            txtTemperatures.setText(Math.round(temp.getMax()) + "°/" + Math.round(temp.getMin()) + "°");
            txtHumidity.setText(Math.round(openDaily.getHumidity()) + "%");
        }
    }
}
