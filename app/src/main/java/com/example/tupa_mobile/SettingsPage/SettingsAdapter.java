package com.example.tupa_mobile.SettingsPage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.AccountActivity;
import com.example.tupa_mobile.Activities.ConnectionsActivity;
import com.example.tupa_mobile.Activities.PrivacyActivity;
import com.example.tupa_mobile.R;
import com.example.tupa_mobile.WeatherAPI.Day;
import com.example.tupa_mobile.WeatherAPI.ForecastDay;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Settings> settingsList;

    public SettingsAdapter(Context context, ArrayList<Settings> settingsList) {
        this.context = context;
        this.settingsList = settingsList;
    }

    @Override
    public int getItemViewType(int position) {
        Settings settings = settingsList.get(position);

        if(settings.hasSwitch()){
            return R.layout.settings_switch_item;
        }
        else if(settings.hasIcon() != true){
            return R.layout.settings_item_noimage;
        }
        else if (settings.showArrow() != true){
            return R.layout.settings_item_noarrow;
        }

        return R.layout.settings_item;
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(viewType, parent, false);

            switch (viewType){
                case R.layout.settings_switch_item:
                    return new SettingsAdapter.SettingsSwitchHolder(view);
                case R.layout.settings_item_noimage:
                    return new SettingsAdapter.SettingsNoImageHolder(view);
                case R.layout.settings_item_noarrow:
                    return new SettingsAdapter.SettingsNoArrowHolder(view);
                default:
                    return new SettingsAdapter.SettingsHolder(view);
            }
        }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Settings settings = settingsList.get(position);

             switch (holder.getItemViewType()) {

                case R.layout.settings_item:
                    SettingsHolder settingsHolder = (SettingsHolder)holder;
                    settingsHolder.setDetails(settings);
                break;

                case R.layout.settings_item_noimage:
                    SettingsNoImageHolder settingsNoImageHolder = (SettingsNoImageHolder)holder;
                    settingsNoImageHolder.setDetails(settings);
                break;

                 case R.layout.settings_item_noarrow:
                     SettingsNoArrowHolder settingsNoArrowHolder = (SettingsNoArrowHolder)holder;
                     settingsNoArrowHolder.setDetails(settings);
                     break;

                 case R.layout.settings_switch_item:
                     SettingsSwitchHolder settingsSwitchHolder = (SettingsSwitchHolder)holder;
                     settingsSwitchHolder.setDetails(settings);
             }
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
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(settings.getIntent());
                    }
                });
            }
        }

    public class SettingsSwitchHolder extends RecyclerView.ViewHolder{

        private TextView txtSetting, txtDesc;
        private ImageView imgSetting;
        private Switch switchSettings;

        public SettingsSwitchHolder(@NonNull View itemView) {
            super(itemView);

            txtSetting = itemView.findViewById(R.id.txtSetting);

        }

        void setDetails(Settings settings){

            txtSetting.setText(settings.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(settings.getIntent());
                }
            });
        }
    }

    public class SettingsNoImageHolder extends RecyclerView.ViewHolder{

        private TextView txtSetting, txtDesc;

        public SettingsNoImageHolder(@NonNull View itemView) {
            super(itemView);

            txtSetting = itemView.findViewById(R.id.txtSetting);
            txtDesc = itemView.findViewById(R.id.txtDesc);

        }

        void setDetails(Settings settings){

            txtSetting.setText(settings.getTitle());
            txtDesc.setText(settings.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(settings.getIntent());
                }
            });
        }
    }

    public class SettingsNoArrowHolder extends RecyclerView.ViewHolder{

        private TextView txtSetting, txtDesc;
        private ImageView imgSetting;
        private Switch switchSettings;

        public SettingsNoArrowHolder(@NonNull View itemView) {
            super(itemView);

            txtSetting = itemView.findViewById(R.id.txtSetting);

        }

        void setDetails(Settings settings){

            txtSetting.setText(settings.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(settings.getIntent());
                }
            });
        }
    }

}
