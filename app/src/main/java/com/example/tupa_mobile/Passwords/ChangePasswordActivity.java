package com.example.tupa_mobile.Passwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText et_oldPass, et_newPass;
    private Button btn_cont;
    private String oldPass, newPass, token;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        et_oldPass = findViewById(R.id.oldPass_et_change);
        et_newPass = findViewById(R.id.newPass_et_change);
        btn_cont = findViewById(R.id.cont_btn_change);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        btn_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = et_oldPass.getText().toString();
                newPass = et_newPass.getText().toString();
                token = sp.getString("token", null);

                Connection connection = new Connection();
                connection.ChangePassword(ChangePasswordActivity.this, token, oldPass, newPass);
            }
        });
    }

}