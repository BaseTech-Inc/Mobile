package com.example.tupa_mobile.Notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tupa_mobile.MainActivity;
import com.example.tupa_mobile.R;

import static com.example.tupa_mobile.Notifications.App.CHANNEL_1_ID;

public class NotificationSender {

    private NotificationManagerCompat notificationManager;
    private Intent activityIntent, broadcastIntent;
    private PendingIntent contentIntent;
    private String title, message, bigTitle, bigText, summary;
    int appColor;

    public void sendOnChannel1(View view){

        activityIntent =  new Intent(view.getContext(), MainActivity.class);
        contentIntent = PendingIntent.getActivity(view.getContext(),
                0, activityIntent, 0);

        title = "Alerta!";
        message = "Há um novo alerta em sua área";
        bigText = "aaaaaaaa a aaa aaaaa aaaa aaaa aaaa aaaa aaa aa a a a a aa aaaaaaaa aaaa aaa aaaaaaaaaaa aa aaaaaaaa a aaaaaa aaaaaaa";
        summary = "Alagamento";
        bigTitle = "Rua Alameda dos Guainumbis";
        notificationManager = NotificationManagerCompat.from(view.getContext());

        Bitmap largeIcon = BitmapFactory.decodeResource(view.getResources(), R.drawable.nibolas);

        Notification notification = new NotificationCompat.Builder(view.getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.nibolas)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigText)
                        .setBigContentTitle(bigTitle)
                        .setSummaryText(summary))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(1, notification);
    }

}
