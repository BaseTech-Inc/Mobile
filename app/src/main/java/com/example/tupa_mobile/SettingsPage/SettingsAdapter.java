package com.example.tupa_mobile.SettingsPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.WeatherAPI.Day;
import com.example.tupa_mobile.WeatherAPI.ForecastDay;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {

    private Context context;
    private ArrayList<Settings> settingsList;

    public SettingsAdapter(Context context, ArrayList<Settings> settingsList) {
        this.context = context;
        this.settingsList = settingsList;
    }

    @NonNull
        @Override
        public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.settings_item,parent, false);
            return new SettingsAdapter.SettingsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingsHolder holder, int position) {
            Settings settings = settingsList.get(position);
            holder.setDetails(settings);
        }

        @Override
        public int getItemCount() {
            return this.settingsList.size();
        }

        public class SettingsHolder extends RecyclerView.ViewHolder{

            private TextView txtSetting, txtDesc;
            private ImageView imgSetting;

            public SettingsHolder(@NonNull View itemView) {
                super(itemView);

                txtSetting = itemView.findViewById(R.id.txtSetting);
                txtDesc = itemView.findViewById(R.id.txtDesc);
                imgSetting = itemView.findViewById(R.id.imgSetting);
            }

            void setDetails(Settings settings){

                txtSetting.setText(settings.getTitle());
                txtDesc.setText(settings.getDescription());
                imgSetting.setImageResource(settings.getImgSource());
            }
        }

}
