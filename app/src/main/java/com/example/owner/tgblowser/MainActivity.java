package com.example.owner.tgblowser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView myWebView;
    private String pageTitle;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
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
//                progressBar.setProgress(40);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
            }
        });
        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
                progressBar.setProgress(progress*1000);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                pageTitle = myWebView.getTitle();
                textView.setText(pageTitle);
                Log.d("TAG","pagetitle "+ pageTitle);
            }
        });

        myWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4");
        myWebView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            String url = uri.toString();
            Toast.makeText(this, url, Toast.LENGTH_LONG).show();
            if(url.startsWith("http://togetter.com/li") ){
                url = "http://i." + url.substring(7,url.length());
                Log.d("TAG","changeurl "+ url);
                myWebView.loadUrl(url);
            }
        }else {
            myWebView.loadUrl("http://togetter.com/hot");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent = new android.content.Intent(this, preference.class);
                startActivity(intent);
                break;
            case R.id.reload:
                myWebView.reload();
                break;
            case R.id.key1:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                Boolean bool = pref.getBoolean("checkbox_1_key",true);
                Toast.makeText(this, bool.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
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
