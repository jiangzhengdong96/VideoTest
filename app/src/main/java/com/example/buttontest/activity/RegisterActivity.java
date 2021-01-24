package com.example.buttontest.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.buttontest.R;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {
    private Button btn_register;
    private EditText et_username, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_username.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                register(account,pwd);
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        btn_register = (Button)findViewById(R.id.rregister);
        et_password = (EditText)findViewById(R.id.rpassword);
        et_username = (EditText)findViewById(R.id.rusername);
    }

    @Override
    protected void initData() {

    }

    public void register(String account, String pwd){
        if (StringUtil.isEmpty(account)){
            showToast("请输入账号");
            return;
        }
        if (StringUtil.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }

        HashMap<String,Object> m = new HashMap<String,Object>();
        m.put("mobile",account);
        m.put("password",pwd);

        Api.config(AppConfig.REGISTER,m).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(res);
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {
                Log.e("register_onFailure",e.toString());
            }
        });
    }
}