package com.hudezhi.freedom.homeapp.audio.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

/**
 * Created by boy on 2017/6/2.
 */

public class MusicDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String MUSIC_DB_NAME = "home.db";
    private static final int DB_VERSION = 1;

    //详细信息
    public static final String TB_MY_MUSIC_INFO = "MusicInfo";

    //my love
    public static final String TB_MY_FOCUS = "MyFocus";

    //recent listen
    public static final String TB_RECENT_SONG = "RecentSongs";

    //music attribute
    public static final String SONG_ID = "id";
    public static final String SINGER_NAME = "artist";
    public static final String SONG_NAME = "songName";
    public static final String ALBUM = "album";
    public static final String ALBUM_ID = "albumId";
    public static final String URI = "uri";
    public static final String SIZE = "size";
    public static final String DURATION = "duration";
    public static final String IS_MUSIC = "isMusic";
    public static final String IS_FOCUS = "isFocus";
    public static final String IS_RECENT = "isRecent";
    public static final String ALBUM_IMG_PATH = "albumImgPath";
    public static final String ALBUM_IMG_URI = "albumIMGUri";
    public static final String RECENT_PLAY_TIME = "recentPlayTime";
    public static final String IS_PLAYED = "isPlayed";


    public MusicDBHelper(Context context) {
        super(context, MUSIC_DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_music_info = "create table if not exists "
                + TB_MY_MUSIC_INFO
                + " (" + SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SINGER_NAME + " TEXT, "
                + SONG_NAME + " TEXT, "
                + ALBUM + " TEXT, "
                + ALBUM_ID + " TEXT, "
                + URI + " TEXT, "
                + SIZE + " TEXT, "
                + DURATION + " TEXT, "
                + IS_MUSIC + " TEXT, "
                + IS_RECENT + " , "
                + IS_FOCUS + " TEXT, "
                + ALBUM_IMG_PATH + " TEXT, "
                + ALBUM_IMG_URI + " TEXT, "
                + IS_PLAYED + " TEXT, "
                + RECENT_PLAY_TIME + " TEXT"
                + ");";
        db.execSQL(sql_music_info);
//        String sql_focus = "create table if not exists "
//                + TB_MY_FOCUS
//                + " (" + SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + SINGER_NAME + " TEXT, " + SONG_NAME + " TEXT);";
//        db.execSQL(sql_focus);
//        String sql_recent = "create table if not exists "
//                + TB_RECENT_SONG
//                + " (" + SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + SINGER_NAME + " TEXT, " + SONG_NAME + " TEXT);";
//        db.execSQL(sql_recent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists " + TB_MY_MUSIC_INFO + ";");
            db.execSQL("drop table if exists " + TB_RECENT_SONG + ";");
            db.execSQL("drop table if exists " + TB_MY_FOCUS + ";");
            onCreate(db);
        }
    }

    public Cursor querySQL(String sql, String[] selectionArgs) {
        Cursor cursor = getWritableDatabase().rawQuery(sql, selectionArgs);
        return cursor;
    }

    public void clostCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public void execSQL(String sql) {
        try {
            getWritableDatabase().execSQL(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearTable(String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            return;
        } else {
            String sql_get_table = "select count(*) from sqlite_master where type='table' and name='" + tableName + "';";
            Cursor cursor = getWritableDatabase().rawQuery(sql_get_table, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return;
            } else {
                String sql_clear_table = "update sqlite_sequence set seq=0 where name='" + tableName + "';";
                getWritableDatabase().execSQL(sql_clear_table);
            }
        }
    }
}
