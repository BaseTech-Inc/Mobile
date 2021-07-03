package com.example.tupa_mobile.SettingsPage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class SelectorSettingsAdapter extends RecyclerView.Adapter<SelectorSettingsAdapter.SelectorSettingsHolder> {

    private Context context;
    private ArrayList<SelectorSettings> settingsArray;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    public SelectorSettingsAdapter(Context context, ArrayList<SelectorSettings> settingsArray) {
        this.context = context;
        this.settingsArray = settingsArray;
    }

    @NonNull
    @Override
    public SelectorSettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settings_selector, parent, false);
        return new SelectorSettingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectorSettingsHolder holder, int position) {
        SelectorSettings settings = settingsArray.get(position);
        holder.setDetails(settings);
        holder.checkButtons(position);
    }

    @Override
    public int getItemCount() {
        return settingsArray.size();
    }


    public class SelectorSettingsHolder extends RecyclerView.ViewHolder {

        RadioButton radioButton;
        TextView txtTheme;

        public SelectorSettingsHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.rbTheme);
            txtTheme = itemView.findViewById(R.id.txtTheme);
        }

        public void setDetails(SelectorSettings settings) {

            radioButton.setChecked(getSavedTheme(settings.getName()));
            txtTheme.setText(settings.getName());

        }

        public void checkButtons(int position){

            radioButton.setTag(new Integer(position));

            //for default check in first item
            if(getSavedTheme(settingsArray.get(position).getName()))
            {
                lastChecked = radioButton;
                lastCheckedPos = position;
            }

            radioButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    RadioButton cb = (RadioButton)v;
                    int clickedPos = ((Integer)cb.getTag()).intValue();

                    if(cb.isChecked())
                    {
                        if(lastChecked != null && lastChecked != cb)
                        {
                            lastChecked.setChecked(false);
                            settingsArray.get(lastCheckedPos).setChecked(false);
                            saveSelectedTheme(settingsArray.get(lastCheckedPos).getName(), false);
                        }

                        lastChecked = cb;
                        lastCheckedPos = clickedPos;
                    }
                    else
                        lastChecked = null;

                    settingsArray.get(clickedPos).setChecked(cb.isChecked());

                    saveSelectedTheme(settingsArray.get(clickedPos).getName(), settingsArray.get(clickedPos).isChecked());

                    if(settingsArray.indexOf(radioButton) == 0) {
                        ((Activity)context).setTheme(R.style.Theme_TupaMobile);
                        ((Activity)context).getApplication().setTheme(R.style.Theme_TupaMobile);
                    }
                    else if(settingsArray.indexOf(radioButton) == 1){
                        ((Activity)context).setTheme(R.style.Theme_DimmedTupaMobile);
                        ((Activity)context).getApplication().setTheme(R.style.Theme_DimmedTupaMobile);
                    }
                    else if(settingsArray.indexOf(radioButton) == 2){
                        ((Activity)context).setTheme(R.style.Theme_DimmedTupaMobile);
                        ((Activity)context).getApplication().setTheme(R.style.Theme_DimmedTupaMobile);
                    }
                    //((Activity)context).recreate();// This is important. It allows the theme change to take effect.
                }
            });
        }

        public void saveSelectedTheme(String key, boolean value){

            SharedPreferences sp = context.getSharedPreferences("SELECTED_THEME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(key, value).apply();

        }

        public boolean getSavedTheme(String key){

            SharedPreferences sp = context.getSharedPreferences("SELECTED_THEME", Context.MODE_PRIVATE);
            return sp.getBoolean(key, false);

        }
    }
}
