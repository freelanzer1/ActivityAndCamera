package com.example.activityandcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {
    WebView webView;


    // для работы с webView в манифесте обязательно указать разрешение на работу с сетью <uses-permission android:name="android.permission.INTERNET" />
    // для примера также укажем android:usesCleartextTraffic="true", чтобы работать без https



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.myWebView);

        // устанавливаем настройки, чтобы ссылки открывались только в нашем окне
        webView.setWebViewClient(new MyWebViewClient());
        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public void button4(View view) {
        TextView editTextUrl = findViewById(R.id.editTextUrl);
        webView.loadUrl(editTextUrl.getText().toString());
    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        // Для старых устройств
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}