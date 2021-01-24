package com.example.buttontest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.buttontest.R;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.entity.LoginResponse;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    private EditText et_password, et_username;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_username.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                login(account,pwd);
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        et_password = (EditText)findViewById(R.id.lpassword);
        et_username = (EditText)findViewById(R.id.lusername);
        bt_login = (Button)findViewById(R.id.llogin);
    }

    @Override
    protected void initData() {

    }

    private void login(String account, String pwd){
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

        Api.config(AppConfig.LOGIN,m).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(res);
//                    }
//                });
//                showToastSync(res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 0){
                    String token = loginResponse.getToken();
                    saveBySp("token",token);
                    navigateTo(HomeActivity.class);
                    showToastSync("登录成功");
                }else {
                    showToastSync("登录失败");
                }

            }

            @Override
            public void onFailure(Exception e) {

            }
        });



    }
//    private void login(String account, String pwd){
//        if (StringUtil.isEmpty(account)){
//            showToast("请输入账号");
//            return;
//        }
//        if (StringUtil.isEmpty(pwd)){
//            showToast("请输入密码");
//            return;
//        }
//
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        Map m = new HashMap();
//        m.put("mobile",account);
//        m.put("password",pwd);
//        JSONObject jsonObject = new JSONObject(m);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonStr);
//
//        Request request = new Request.Builder()
//                .url(AppConfig.BASE_URl + "/app/login")
//                .addHeader("contentType","application/json;charset=UTF-8")
//                .post(requestBodyJson)
//                .build();
//
//        final Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("onFailure",e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String result = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(result);
//                    }
//                });
//
//            }
//        });
//    }
}