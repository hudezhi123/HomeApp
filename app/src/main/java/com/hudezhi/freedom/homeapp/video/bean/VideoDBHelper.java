package com.hudezhi.freedom.homeapp.video.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by boy on 2017/8/24.
 */

public class VideoDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "video.db";
    private static final int VERSION = 1;

    public static final String VIDEO_PROCESS_TB = "video_process_tb";
    public static final String ID = "_id";
    public static final String FILE_URL = "file_url";
    public static final String FILE_PATH = "file_path";
    public static final String FILE_SIZE = "file_size";
    public static final String FILE_NAME = "file_name";
    public static final String FILE_PAUSE_POINT = "file_pause_point";
    public static final String IS_DOWNLOAD_FINISH = "is_download_finish";
    public static final String FILE_THUMBNAIL_PATH = "file_thumbnail_path";
    public static final String IS_DOWNLOADING = "is_downloading";


    public VideoDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_video_proccess = "create table if not exists "
                + VIDEO_PROCESS_TB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FILE_URL + " TEXT, "
                + FILE_PATH + " TEXT, "
                + FILE_SIZE + " TEXT, "
                + FILE_NAME + " TEXT, "
                + FILE_PAUSE_POINT + " TEXT, "
                + IS_DOWNLOAD_FINISH + " TEXT, "
                + FILE_THUMBNAIL_PATH + " TEXT, "
                + IS_DOWNLOADING + " TEXT"
                + ");";
        db.execSQL(sql_video_proccess);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("drop table if exists " + VIDEO_PROCESS_TB + ";");
        }
    }

    public Cursor querySQL(String sql, String[] selectionArgs) {
        Cursor cursor = getWritableDatabase().rawQuery(sql, selectionArgs);
        return cursor;
    }


    public void execSQL(String sql) {
        try {
            getWritableDatabase().execSQL(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
