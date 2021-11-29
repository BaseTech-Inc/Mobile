package com.example.tupa_mobile.OpenWeather;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
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

        ImageView imgMiniForecast;
        TextView txtUpdateTime, txtMaxTemperature, txtMinTemperature, txtHumidity;
        CardView clickableLayout;

        public OpenDailyHolder(@NonNull View itemView) {
            super(itemView);

            //initialize the textViews
            txtUpdateTime = itemView.findViewById(R.id.txtUpdateTime);
            txtMaxTemperature = itemView.findViewById(R.id.txtMaxTemperature);
            txtMinTemperature = itemView.findViewById(R.id.txtMinTemperature);
            txtHumidity = itemView.findViewById(R.id.txtHumidity);
            imgMiniForecast = itemView.findViewById(R.id.imgMiniForecast);
            clickableLayout = itemView.findViewById(R.id.clickableLayout);
        }

        void setDetails(OpenDaily openDaily){

            String icon = openDaily.getWeather().get(0).getIcon();
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

            //assign textViews' values
            Temperature temp = openDaily.getTemp();
            ArrayList<OpenWeatherCondition> conditionList = openDaily.getWeather();
            OpenWeatherCondition condition = conditionList.get(0);
            FeelsLike feelsLike = openDaily.getFeels_like();

            txtUpdateTime.setText(openDaily.getDtFormatted());
            txtMaxTemperature.setText(Math.round(temp.getMax()) + "°");
            txtMinTemperature.setText(Math.round(temp.getMin()) + "°");
            txtHumidity.setText(Math.round(openDaily.getHumidity()) + "%");
            imgMiniForecast.setImageResource(resID);
            Log.d("tag", name);
            Log.d("tag", String.valueOf(resID));
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
