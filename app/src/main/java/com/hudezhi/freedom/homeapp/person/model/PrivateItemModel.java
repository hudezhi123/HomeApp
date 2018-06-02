package com.hudezhi.freedom.homeapp.person.model;

import android.content.Context;
import android.database.Cursor;

import com.hudezhi.freedom.homeapp.person.bean.PrivateModuleItem;
import com.hudezhi.freedom.homeapp.person.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/8/7.
 */

public class PrivateItemModel implements IPrivateItemModel {

    private DBHelper dbHelper;

    private Context mContext;

    public PrivateItemModel() {
    }

    public PrivateItemModel(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    @Override
    public List<PrivateModuleItem> loadPrivateItemList() {
        String sql = "select * from " + DBHelper.TB_MODULE_ITEM;
        Cursor cursor = dbHelper.querySql(sql);
        List<PrivateModuleItem> itemList = new ArrayList<>();
        PrivateModuleItem item = null;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                item = new PrivateModuleItem();
                item.setModuleName(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NAME)));
                item.setTag(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_TAG)));
                item.setImgId(cursor.getInt(cursor.getColumnIndex(DBHelper.MODULE_IMG_ID)));
                itemList.add(item);
            }
            cursor.close();
            return itemList;
        }
        return null;
    }

    @Override
    public PrivateModuleItem loadPrivateItem(int id) {
        String sql = "select * from " + DBHelper.TB_MODULE_ITEM;
        Cursor cursor = dbHelper.querySql(sql);
        PrivateModuleItem item = null;
        if (cursor != null) {
            cursor.moveToFirst();
            item = new PrivateModuleItem();
            item.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.MODULE_ID)));
            item.setModuleName(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NAME)));
            item.setTag(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_TAG)));
            item.setImgId(cursor.getInt(cursor.getColumnIndex(DBHelper.MODULE_IMG_ID)));
        }
        return item;
    }


    @Override
    public void savePrivateItem(String tag, String name, int img_id) {
        String sql = "insert into "
                + DBHelper.TB_MODULE_ITEM + "("
                + DBHelper.MODULE_TAG + " ,"
                + DBHelper.MODULE_NAME + ""
                + DBHelper.MODULE_IMG_ID + ""
                + ")values("
                + "'" + tag + "',"
                + "'" + name + "',"
                + "'" + img_id + "');";
        dbHelper.exeSql(sql);
    }
}
