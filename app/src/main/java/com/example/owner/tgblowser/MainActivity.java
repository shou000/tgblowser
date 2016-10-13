package com.example.owner.tgblowser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

//    public ArrayList<ListItem> list;
//    private ListView listView;
//    private ListArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        list = new ArrayList<ListItem>();
//        listView = (ListView)findViewById(R.id.listView);

        new NewThread(this).execute("a");
//        adapter =
//                new ListArrayAdapter(this, R.layout.item,list);

//        mAdapter = new ListArrayAdapter(this,R.layout.item,list);
//        listView.setAdapter(mAdapter);
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
                //myWebView.reload();
                break;
            case R.id.key1:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                Boolean bool = pref.getBoolean("checkbox_1_key",true);
                Toast.makeText(this, bool.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

}
