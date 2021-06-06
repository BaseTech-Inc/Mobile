package com.example.tupa_mobile.OpenWeather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        View view = LayoutInflater.from(context).inflate(R.layout.open_forecast_item,parent, false);
        return new OpenDailyAdapter.OpenDailyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpenDailyHolder holder, int position) {
        OpenDaily openDaily = dailies.get(position);
        holder.setDetails(openDaily);

        boolean isExpandable = dailies.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.dailies.size();
    }

    public class OpenDailyHolder extends RecyclerView.ViewHolder {

        TextView txtUpdateTime, txtMaxTemp, txtMinTemp, txtFeelsLike, txtSunrise, txtSunset, txtPressure, txtHumidity, txtRain, txtPop, txtCondition, txtDescCondition;
        LinearLayout clickableLayout;
        RelativeLayout expandableLayout;

        public OpenDailyHolder(@NonNull View itemView) {
            super(itemView);

            //initialize the textViews
            txtUpdateTime = itemView.findViewById(R.id.txtUpdateTime);
            txtMaxTemp = itemView.findViewById(R.id.txtMaxTemp);
            txtMinTemp = itemView.findViewById(R.id.txtMinTemp);
            txtFeelsLike = itemView.findViewById(R.id.txtFeelsLike);
            txtSunrise = itemView.findViewById(R.id.txtSunrise);
            txtSunset = itemView.findViewById(R.id.txtSunset);
            txtPressure = itemView.findViewById(R.id.txtPressure);
            txtHumidity = itemView.findViewById(R.id.txtHumidity);
            txtRain = itemView.findViewById(R.id.txtRain);
            txtPop = itemView.findViewById(R.id.txtPop);
            txtCondition = itemView.findViewById(R.id.txtCondition);
            txtDescCondition = itemView.findViewById(R.id.txtConditionDesc);

            clickableLayout = itemView.findViewById(R.id.clickableLayout);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            clickableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OpenDaily daily =  dailies.get(getAdapterPosition());
                    daily.setExpandable(!daily.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }

        void setDetails(OpenDaily openDaily){

            //assign textViews' values
            Temperature temp = openDaily.getTemp();
            ArrayList<OpenWeatherCondition> conditionList = openDaily.getWeather();
            OpenWeatherCondition condition = conditionList.get(0);
            FeelsLike feelsLike = openDaily.getFeels_like();

            txtUpdateTime.setText(openDaily.getDtFormatted());
            txtMaxTemp.setText(String.format("%s°", temp.getMax()));
            txtMinTemp.setText(String.format("%s°", temp.getMin()));
            txtFeelsLike.setText(String.format("Feels like: %s°", feelsLike.getDay()));
            txtSunrise.setText(String.format("Sunrise: %d", openDaily.getSunrise()));
            txtSunset.setText(String.format("Sunset: %d", openDaily.getSunset()));
            txtPressure.setText(String.format("Pressure: %datm", openDaily.getPressure()));
            txtHumidity.setText(String.format("Humidity:%s", openDaily.getHumidity()));
            txtRain.setText(String.format("Rain: %s", openDaily.getRain()));
            txtPop.setText(String.format("%s", openDaily.getPop()));
            txtCondition.setText(String.format("Condition: %s", condition.getMain()));
            txtDescCondition.setText(String.format("Description: %s", condition.getDescription()));
        }
    }
}
