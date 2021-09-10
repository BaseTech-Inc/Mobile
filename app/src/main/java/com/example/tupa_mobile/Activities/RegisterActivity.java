package com.example.tupa_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;

public class RegisterActivity extends AppCompatActivity {

    private Button volt_cad, cont_cad;
    private EditText username_et_cad, email_et_cad, password_et_cad;
    private String username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        volt_cad = findViewById(R.id.volt_btn_cad);
        cont_cad = findViewById(R.id.cont_btn_cad);

        username_et_cad = findViewById(R.id.nome_et_cad);
        email_et_cad = findViewById(R.id.email_et_cad);
        password_et_cad = findViewById(R.id.senha_et_cad);

        volt_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), LoginOptionsActivity.class);
                startActivity(it);
                finish();
            }
        });
        cont_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_et_cad.getText().toString();
                email = email_et_cad.getText().toString();
                password = password_et_cad.getText().toString();
                Connection connection = new Connection();
                connection.registerUser(RegisterActivity.this, username, email, password);
            }
        });

    }
}