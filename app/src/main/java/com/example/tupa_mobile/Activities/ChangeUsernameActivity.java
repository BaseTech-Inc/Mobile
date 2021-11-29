package com.example.tupa_mobile.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;

public class ChangeUsernameActivity extends AppCompatActivity {

    EditText profTxt;
    Button profBtn, voltBtn;
    String name, token, tipo;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        profBtn = findViewById(R.id.prof_btn_change);
        profTxt = findViewById(R.id.prof_name_change);
        voltBtn = findViewById(R.id.volt_btn_change);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        token = sp.getString("token", null);
        tipo = sp.getString("tipo", null);

        profBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = profTxt.getText().toString();

                Connection connection = new Connection();
                connection.SendInfoProfile(ChangeUsernameActivity.this, name, tipo);
            }
        });

        voltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), AccountActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
}
