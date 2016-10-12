package com.example.owner.tgblowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by owner on 2016/10/12.
 */

public class ListArrayAdapter extends ArrayAdapter<ListItem> {
    private int resourceId;
    private List<ListItem> items;
    private LayoutInflater inflater;


    public ListArrayAdapter(Context context, int resourceId, List<ListItem> items) {
        super(context, resourceId, items);
        this.resourceId = resourceId;
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null){
            view = convertView;
        }else {
            view = this.inflater.inflate(this.resourceId,null);
        }
        ListItem item = this.items.get(position);
        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(item.getTitle());
        TextView link = (TextView)view.findViewById(R.id.link);
        link.setText(item.getLink());
        TextView thumb = (TextView)view.findViewById(R.id.thumb);
        thumb.setText(item.getThumb());

        return view;
    }
}
