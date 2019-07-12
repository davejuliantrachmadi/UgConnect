package com.davejr.ugconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.ProgressBar;


public class BAAK extends AppCompatActivity {
WebView web_view;
String ShowOrHideWebViewInitialUse = "show";

private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        getSupportActionBar().setTitle("BAAK");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        web_view= (WebView) findViewById(R.id.web_view);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        web_view.setWebViewClient(new CustomWebViewClient());
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setDomStorageEnabled(true);
        web_view.getSettings().setLoadWithOverviewMode(true);
        web_view.getSettings().setDefaultTextEncodingName("utf-8");
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.getSettings().setDisplayZoomControls(false);
        web_view.getSettings().setSupportZoom(true);
        web_view.loadUrl("https://baak.gunadarma.ac.id/");


    }

    private class CustomWebViewClient extends WebViewClient {

        public void onPageStarted(WebView webview, String url, Bitmap favicon) {

            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setVisibility(webview.INVISIBLE);
            }
            webview.setVisibility(webview.INVISIBLE);
        }


        @Override
        public void onPageFinished(WebView view, String url) {

            ShowOrHideWebViewInitialUse = "hide";
            spinner.setVisibility(View.GONE);

            view.setVisibility(web_view.VISIBLE);
            super.onPageFinished(view, url);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (web_view.canGoBack()) {
                        web_view.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
