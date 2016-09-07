package com.example.owner.tgblowser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    private WebView myWebView;
    private char i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView)findViewById(R.id.webView);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if(url.equals("http://togetter.com/li/"+"*" )){

                Log.d("TAG","Originalurl "+ url);
                if(url.startsWith("http://togetter.com/li") ){
                    url = "http://i." + url.substring(7,url.length());
                    Log.d("TAG","changeurl "+ url);
                    myWebView.loadUrl(url);
                }else if(url.startsWith("myapp:link:")){
                    url = url.substring(11,url.length());
                    url = url.replaceFirst("%3A",":");
                    Log.d("TAG","intent " + url);
                    myWebView.loadUrl(url);
                }

                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(40);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
            }
        });

        myWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4");
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.loadUrl("http://togetter.com/hot");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()){
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,  event);
    }
}
