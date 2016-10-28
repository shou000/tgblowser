package com.example.owner.tgblowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by owner on 2016/10/06.
 */
public class NewThread extends AsyncTask<String,Void,ArrayList<ListItem>> {
    private ArrayList<ListItem> mlist = new ArrayList<ListItem>();
    private Activity mainActivity;
    private ListArrayAdapter mAdapter;

//    public NewThread(Activity activity) {
////        mlist = list;
//        this.mainActivity = activity;
//    }

    @Override
    protected ArrayList<ListItem> doInBackground(String... params) {
        Document doc;
        try {
//            doc = Jsoup.parse(,"UTF-8");
//            for (int i = 1 ;i<=5;i++) {
                doc = Jsoup.connect("http://togetter.com/hot?page="+params[0])
                        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4")
                        .get();
                parse(doc);
//                doc = Jsoup.connect("http://togetter.com/hot?page=2")
//                        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4")
//                        .get();
//                parse(doc);
//            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return mlist;
    }

    @Override
    protected void onPostExecute(final ArrayList<ListItem> list) {}

    public void parse(Document doc){
//        ListItem item = new ListItem();
//        item.setLink("aaaaaaaaaa");
//        item.setThumb("bbbbbbb");
//        item.setTitle("c");
//        mlist.add(item);

        Elements lists_nothumb,lists;
        lists = doc.select("div.contents_main > ul.simple_list li.clearfix");
        for(Element list : lists){
            ListItem item = new ListItem();
            item.setTitle(list.getElementsByTag("h3").attr("title"));
            if(list.getElementsByClass("thumb").select("img").attr("src").isEmpty()){
                item.setThumb(null);
            }else{
                item.setThumb(list.getElementsByClass("thumb").select("img").attr("src"));
            }
//            item.setThumb(list.getElementsByClass("thumb").select("img").attr("src"));
//            item.setThumb(R.mipmap.ic_launcher);
            item.setLink(list.getElementsByTag("a").attr("href"));
//            item.setTitle("a");
//            item.setThumb("b");
//            item.setLink(list.outerHtml());
            mlist.add(item);
        }
    }

}