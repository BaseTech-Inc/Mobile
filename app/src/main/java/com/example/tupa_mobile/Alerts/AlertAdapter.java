package com.example.tupa_mobile.Alerts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertHolder> {

    private Context context;
    private ArrayList<Alert> alerts;

    public AlertAdapter(Context context, ArrayList<Alert> alerts) {
        this.context = context;
        this.alerts = alerts;
    }


    @NonNull
    @Override
    public AlertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_item,parent, false);
        return new AlertHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertHolder holder, int position) {
        Alert alert = alerts.get(position);
        holder.setDetails(alert);
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }


    public class AlertHolder extends RecyclerView.ViewHolder{

        private TextView txtAlertDate, txtAlertTitle, txtAlertText, txtAlertAddress;

        public AlertHolder(@NonNull View itemView) {
            super(itemView);

            txtAlertTitle = itemView.findViewById(R.id.txtNotificationTitle);
            txtAlertAddress = itemView.findViewById(R.id.txtNotificationAddress);
        }

        void setDetails(Alert alert){
            txtAlertTitle.setText(alert.getTitle());
            txtAlertAddress.setText(alert.getAddress());
        }
    }
}