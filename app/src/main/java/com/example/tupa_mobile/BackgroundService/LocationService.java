package com.example.tupa_mobile.BackgroundService;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.tupa_mobile.Activities.MainActivity;
import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.concurrent.Executor;

import static com.example.tupa_mobile.Fragments.MapFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class LocationService extends Service{

    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    private static final String NOTIF_CHANNEL_DESC = "Channel_Desc";

    private static final String TAG = LocationService.class.getSimpleName();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Do service here
        mHandler = new Handler();
        startRepeatingTask();
        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                //compare location
                getLocationPermission();
                try {
                    if (locationPermissionGranted) {
                        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                        locationResult.addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                Log.e(TAG, latitude + ", " + longitude);
                            }
                        });
                    }
                } catch (SecurityException e)  {
                    Log.e("Exception: %s", e.getMessage(), e);
                }
                Log.d("Teste", "ta funfando");

                //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    void startRepeatingTask() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getBaseContext());
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        createNotificationChannel();

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = NOTIF_CHANNEL_ID;
            String description = NOTIF_CHANNEL_DESC;
            int importance = NotificationManager.IMPORTANCE_MIN;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
