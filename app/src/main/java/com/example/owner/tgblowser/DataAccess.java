package com.example.owner.tgblowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owner on 2016/10/17.
 */

public class DataAccess {
    private DatabaseOpenHelperItem helper = null;

    public DataAccess(Context context) {
        helper = new DatabaseOpenHelperItem(context);
    }

    public FavoriteItem save_item(FavoriteItem item){
        SQLiteDatabase db = helper.getWritableDatabase();
        FavoriteItem result = null;

        try {
            ContentValues values = new ContentValues();
            values.put(FavoriteItem.COLUMN_TITLE, item.getItem_title());
            values.put(FavoriteItem.COLUMN_URL,item.getItem_url());

            int rowId = item.getRowid();
            if(rowId == 0){
                rowId = (int)db.insert(FavoriteItem.TABLE_NAME,null,values);
            }else {
                db.update(FavoriteItem.TABLE_NAME,
                        values,
                        FavoriteItem.COLUMN_ID + "=?",
                        new String[]{String.valueOf(rowId)});
            }result = load_item(rowId);
        }finally {
            db.close();
        }
        return result;
    }

    public void delete_item(FavoriteItem item){
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.delete(FavoriteItem.TABLE_NAME,
                    FavoriteItem.COLUMN_ID+"=?",
                    new String[]{String.valueOf(item.getRowid())});
        }finally {
            db.close();
        }
    }

    public FavoriteItem load_item(int itemid){
        SQLiteDatabase db = helper.getReadableDatabase();
        FavoriteItem number = null;
        try {
            String query = "select * "+
                    " from " + FavoriteItem.TABLE_NAME+
                    " where "+FavoriteItem.COLUMN_ID+
                    " = '"+itemid+"';";
            Cursor cursor = db.rawQuery(query ,null);
            cursor.moveToFirst();
            number = getItem(cursor);
        }finally {
            db.close();
        }
        return number;
    }

    public List all_list(FavoriteItem item){
        SQLiteDatabase db = helper.getReadableDatabase();
        List itemlist;
        try {
            Cursor cursor = db.rawQuery("select * from "+ FavoriteItem.TABLE_NAME,null);
            itemlist = new ArrayList();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                itemlist.add(getItem(cursor));
                cursor.moveToNext();
            }
        }finally {
            db.close();
        }
        return itemlist;
    }

    private FavoriteItem getItem(Cursor cursor){
        FavoriteItem item = new FavoriteItem();

        item.setRowid((int)cursor.getLong(0));
        item.setItem_title(cursor.getString(1));
        item.setItem_url(cursor.getString(2));
        return item;
    }
}
