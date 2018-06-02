package com.hudezhi.freedom.homeapp.person.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by boy on 2017/8/7.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "private_work";
    private static final int DB_VERSION = 1;

    public static final String TB_USER_INFO = "tb_user_info";
    public static final String TB_MODULE_ITEM = "tb_module_item";
    public static final String TB_BUSINESS_RECEIPT = "tb_business_receipt";

    //tb_module
    public static final String MODULE_ID = "module_id";
    public static final String MODULE_TAG = "module_tag";
    public static final String MODULE_NAME = "module_name";
    public static final String MODULE_IMG_ID = "module_img_id";

    //tb_business_receipt
    public static final String _ID = "receipt_id";
    public static final String GO_START_DATE = "go_start_date";
    public static final String GO_START_TIME = "go_start_time";
    public static final String GO_START_PLACE = "go_start_place";
    public static final String GO_ARRIVE_DATE = "go_arrive_date";
    public static final String GO_ARRIVE_TIME = "go_arrive_time";
    public static final String GO_ARRIVE_PLACE = "go_arrive_place";
    public static final String GO_TOOLS = "go_tools";
    public static final String GO_FEES = "go_fees";

    public static final String BACK_START_DATE = "back_start_date";
    public static final String BACK_START_TIME = "back_start_time";
    public static final String BACK_START_PLACE = "back_start_place";
    public static final String BACK_ARRIVE_DATE = "back_arrive_date";
    public static final String BACK_ARRIVE_TIME = "back_arrive_time";
    public static final String BACK_ARRIVE_PLACE = "back_arrive_place";
    public static final String BACK_TOOLS = "back_tools";
    public static final String BACK_FEES = "back_fees";

    public static final String BONUS = "bonus";
    public static final String ROOM_FEES = "room_fees";
    public static final String OTHER_FEES = "other_fees";
    public static final String REMARKS = "remarks";

    public static final String IMG_PATH_LIST = "img_path_list";


    private Context mContext;
    private OnDBChangedListener mOnDBChangedListener;

    public void setOnDBChangedListener(OnDBChangedListener mOnDBChangedListener) {
        this.mOnDBChangedListener = mOnDBChangedListener;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String module_item_sql = "create table if not exists "
                + TB_MODULE_ITEM + "("
                + MODULE_ID + " integer primary key autoincrement, "
                + MODULE_TAG + " , "
                + MODULE_IMG_ID + " integer, "
                + MODULE_NAME + " text not null" + ");";
        db.execSQL(module_item_sql);

        String receipt_info_sql = "create table if not exists "
                + TB_BUSINESS_RECEIPT + "("
                + _ID + " integer primary key autoincrement, "
                + GO_START_DATE + "  text not null, "
                + GO_START_TIME + "  text not null, "
                + GO_ARRIVE_DATE + "  text not null, "
                + GO_ARRIVE_TIME + "  text not null, "
                + GO_START_PLACE + "  text not null, "
                + GO_ARRIVE_PLACE + "  text not null, "
                + GO_TOOLS + "  text not null, "
                + GO_FEES + "  integer not null, "
                + BACK_START_DATE + "  text not null, "
                + BACK_START_TIME + "  text not null, "
                + BACK_ARRIVE_DATE + "  text not null, "
                + BACK_ARRIVE_TIME + "  text not null, "
                + BACK_START_PLACE + "  text not null, "
                + BACK_ARRIVE_PLACE + "  text not null, "
                + BACK_TOOLS + "  text not null, "
                + BACK_FEES + "  integer not null, "
                + BONUS + "  integer not null, "
                + ROOM_FEES + "  integer not null, "
                + OTHER_FEES + "  integer not null, "
                + REMARKS + "  text not null, "
                + IMG_PATH_LIST + "  text not null);";
        db.execSQL(receipt_info_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists " + TB_MODULE_ITEM + ";");
            db.execSQL("drop table if exists " + TB_BUSINESS_RECEIPT + ";");
            onCreate(db);
        }
    }

    public void exeSql(String sql) {
        getWritableDatabase().execSQL(sql);
        if (mOnDBChangedListener != null) {
            mOnDBChangedListener.onDBChanged();
        }
    }

    public void exeSql(String sql, int id) {
        getWritableDatabase().execSQL(sql);
        if (mOnDBChangedListener != null) {
            mOnDBChangedListener.onDBChanged();
        }
    }

    public void exeSql(String sql, String[] args) {

    }


    public Cursor querySql(String sql) {
        return querySql(sql, null);
    }

    public Cursor querySql(String sql, String[] args) {
        return getWritableDatabase().rawQuery(sql, args);
    }

    public interface OnDBChangedListener {
        public void onDBChanged();
    }
}
