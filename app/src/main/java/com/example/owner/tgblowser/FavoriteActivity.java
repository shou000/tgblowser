package com.example.owner.tgblowser;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayAdapter<FavoriteItem> arrayAdapter;
    private FavoriteItem favoriteItem= null;
    private List<FavoriteItem> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ListView listView = (ListView)findViewById(R.id.listView);
        arrayAdapter =
                new ArrayAdapter<FavoriteItem>(this, R.layout.favorite_item);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavoriteItem item = arrayAdapter.getItem(position);

                Intent intent = new Intent(FavoriteActivity.this,DetailActivity.class);
                intent.setData(Uri.parse(item.getItem_url()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataLoadTask dataLoadTask = new DataLoadTask();
        dataLoadTask.execute();

    }

    public class DataLoadTask extends AsyncTask<Object,Integer,List<FavoriteItem>>{

        @Override
        protected List<FavoriteItem> doInBackground(Object... params) {
            DataAccess dac = new DataAccess(FavoriteActivity.this);

            return dac.all_list(favoriteItem);
        }

        @Override
        protected void onPostExecute(List<FavoriteItem> favoriteItems) {
            arrayAdapter.clear();
            for (FavoriteItem item:favoriteItems){
                arrayAdapter.add(item);
            }
            if(arrayAdapter.getCount() == 0){
                Toast.makeText(FavoriteActivity.this, "no item", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
