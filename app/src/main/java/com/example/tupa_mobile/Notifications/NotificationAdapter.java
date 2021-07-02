package com.example.tupa_mobile.Notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Markers.MarkerAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{

    Context context;
    ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.setDetails(notification);
    }

    @Override
    public int getItemCount() {
        return this.notifications.size();
    }


    public class NotificationHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDate, txtDesc;
        ImageView icoNotification, icoActive;


        public NotificationHolder(@NonNull View view) {
            super(view);

            txtTitle = view.findViewById(R.id.txtTitle);
            txtDate = view.findViewById(R.id.txtDate);
            txtDesc = view.findViewById(R.id.txtDesc);
            icoNotification = view.findViewById(R.id.icoNotificationType);
            icoActive = view.findViewById(R.id.icoActive);

        }

        public void setDetails(Notification notification) {

            txtTitle.setText(notification.getTitle());
            txtDate.setText(notification.getDate());
            txtDesc.setText(notification.getDescription());
            icoNotification.setImageResource(notification.getIcon());

            if (notification.isActive()){
                icoActive.setImageResource(R.drawable.night_clear);
            }
            else{
                icoActive.setImageResource(R.drawable.day_sunny);
            }
        }
    }
}
