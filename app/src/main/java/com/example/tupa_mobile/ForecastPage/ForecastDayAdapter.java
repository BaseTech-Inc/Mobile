package com.example.tupa_mobile.ForecastPage;

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
        return forecasts.size();
    }


    public class ForecastDayHolder extends RecyclerView.ViewHolder{

        private TextView txtHumidity, txtTemp1, txtTemp2, txtLocation, txtDay, txtSensation;

        public ForecastDayHolder(@NonNull View itemView) {
            super(itemView);

            txtHumidity = itemView.findViewById(R.id.txtHumidity);
            txtTemp1 = itemView.findViewById(R.id.txtTemp1);
            txtTemp2 = itemView.findViewById(R.id.txtTemp2);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtSensation = itemView.findViewById(R.id.txtSensation);
        }

        void setDetails(ForecastDay forecastDay){
            txtHumidity.setText(String.format("%d", forecastDay.getHumidity()));
            txtTemp1.setText(String.format("%d°", forecastDay.getMaxTemp()));
            txtTemp2.setText(String.format("/%d°", forecastDay.getMinTemp()));
            txtSensation.setText(String.format("Sensação térmica: %d°", forecastDay.getSensation()));
            txtLocation.setText(forecastDay.getLocation());
            txtDay.setText(forecastDay.getDate());
        }
    }
}
