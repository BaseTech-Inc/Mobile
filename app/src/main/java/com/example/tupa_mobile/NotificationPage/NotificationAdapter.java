package com.example.tupa_mobile.NotificationPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item,parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.setDetails(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public class NotificationHolder extends RecyclerView.ViewHolder{

        private TextView txtNotificationDate, txtNotificationText;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            txtNotificationDate = itemView.findViewById(R.id.txtNotificationDate);
            txtNotificationText = itemView.findViewById(R.id.txtNotificationText);
        }

        void setDetails(Notification notification){
            txtNotificationDate.setText(notification.getDate());
            txtNotificationText.setText(notification.getAlertMessage());
        }
    }
}
