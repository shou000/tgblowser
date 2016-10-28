package com.example.owner.tgblowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

        ImageView thumb = (ImageView)view.findViewById(R.id.thumb);
        Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher_xdpi);
        bmp = Bitmap.createBitmap(bmp,0,0,128,128);
        thumb.setImageBitmap(bmp);
//        thumb.setImageResource(R.mipmap.ic_launcher);
        if (item.getThumb()!=null){
            ImageTask task = new ImageTask(thumb);
            task.execute(item.getThumb());
        }
        //thumb.setImageResource(item.getThumb());

        return view;
    }

    class ImageTask extends AsyncTask<String,Void, Bitmap>{
        private ImageView mimage;
        public ImageTask(ImageView image){
            mimage = image;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                inputStream = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);
               bitmap = Bitmap.createBitmap(bitmap,0,0,128,128);
            }catch (MalformedURLException exception){

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mimage.setImageBitmap(bitmap);
        }
    }
}
