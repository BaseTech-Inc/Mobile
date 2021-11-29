package com.example.tupa_mobile.Passwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tupa_mobile.Activities.LoginActivity;
import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button volt_btn_pass, env_btn_pass;
    private EditText email_et_pass;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        volt_btn_pass = findViewById(R.id.volt_btn_pass);
        env_btn_pass = findViewById(R.id.env_btn_pass);
        email_et_pass = findViewById(R.id.email_et_pass);


        volt_btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });

        env_btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_et_pass.getText().toString();
                Connection connection = new Connection();
                connection.ResetPassword(ResetPasswordActivity.this, email);

            }
        });

    }
}