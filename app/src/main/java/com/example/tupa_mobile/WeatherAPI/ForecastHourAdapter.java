package com.example.tupa_mobile.WeatherAPI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            txtTempHour.setText(String.valueOf(Math.round(forecastHour.getTemp_c())) + "°");
            imgHour.setImageResource(getImageId(forecastHour.getTimeFormatted(), forecastHour.getCondition().getText()));
            Log.d("niv", forecastHour.getCondition().getText());
            Log.d("oi", forecastHour.getTimeFormatted());
        }




        private int getImageId(String time, String text)
        {
            int hours = Integer.parseInt(time.split(":")[0]);
            if (hours < 7 || hours > 19){
                if (text.contains("y")){
                    return R.drawable.night_cloudy;
                }
                else if (text.contains("rain")){
                    return R.drawable.rain_night;
                }else if (text.contains("Clear")){
                    return R.drawable.clear_sky_night;
                }
                else return R.drawable.clear_sky_night;
            }
            else {
                if (text.contains("loudy")){
                    return R.drawable.day_cloudy;
                }
                else if (text.contains("rain")){
                    return R.drawable.rain_day;
                }else {
                    return R.drawable.clear_sky_day;
                }
            }

            /*
            switch (iconNumber)
            {
                case 1000: return "Sunny or Clear";
                case 1003: return "Partly cloudy";
                case 1006: return "cloudy";
                case 1009: return "cloudy";
                case 1030: return "mist";
                case 1063: return "Patchy rain possible";
                case 1069: return "Patchy sleet possible";
                case 1072: return "Patchy freezing drizzle possible";
                case 1087: return "Thundery outbreaks possible";
                case 1135: return "Fog";
                case 1150: return "Patchy light drizzle";
                case 1153: return "Light drizzle";
                case 1180: return "Patchy light rain";
                case 1183: return "Light rain";
                case 1186: return
                default: return "clear_sky";
            }

             */
        }

    }
}
