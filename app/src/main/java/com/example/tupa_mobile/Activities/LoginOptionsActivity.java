package com.example.tupa_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tupa_mobile.R;

public class LoginOptionsActivity extends AppCompatActivity {

    private Button log_btn, cad_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        log_btn = findViewById(R.id.log_btn);
        cad_btn = findViewById(R.id.cad_btn);

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
        cad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}