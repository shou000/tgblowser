package com.example.owner.tgblowser;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by owner on 2016/10/06.
 */
public class NewThread extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        Document doc;
        try {
            doc = Jsoup.connect("http://togetter.com/hot")
                    .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4")
                    .get();
            //li = doc.select("li[class^=clearfix]");

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}