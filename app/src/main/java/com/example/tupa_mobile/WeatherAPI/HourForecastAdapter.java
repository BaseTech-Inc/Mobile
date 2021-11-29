package com.example.tupa_mobile.WeatherAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HourForecastAdapter extends RecyclerView.Adapter<HourForecastAdapter.HourForecastHolder> implements Filterable {

    private Context context;
    private ArrayList<HourForecast> hours;
    private ArrayList<HourForecast> allHours;

    public HourForecastAdapter(Context context, ArrayList<HourForecast> hours) {
        this.context = context;
        this.hours = hours;
        allHours = new ArrayList<>(hours);
    }

    @Override
    public Filter getFilter() {
        return hourFilter;
    }

    private Filter hourFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<HourForecast> filteredHours = new ArrayList<>();

            for(HourForecast forecastHour : allHours){
                if(forecastHour.getDt() >= System.currentTimeMillis()){
                    filteredHours.add(forecastHour);
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredHours;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hours.clear();
            hours.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public HourForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_hour_item,parent, false);
        return new HourForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourForecastHolder holder, int position) {
        HourForecast hourForecast = hours.get(position);
        holder.setDetails(hourForecast);
    }

    @Override
    public int getItemCount() {
        return this.hours.size();
    }

    public class HourForecastHolder extends RecyclerView.ViewHolder {

        private TextView txtHour, txtTempHour;
        private ImageView imgHour;

        public HourForecastHolder(View view) {
            super(view);

            txtHour = itemView.findViewById(R.id.txtHour);
            txtTempHour = itemView.findViewById(R.id.txtTempHour);
            imgHour = itemView.findViewById(R.id.imgHour);

        }

        public void setDetails(HourForecast hourForecast) {

            Date date = new Date();
            date.setTime((long) hourForecast.getTemp() * 1000);
            String time = date.getHours() + ":" + date.getMinutes();

            txtHour.setText(time);
            txtTempHour.setText(String.format("%d°", Math.round(hourForecast.getTemp())));
            imgHour.setImageResource(R.drawable.nibolas);
        }
    }
}
