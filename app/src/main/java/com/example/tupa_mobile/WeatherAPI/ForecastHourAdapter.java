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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastHourAdapter extends RecyclerView.Adapter<ForecastHourAdapter.ForecastHourHolder> implements Filterable {

    private Context context;
    private ArrayList<ForecastHour> hours;
    private ArrayList<ForecastHour> allHours;

    public ForecastHourAdapter(Context context, ArrayList<ForecastHour> hours) {
        this.context = context;
        this.hours = hours;
        allHours = new ArrayList<>(hours);
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

    @Override
    public Filter getFilter() {
        return hourFilter;
    }

    private Filter hourFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ForecastHour> filteredHours = new ArrayList<>();

            for(ForecastHour forecastHour : allHours){
                if(forecastHour.getTime_epoch() >= System.currentTimeMillis()){
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

            txtHour.setText(forecastHour.getTimeFormatted());
            txtTempHour.setText(String.valueOf(forecastHour.getTemp_c()) + "°");
            imgHour.setImageResource(R.drawable.night_clear);
        }
    }
}
