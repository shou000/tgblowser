package com.example.owner.tgblowser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by owner on 2016/10/17.
 */

public class DatabaseOpenHelperItem extends SQLiteOpenHelper {

    private static final String DB_NAME="FAVORITE_ITEM";

    public DatabaseOpenHelperItem(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            StringBuilder createsdl = new StringBuilder();
            createsdl.append("create table " + FavoriteItem.TABLE_NAME + " (");
            createsdl.append(FavoriteItem.COLUMN_ID + " integer primary key autoincrement not null, ");
            createsdl.append(FavoriteItem.COLUMN_TITLE +" text, ");
            createsdl.append(FavoriteItem.COLUMN_URL + " text");
            createsdl.append(")");

            db.execSQL(createsdl.toString());
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
