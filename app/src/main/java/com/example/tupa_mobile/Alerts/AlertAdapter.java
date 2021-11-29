package com.example.tupa_mobile.Alerts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertHolder> {

    private Context context;
    private ArrayList<AlerBairro> alerts;

    public AlertAdapter(Context context, ArrayList<AlerBairro> alerts) {
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
        AlerBairro alert = alerts.get(position);
        holder.setDetails(alert);
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }


    public class AlertHolder extends RecyclerView.ViewHolder{

        private TextView txtAlertDate, txtAlertTitle, txtAlertText, txtAlertAddress, txtTempoInicial, txtTempoFinal;
        private ImageView imgRideIcon;
        public AlertHolder(@NonNull View itemView) {
            super(itemView);

            txtAlertTitle = itemView.findViewById(R.id.txtNotificationTitle);
            txtAlertAddress = itemView.findViewById(R.id.txtNotificationAddress);
            txtTempoFinal = itemView.findViewById(R.id.txtFinalTime);
            txtTempoInicial = itemView.findViewById(R.id.txtInitialTime);
            imgRideIcon = itemView.findViewById(R.id.imgRideIcon);
        }

        void setDetails(AlerBairro alert){
            txtAlertTitle.setText(alert.getDistricts().getNome());
            txtAlertAddress.setText(alert.getDescricao());
            String[] a = alert.getTempoFinal().split("T");
            String b = a[1];
            String[] z = b.split(":");
            txtTempoFinal.setText(z[0] + ":" + z[1] + "H");
            String[] n = alert.getTempoInicio().split("T");
            String o = n[1];
            String[] p = o.split(":");
            txtTempoInicial.setText(p[0] + ":" + p[1] + "H");
            if (alert.getTransitividade() == true){
                imgRideIcon.setImageResource(R.drawable.ic_icon_transitavel_ativo);
            }
            if (alert.getTransitividade() == false){
                imgRideIcon.setImageResource(R.drawable.ic_icon_intransitavel_ativo);
            }

        }
    }
}