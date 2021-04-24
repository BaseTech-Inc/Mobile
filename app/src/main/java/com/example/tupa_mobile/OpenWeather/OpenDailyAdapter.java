package com.example.tupa_mobile.OpenWeather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

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
    }

    @Override
    public int getItemCount() {
        return this.dailies.size();
    }

    public class OpenDailyHolder extends RecyclerView.ViewHolder {

        TextView txtUpdateTime, txtTempDay, txtTempNight, txtFeelsLikeDay, txtFeelsLikeNight, txtHumidity, txtRain, txtPop, txtCondition, txtDescCondition;

        public OpenDailyHolder(@NonNull View itemView) {
            super(itemView);

            //initialize the textViews
            txtUpdateTime = itemView.findViewById(R.id.txtUpdateTime);
            txtTempDay = itemView.findViewById(R.id.txtTempDay);
            txtRain = itemView.findViewById(R.id.txtRain);
            txtPop = itemView.findViewById(R.id.txtPop);

        }

        void setDetails(OpenDaily openDaily){

            //assign textViews' values

            txtUpdateTime.setText("Update: " + openDaily.getDt());
            txtTempDay.setText("Day UVI: " + openDaily.getUvi());
            txtRain.setText("Rain (mm): " + openDaily.getRain());
            txtPop.setText("Rain Probability:" + openDaily.getPop());

        }
    }
}
