package com.example.tupa_mobile.SettingsPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

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

        switch (settings.getType()){
            case 0:
                return R.layout.settings_item;
            case 1:
                return R.layout.settings_switch_item;
            case 2:
                return R.layout.settings_item_noimage;
            case 3:
                return R.layout.settings_dangerous;
            case 4:
                return R.layout.settings_no_arrow_or_icon;
            default:
                return R.layout.settings_item;
        }

    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(viewType, parent, false);

            switch (viewType){
                case R.layout.settings_switch_item:
                    return new SettingsAdapter.SettingsSwitchHolder(view);
                case R.layout.settings_item_noimage:
                    return new SettingsAdapter.SettingsNoImageHolder(view);
                case R.layout.settings_dangerous:
                    return new SettingsAdapter.SettingsDangerousHolder(view);
                case R.layout.settings_no_arrow_or_icon:
                    return new SettingsAdapter.SettingsBasicHolder(view);
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

                 case R.layout.settings_dangerous:
                     SettingsDangerousHolder settingsDangerousHolder = (SettingsDangerousHolder)holder;
                     settingsDangerousHolder.setDetails(settings);
                     break;

                 case R.layout.settings_no_arrow_or_icon:
                     SettingsBasicHolder settingsBasicHolder = (SettingsBasicHolder) holder;
                     settingsBasicHolder.setDetails(settings);
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

    public class SettingsDangerousHolder extends RecyclerView.ViewHolder{

        private TextView txtSetting;

        public SettingsDangerousHolder(@NonNull View itemView) {
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

    public class SettingsBasicHolder extends RecyclerView.ViewHolder{

        private TextView txtSetting;

        public SettingsBasicHolder(@NonNull View itemView) {
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
