package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tupa_mobile.Notifications.Notification;
import com.example.tupa_mobile.Notifications.NotificationAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView notificationRecycler;
    private ArrayList<Notification> notifications;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = findViewById(R.id.notificationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_right_icon_white_black_theme_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notificationRecycler = findViewById(R.id.notificationRecycler);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(getApplicationContext(), notifications);
        notificationRecycler.setAdapter(adapter);

        createViews();
    }

    private void createViews(){
        notifications.add(new Notification(R.drawable.day_cloudy, "Título 1", "Hoje às 22:00", "asdaasd asdasdas asdasdasd asdasdasd asdasdasd asdasdasd asdasdasda", true));
        notifications.add(new Notification(R.drawable.night_windy, "Título 2", "Hoje às 22:10", "asdaasd asdasdas asdasdasd asdasdasd asdasdasd asdasdasd asdasdasda", false));
        notifications.add(new Notification(R.drawable.night_clear, "Título 3", "Hoje às 22:20", "asdaasd asdasdas asdasdasd asdasdasd asdasdasd asdasdasd asdasdasda", false));
        notifications.add(new Notification(R.drawable.night_cloudy, "Título 4", "Hoje às 22:30", "asdaasd asdasdas asdasdasd asdasdasd asdasdasd asdasdasd asdasdasda", true));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("result", getCurrentFragmentIndex());
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int getCurrentFragmentIndex(){
        Intent intent = getIntent();
        int currentFragment = intent.getIntExtra("currentFragment", 0);
        return currentFragment;
    }
}