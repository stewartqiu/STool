package net.schooldroid.stool.WebView;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import net.schooldroid.stool.R;

import java.util.Objects;


public class WebView_Opener extends AppCompatActivity {

    public static String url;
    public static String title;
    WebView webView;
    ProgressBar pBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_opener);

        initialSetup();
        openUrl();
    }




    private void initialSetup() {

        if (title!=null && !title.isEmpty()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        }

        webView = findViewById(R.id.webView);
        pBar = findViewById(R.id.simpleProgressBar);
        pBar.setMax(100);

    }




    @SuppressLint("SetJavaScriptEnabled")
    private void openUrl() {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pBar.setVisibility(View.VISIBLE);
                pBar.setProgress(newProgress);

                if(newProgress == 100) {
                    pBar.setVisibility(View.GONE);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }




    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
