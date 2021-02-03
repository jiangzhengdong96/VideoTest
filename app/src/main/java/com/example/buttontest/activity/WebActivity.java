package com.example.buttontest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.example.buttontest.R;
import com.example.buttontest.jsbridge.BridgeWebView;

public class WebActivity extends BaseActivity {
    private BridgeWebView webView;


    @Override
    protected int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        webView = (BridgeWebView)findViewById(R.id.zixun_detail);
    }

    @Override
    protected void initData() {
        Bundle b = getIntent().getExtras();
        if (b != null){
            webView.loadUrl(b.getString("uri"));
        }
        initWebView();
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void registJavaHandler() {
    }
}