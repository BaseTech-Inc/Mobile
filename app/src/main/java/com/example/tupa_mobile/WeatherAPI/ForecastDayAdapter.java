package com.example.tupa_mobile.WeatherAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class ForecastDayAdapter extends RecyclerView.Adapter<ForecastDayAdapter.ForecastDayHolder> {

    private Context context;
    private ArrayList<ForecastDay> forecasts;

    public ForecastDayAdapter(Context context, ArrayList<ForecastDay> forecasts) {
        this.context = context;
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public ForecastDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_item,parent, false);
        return new ForecastDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDayHolder holder, int position) {
        ForecastDay forecastDay = forecasts.get(position);
        holder.setDetails(forecastDay);
    }

    @Override
    public int getItemCount() {
        return this.forecasts.size();
    }

    public class ForecastDayHolder extends RecyclerView.ViewHolder{

        private TextView txtHumidity, txtTemp1, txtTemp2, txtLocation, txtDay, txtRain;

        public ForecastDayHolder(@NonNull View itemView) {
            super(itemView);

            txtHumidity = itemView.findViewById(R.id.txtHumidity);
            txtTemp1 = itemView.findViewById(R.id.txtTemp1);
            txtTemp2 = itemView.findViewById(R.id.txtTemp2);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtRain = itemView.findViewById(R.id.txtRain);
        }

        void setDetails(ForecastDay forecastDay){

            Day day = forecastDay.getDay();

            txtHumidity.setText(String.valueOf(day.getAvghumidity()));
            txtTemp1.setText(day.getMaxtemp_c() + "°");
            txtTemp2.setText(day.getMintemp_c() + "°");
            txtRain.setText(day.getTotalprecip_mm() + "mm");
            txtLocation.setText("Sampa");
            txtDay.setText(String.valueOf(forecastDay.getDate()));
        }
    }
}
