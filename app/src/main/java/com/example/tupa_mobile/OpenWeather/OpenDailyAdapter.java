package com.example.tupa_mobile.OpenWeather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.WeatherAPI.ForecastHour;
import com.example.tupa_mobile.WeatherAPI.WeatherCondition;

import java.util.ArrayList;
import java.util.List;

public class OpenDailyAdapter extends RecyclerView.Adapter<OpenDailyAdapter.OpenDailyHolder> {

    private Context context;
    private ArrayList<OpenDaily> dailies;
    private ArrayList<OpenDaily> allDailies;

    public OpenDailyAdapter(Context context, ArrayList<OpenDaily> dailies) {
        this.context = context;
        this.dailies = dailies;
        allDailies = new ArrayList<>(dailies);
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

    public Filter getFilter() {
        return daysFilter;
    }

    private Filter daysFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<OpenDaily> filteredDays = new ArrayList<>();

            for(int i = 0; i < 5; i++){
                filteredDays.add(allDailies.get(i));
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredDays;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dailies.clear();
            dailies.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class OpenDailyHolder extends RecyclerView.ViewHolder {

        TextView txtUpdateTime, txtMaxTemperature, txtMinTemperature, txtHumidity;
        CardView clickableLayout;

        public OpenDailyHolder(@NonNull View itemView) {
            super(itemView);

            //initialize the textViews
            txtUpdateTime = itemView.findViewById(R.id.txtUpdateTime);
            txtMaxTemperature = itemView.findViewById(R.id.txtMaxTemperature);
            txtMinTemperature = itemView.findViewById(R.id.txtMinTemperature);
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
            txtMaxTemperature.setText(Math.round(temp.getMax()) + "°");
            txtMinTemperature.setText(Math.round(temp.getMin()) + "°");
            txtHumidity.setText(Math.round(openDaily.getHumidity()) + "%");
        }
    }
}
