package com.example.buttontest.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.buttontest.activity.LoginActivity;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

import static android.content.Context.MODE_PRIVATE;

public class Api {
    public static Api api = new Api();
    private static String requestUrl;
    private static OkHttpClient client;
    private static HashMap<String, Object> mParams;
    public Api(){

    }

    public static Api config(String url, HashMap<String, Object> params){
        client = new OkHttpClient.Builder().build();
        requestUrl = url;
        mParams = params;
        return api;
    }
    public void postRequest(TtitCallback callback){
//        SharedPreferences sp = context.getSharedPreferences("sp_ttit", MODE_PRIVATE);
//        String token = sp.getString("token", "");
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonStr);

        Request request = new Request.Builder()
                .url(AppConfig.BASE_URl + requestUrl)
                .addHeader("contentType","application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();

        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure",e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });
    }

    public void getRequest(Context context,TtitCallback callback){
        SharedPreferences sp = context.getSharedPreferences("sp_ttit", MODE_PRIVATE);
        String token = sp.getString("token", "");
        String url = getRequestUrl(AppConfig.BASE_URl + requestUrl,mParams);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token",token)
                .get()
                .build();

        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure",e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    if (code.equals("401")) {
                        Intent in = new Intent(context, LoginActivity.class);
                        context.startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(result);
            }
        });
    }

    private String getRequestUrl(String baseUrl, Map<String,Object> mParams){
        StringBuffer stringBuffer = new StringBuffer();
        if (!mParams.isEmpty() && mParams.size() != 0){
            Iterator<Map.Entry<String,Object>> it = mParams.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String, Object> entry = it.next();
                if (StringUtil.isEmpty(stringBuffer.toString())){
                    stringBuffer.append("?");
                }else{
                    stringBuffer.append("&");
                }
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return baseUrl + stringBuffer.toString();
    }
}
