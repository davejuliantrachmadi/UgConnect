package com.davejr.ugconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class SAP extends AppCompatActivity {
    WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sap);

        getSupportActionBar().setTitle("SAP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        web_view= (WebView) findViewById(R.id.web_view);
        web_view.loadUrl("https://sap.gunadarma.ac.id/");
        web_view.setWebViewClient(new WebViewClient());
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
