package com.example.tupa_mobile.WeatherAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class ForecastHourAdapter extends RecyclerView.Adapter<ForecastHourAdapter.ForecastHourHolder>{

    private Context context;
    private ArrayList<ForecastHour> hours;

    public ForecastHourAdapter(Context context, ArrayList<ForecastHour> hours) {
        this.context = context;
        this.hours = hours;
    }

    @NonNull
    @Override
    public ForecastHourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_hour_item,parent, false);
        return new ForecastHourHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastHourHolder holder, int position) {
        ForecastHour forecastHour = hours.get(position);
        holder.setDetails(forecastHour);
    }

    @Override
    public int getItemCount() {
        return this.hours.size();
    }

    public class ForecastHourHolder extends RecyclerView.ViewHolder{

        private TextView txtHour, txtTempHour;
        private ImageView imgHour;

        public ForecastHourHolder(@NonNull View itemView) {
            super(itemView);

            txtHour = itemView.findViewById(R.id.txtHour);
            txtTempHour = itemView.findViewById(R.id.txtTempHour);
            imgHour = itemView.findViewById(R.id.imgHour);
        }

        public void setDetails(ForecastHour forecastHour) {

            txtHour.setText("10:00");
            txtTempHour.setText(String.format("%s°", forecastHour.getTemp_c()));
            imgHour.setImageResource(R.drawable.nibolas);
        }
    }
}
