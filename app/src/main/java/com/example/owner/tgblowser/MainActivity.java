package com.example.owner.tgblowser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements ListView.OnScrollListener {

//    public ArrayList<ListItem> list;
    private Activity mainActivity = this;
//    private ListView listView;
    private ListArrayAdapter listArrayAdapter;
    private NewThread mTask;
    private final static int MAX_COUNT = 10;
    private int mCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        list = new ArrayList<ListItem>();
//        ListView listView = (ListView)findViewById(R.id.listView);
        final ListView listView = this.getListView();
        listView.addFooterView(getfooter());
        listView.setOnScrollListener(this);
        NewThread newThread= new  NewThread(){
            @Override
            protected void onPostExecute(final ArrayList<ListItem> list) {
//                ListView listView = mainActivity.getListView();
//        listView.addFooterView();
                listArrayAdapter = new ListArrayAdapter(mainActivity,R.layout.item,list);
                listView.setAdapter(listArrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s = list.get(position).getLink();
                        Intent intent = new Intent(mainActivity,DetailActivity.class);
                        intent.setData(Uri.parse(s));
                        mainActivity.startActivity(intent);
                    }
                });
            }
        };
        newThread.execute("1");
//        adapter =
//                new ListArrayAdapter(this, R.layout.item,list);

//        mAdapter = new ListArrayAdapter(this,R.layout.item,list);
//        listView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
//                new NewThread(this).execute("hot");
                break;
            case R.id.key1:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                Boolean bool = pref.getBoolean("checkbox_1_key",true);
                Toast.makeText(this, bool.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.favoriteview:
                Intent intent2 = new Intent(this,FavoriteActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }



    private View getfooter(){
        View mFooter = getLayoutInflater().inflate(R.layout.mainlist_footer,null);
        return mFooter;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == firstVisibleItem + visibleItemCount && totalItemCount != 0) {
            additionalReading();
        }
    }

    private void additionalReading(){
        if (mCount >= MAX_COUNT){
            invisibleFooter();
            return;
        }
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING){
            return;
        }
        mTask = new NewThread(){
            @Override
            protected void onPostExecute(ArrayList<ListItem> list) {
                addListData(list);
                getListView().invalidateViews();
            }
        };
        mTask.execute("" + mCount);
    }

    private void addListData(ArrayList<ListItem> list) {
        for (ListItem listitem:list){
            listArrayAdapter.add(listitem);
        }
        mCount++;
    }

    private void invisibleFooter(){
        getListView().removeFooterView(getfooter());
    }
}
