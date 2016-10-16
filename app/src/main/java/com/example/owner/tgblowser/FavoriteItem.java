package com.example.owner.tgblowser;

import java.io.Serializable;

/**
 * Created by owner on 2016/10/17.
 */

public class FavoriteItem implements Serializable {

    /*テーブル名*/
    public static final String TABLE_NAME = "tbl_favorite";

    /*カラム名*/
    /* 実際のDataBaseに記録されるカラム名 */
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URL = "url";

    /*プロパティ*/
    /* 保存の情報や検索情報などを保持するものになります */
    private int rowid = 0;
    private String item_title = null;
    private String item_url = null;

    private static String item_serach_title = null;

    /* プロパティを操作するメソッド達です */

    public int getRowid() {
        return rowid;
    }
    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String getItem_title() {
        return item_title;
    }
    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_url() {
        return item_url;
    }
    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

    public static String getItem_serach_title() {
        return item_serach_title;
    }
    public static void setItem_serach_title(String item_serach_title) {
        FavoriteItem.item_serach_title = item_serach_title;
    }

    @Override
    public String toString() {
        return getItem_title();
    }
}
