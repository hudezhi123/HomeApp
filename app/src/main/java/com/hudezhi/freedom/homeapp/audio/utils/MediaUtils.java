package com.hudezhi.freedom.homeapp.audio.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.util.SparseArray;

import com.hudezhi.freedom.homeapp.MainApplication;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.bean.Constant;
import com.hudezhi.freedom.homeapp.audio.bean.ImageInfo;
import com.hudezhi.freedom.homeapp.audio.bean.MusicFileInfo;
import com.hudezhi.freedom.homeapp.audio.bean.MusicInfo;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by boy on 2017/6/2.
 */

public class MediaUtils {

    public static HashMap<Long, Integer> hashSearch = new HashMap<>();
    public static Context mContext;

    public static MusicInfo getMusicInfo(Context context, long _id) {

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media._ID + "=" + _id, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        MusicInfo musicInfo = null;
        if (cursor.moveToNext()) {
            musicInfo = new MusicInfo();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));   //the file's path
            if (isMusic != 0) {   //only add music into the list
                musicInfo.id = id;
                musicInfo.songName = title;
                musicInfo.artistName = artist;
                musicInfo.album = album;
                musicInfo.albumId = albumId;
                musicInfo.duration = duration;
                musicInfo.size = size;
                musicInfo.url = url;
            }
        }
        cursor.close();
        return musicInfo;
    }

    public static ArrayList<MusicInfo> getMusicInfoList() {
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.DURATION + ">=100000", null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        ArrayList<MusicInfo> musicInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            MusicInfo musicInfo = new MusicInfo();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));   //the file's path
            if (isMusic != 0) {   //only add music into the list
                musicInfo.id = id;
                musicInfo.songName = title;
                musicInfo.artistName = artist;
                musicInfo.album = album;
                musicInfo.albumId = albumId;
                musicInfo.duration = duration;
                musicInfo.size = size;
                musicInfo.url = url;
                musicInfos.add(musicInfo);
            }
        }
        cursor.close();
        return musicInfos;
    }

    public static List<ImageInfo> getImageInfoList(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        List<ImageInfo> imgInfoList = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo._ID = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                imageInfo.Title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                imageInfo.DisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                imageInfo.Description = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                imageInfo.Uri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imageInfo.BucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                imageInfo.BucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                imgInfoList.add(imageInfo);
            }
        }
        cursor.close();
        return imgInfoList;
    }

    public static HashMap<String, ImageInfo> getImageInfoList() {
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        HashMap<String, ImageInfo> hashInfos = new HashMap<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String BucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                if (!"Cover".equals(BucketDisplayName)) {
                    continue;
                } else {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo._ID = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    imageInfo.Title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                    imageInfo.DisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    imageInfo.Description = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                    imageInfo.Uri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    imageInfo.BucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                    imageInfo.BucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    String[] depart = departName(imageInfo.DisplayName);
                    imageInfo.ARTIST = depart[0];
                    imageInfo.ALBUM = depart[1];
                    hashInfos.put(imageInfo.Title, imageInfo);
                }

            }
        }
        cursor.close();
        return hashInfos;
    }

    public static HashMap<String, MusicFileInfo> getImageInfoList(Context context, String dir) {
        HashMap<String, MusicFileInfo> musicMap = new HashMap<>();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootDir = Environment.getExternalStorageDirectory();
            File desPath = new File(rootDir, dir);
            if (!desPath.exists()) {
                desPath.mkdirs();
                return null;
            } else {
                File[] musicArtFileList = desPath.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        return filename.endsWith("JPG") && filename.endsWith("jpg");
                    }
                });
                for (File file : musicArtFileList) {
                    MusicFileInfo musicFileInfo = new MusicFileInfo();

                    String fileName = file.getName();
                    if (!TextUtils.isEmpty(fileName)) {
                        String departName[] = fileName.split(" - ");
                        musicFileInfo.Artist = departName[0];
                        musicFileInfo.FileName = departName[1];
                    }
                    musicFileInfo.Path = file.getAbsolutePath();
                    musicMap.put(file.getName(), musicFileInfo);
                }
            }
        }
        return null;
    }

    /**
     * 将文件名分割成为singer和songName
     *
     * @param fileName
     * @return
     */
    private static String[] departName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        } else {
            return fileName.split(" - ");
        }
    }


    private static Bitmap getDefaultArtWork(Context context, boolean isSmall) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        if (isSmall) {
            return BitmapFactory.decodeStream(context.getResources().openRawResource(R.mipmap.video_logo), null, opts);
        }
        return BitmapFactory.decodeStream(context.getResources().openRawResource(R.mipmap.video_logo), null, opts);
    }

    public static Bitmap getArtWorkFromFile(Context context, long songId, long albumId) {
        Bitmap bm = null;
        if (albumId < 0 && songId < 0) {
            throw new IllegalArgumentException("Must specify an album or a songId");
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fd = null;
            if (albumId < 0) {
                Uri uri = Uri.parse(Constant.ALBUM_URI_START + songId + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            } else {
                Uri uri = ContentUris.withAppendedId(Uri.parse(Constant.ALBUM_URI_STRING), albumId);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            }
            options.inSampleSize = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            options.inSampleSize = 100;
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap getArtWork(Context context, long songId, long albumId, boolean allowDefault, boolean isSmall) {
        if (albumId < 0) {
            if (songId < 0) {
                Bitmap bitmap = getArtWorkFromFile(context, songId, -1);
                if (bitmap != null) {
                    return bitmap;
                }
            }
            if (allowDefault) {
                return getDefaultArtWork(context, isSmall);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(Uri.parse(Constant.ALBUM_URI_STRING), albumId);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in, null, options);
                if (isSmall) {
                    options.inSampleSize = 5;
                } else {
                    options.inSampleSize = 2;
                }
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return BitmapFactory.decodeStream(in, null, options);
            } catch (FileNotFoundException e) {
                Bitmap bm = getArtWorkFromFile(context, songId, -1);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowDefault) {
                            return getDefaultArtWork(context, isSmall);
                        }
                    }
                } else if (allowDefault) {
                    bm = getDefaultArtWork(context, isSmall);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static int computeSampleSize(BitmapFactory.Options options, int i) {
        return 0;
    }

    public static Bitmap getArtWork(Context context, String uriString) {
        Uri uri = Uri.fromFile(new File(uriString));
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, uri);
        byte[] artwork = retriever.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
        return bitmap;
    }

    public static String getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = mContext.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }


    public static boolean insertMusicInfoIToDB() {
        MusicDBHelper dbHelper = new MusicDBHelper(mContext);
//        dbHelper.clearTable(MusicDBHelper.TB_MY_MUSIC_INFO);
        try {
            dbHelper.getWritableDatabase().beginTransaction();
            List<MusicInfo> musicInfoList = getMusicInfoList();
            HashMap<String, ImageInfo> hashInfos = getImageInfoList();
            for (MusicInfo musicInfo : musicInfoList) {
                String DisplayName = musicInfo.artistName + " - " + musicInfo.album;
                ImageInfo imageInfo = hashInfos.get(DisplayName);
                if (imageInfo != null) {
                    musicInfo.albumIMGUri = imageInfo.Uri;
                    musicInfo.albumImgPath = imageInfo.Uri;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                musicInfo.recentPlayTime = format.format(System.currentTimeMillis());
                musicInfo.isPlayed = false;
                String sql = "insert into " + MusicDBHelper.TB_MY_MUSIC_INFO + " ("
                        + MusicDBHelper.SONG_ID + ", "
                        + MusicDBHelper.SINGER_NAME + ", "
                        + MusicDBHelper.SONG_NAME + ", "
                        + MusicDBHelper.ALBUM + ", "
                        + MusicDBHelper.ALBUM_ID + ", "
                        + MusicDBHelper.URI + ", "
                        + MusicDBHelper.SIZE + ", "
                        + MusicDBHelper.DURATION + ", "
                        + MusicDBHelper.IS_MUSIC + ", "
                        + MusicDBHelper.IS_RECENT + " , "
                        + MusicDBHelper.IS_FOCUS + ", "
                        + MusicDBHelper.ALBUM_IMG_PATH + ", "
                        + MusicDBHelper.ALBUM_IMG_URI + ", "
                        + MusicDBHelper.IS_PLAYED + ", "
                        + MusicDBHelper.RECENT_PLAY_TIME + ""
                        + ") values ("
                        + "'" + musicInfo.id + "',"
                        + "'" + musicInfo.artistName + "',"
                        + "'" + musicInfo.songName + "',"
                        + "'" + musicInfo.album + "',"
                        + "'" + musicInfo.albumId + "',"
                        + "'" + musicInfo.url + "',"
                        + "'" + musicInfo.size + "',"
                        + "'" + musicInfo.duration + "',"
                        + "'" + musicInfo.isMusic + "',"
                        + "'" + musicInfo.isRecent + "',"
                        + "'" + musicInfo.isFocus + "',"
                        + "'" + musicInfo.albumImgPath + "',"
                        + "'" + musicInfo.albumIMGUri + "',"
                        + "'" + musicInfo.isPlayed + "',"
                        + "'" + musicInfo.recentPlayTime + "'"
                        + ");";
                dbHelper.execSQL(sql);
            }
            dbHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            dbHelper.getWritableDatabase().endTransaction();
        }
    }

    public static MusicInfo getChooseItemFromDB(int songId) {
        MusicDBHelper dbHelper = new MusicDBHelper(mContext.getApplicationContext());
        String sql = "select * from " + MusicDBHelper.TB_MY_MUSIC_INFO + " where " + MusicDBHelper.SONG_ID + " = '" + songId + "';";
        Cursor cursor = dbHelper.querySQL(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.id = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.SONG_ID));
            musicInfo.artistName = cursor.getString(cursor.getColumnIndex(MusicDBHelper.SINGER_NAME));
            musicInfo.songName = cursor.getString(cursor.getColumnIndex(MusicDBHelper.SONG_NAME));
            musicInfo.album = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM));
            musicInfo.albumId = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.ALBUM_ID));
            musicInfo.duration = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.DURATION));
            musicInfo.size = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.DURATION));
            musicInfo.url = cursor.getString(cursor.getColumnIndex(MusicDBHelper.URI));
            musicInfo.isFocus = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_FOCUS)));
            musicInfo.isRecent = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_RECENT)));
            musicInfo.isMusic = cursor.getInt(cursor.getColumnIndex(MusicDBHelper.IS_MUSIC));
            musicInfo.albumIMGUri = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM_IMG_URI));
            musicInfo.albumImgPath = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM_IMG_PATH));
            musicInfo.isPlayed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_PLAYED)));
            musicInfo.recentPlayTime = cursor.getString(cursor.getColumnIndex(MusicDBHelper.RECENT_PLAY_TIME));
            cursor.close();
            return musicInfo;
        }
        return null;
    }

    public static MusicInfo getRecentItemFromDB() {
        MusicDBHelper dbHelper = new MusicDBHelper(mContext.getApplicationContext());
        String sql = "select * from " + MusicDBHelper.TB_MY_MUSIC_INFO + " order by " + MusicDBHelper.RECENT_PLAY_TIME + " desc;";
        Cursor cursor = dbHelper.querySQL(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.id = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.SONG_ID));
            musicInfo.artistName = cursor.getString(cursor.getColumnIndex(MusicDBHelper.SINGER_NAME));
            musicInfo.songName = cursor.getString(cursor.getColumnIndex(MusicDBHelper.SONG_NAME));
            musicInfo.album = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM));
            musicInfo.albumId = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.ALBUM_ID));
            musicInfo.duration = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.DURATION));
            musicInfo.size = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.DURATION));
            musicInfo.url = cursor.getString(cursor.getColumnIndex(MusicDBHelper.URI));
            musicInfo.isFocus = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_FOCUS)));
            musicInfo.isRecent = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_RECENT)));
            musicInfo.isMusic = cursor.getInt(cursor.getColumnIndex(MusicDBHelper.IS_MUSIC));
            musicInfo.albumIMGUri = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM_IMG_URI));
            musicInfo.albumImgPath = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM_IMG_PATH));
            musicInfo.isPlayed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_PLAYED)));
            musicInfo.recentPlayTime = cursor.getString(cursor.getColumnIndex(MusicDBHelper.RECENT_PLAY_TIME));
            cursor.close();
            return musicInfo;
        }
        return null;
    }


    public static List<MusicInfo> getDataFromDB() {
        MusicDBHelper dbHelper = new MusicDBHelper(mContext.getApplicationContext());
        String sql = "select * from " + MusicDBHelper.TB_MY_MUSIC_INFO + " order by  " + MusicDBHelper.DURATION + " desc;";
        List<MusicInfo> list = new ArrayList<>();
        Cursor cursor = dbHelper.querySQL(sql, null);
        int count = 0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.id = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.SONG_ID));
                musicInfo.artistName = cursor.getString(cursor.getColumnIndex(MusicDBHelper.SINGER_NAME));
                musicInfo.songName = cursor.getString(cursor.getColumnIndex(MusicDBHelper.SONG_NAME));
                musicInfo.album = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM));
                musicInfo.albumId = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.ALBUM_ID));
                musicInfo.duration = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.DURATION));
                musicInfo.size = cursor.getLong(cursor.getColumnIndex(MusicDBHelper.DURATION));
                musicInfo.url = cursor.getString(cursor.getColumnIndex(MusicDBHelper.URI));
                musicInfo.isFocus = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_FOCUS)));
                musicInfo.isRecent = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_RECENT)));
                musicInfo.isMusic = cursor.getInt(cursor.getColumnIndex(MusicDBHelper.IS_MUSIC));
                musicInfo.albumIMGUri = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM_IMG_URI));
                musicInfo.albumImgPath = cursor.getString(cursor.getColumnIndex(MusicDBHelper.ALBUM_IMG_PATH));
                musicInfo.isPlayed = Boolean.getBoolean(cursor.getString(cursor.getColumnIndex(MusicDBHelper.IS_PLAYED)));
                musicInfo.recentPlayTime = cursor.getString(cursor.getColumnIndex(MusicDBHelper.RECENT_PLAY_TIME));

                hashSearch.put(musicInfo.id, count++);
                list.add(musicInfo);
            }
            cursor.close();
            return list;
        }
        return null;
    }

    public static Bitmap getThumbnail(String filePath) {

        //获取图片采样参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds是否指采样边界（以便得到bitmap的宽高）
        options.inJustDecodeBounds = true;
        //把设置好的采样参数应用到具体的采样过程中
        BitmapFactory.decodeFile(filePath, options);
        //得到新旧bitmap的宽高比
        //bitmap缩小的倍数
        options.inSampleSize = 5;
        //第二次采样
        options.inJustDecodeBounds = false;
        //设置像素点格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(filePath, options);
    }
}
