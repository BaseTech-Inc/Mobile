package com.example.tupa_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.Passwords.ResetPasswordActivity;
import com.example.tupa_mobile.R;

public class LoginActivity extends AppCompatActivity {

    private Button volt_log, cont_log;
    private EditText email_et_log, password_et_log;
    private TextView lblPass;
    private String email, password;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        volt_log = findViewById(R.id.volt_btn_log);
        cont_log = findViewById(R.id.cont_btn_log);

        email_et_log = findViewById(R.id.email_et_log);
        password_et_log = findViewById(R.id.senha_et_log);

        lblPass = findViewById(R.id.senha_lbl_log);


        sp = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        email = sp.getString("email", null);
        password = sp.getString("password", null);

        volt_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), LoginOptionsActivity.class);
                startActivity(it);
                finish();
            }
        });

        cont_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_et_log.getText().toString();
                password = password_et_log.getText().toString();
                Connection connection = new Connection();
                connection.loginUser(LoginActivity.this, email, password);
            }
        });

        lblPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), ResetPasswordActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}