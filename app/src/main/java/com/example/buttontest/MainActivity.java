package com.example.buttontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.buttontest.activity.BaseActivity;
import com.example.buttontest.activity.LoginActivity;
import com.example.buttontest.activity.RegisterActivity;

public class MainActivity extends BaseActivity {
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
//                Intent in = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(in);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
//                Intent in = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(in);
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnRegister = (Button)findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {

    }
}