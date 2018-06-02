package com.hudezhi.freedom.homeapp.video.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.style.LineHeightSpan;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


import com.hudezhi.freedom.homeapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.transform.Templates;

import static android.R.attr.width;

/**
 * Created by hudezhi on 2017/3/13.
 * 缩略图工具类，采用三级缓存机制展示缩略图
 * 1、内存缓存
 * 2、sdcard缓存
 * 3、网络缓存
 */

public class ThumbnailUtils {
    private Context context;
    private MyLrucache mLrucache;

    private static int count = 0;

    public ThumbnailUtils(Context context) {

        this.context = context;
        int memoryAount = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
        if (mLrucache == null) {
            mLrucache = new MyLrucache(memoryAount);
        }
    }

    /**
     * 内存缓存,优先加载，速度最快
     */
    public class MyLrucache extends android.support.v4.util.LruCache<String, Bitmap> {

        /**
         * 定义强引用缓存区的大小
         *
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public MyLrucache(int maxSize) {
            super(maxSize);
        }

        /**
         * 返回每个图片大小
         *
         * @param key
         * @param value
         * @return
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
        }
    }


    /**
     * @param videoPath 视频路径名
     * @param img
     */
    public void display(String videoPath, ImageView img) {
        Bitmap bitmap = null;
        //从内存获取
        if (mLrucache != null) {
            bitmap = mLrucache.get(MD5Encoder(videoPath));
            if (bitmap != null) {
                img.setImageBitmap(bitmap);
                return;
            }
        }
        //从sdcard获取
        bitmap = getThumbnailFromSDCard(videoPath);
        if (bitmap != null) {
            img.setImageBitmap(bitmap);
            return;
        }
        //从视频上直接获取缩略图
        getFromVideo(videoPath, img);

    }

    private void getFromVideo(String videoPath, ImageView img) {
        //从流媒体中获取
        new ThumbnailAsynctask(img).execute(videoPath);
    }

    public class ThumbnailAsynctask extends AsyncTask<String, Void, Bitmap> {
        private ImageView thumbnailIMG;


        public ThumbnailAsynctask(ImageView thumbnailIMG) {
            this.thumbnailIMG = thumbnailIMG;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            /**
             *  加载图片：从内存中获取，如果没有从sdcard中获取、sdcard中没有则通过流媒体直接获得
             */
            Log.i("---count---", "" + (++count));
            Bitmap bitmap = getVideoThumbnail(params[0]);
            Bitmap tempBit = null;
            String videoPath = MD5Encoder(params[0]);
            boolean isSaved = saveThumbnialToSDCard(videoPath, bitmap);
            if (isSaved) {
                File file = new File(context.getExternalCacheDir(), videoPath);
                tempBit = BitmapFactory.decodeFile(file.getAbsolutePath());
                mLrucache.put(videoPath, tempBit);
                return tempBit;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                thumbnailIMG.setImageBitmap(bitmap);
            } else {
                thumbnailIMG.setImageResource(R.mipmap.video_logo);
            }
        }

        /**
         * 获取到视频的缩略图
         */
        private Bitmap getVideoThumbnail(String videoPath) {
            //获取流媒体文件信息
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoPath);
            Bitmap bitmap = retriever.getFrameAtTime();
            retriever.release();
            return bitmap;
        }
    }

    /**
     * 对图片进行二次采样
     */
    public static Bitmap createImgThunbmail(String filePath) {
        //获取图片采样参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds是否指采样边界（以便得到bitmap的宽高）
        options.inJustDecodeBounds = true;
        //把设置好的采样参数应用到具体的采样过程中
        BitmapFactory.decodeFile(filePath, options);
        //得到新旧bitmap的宽高比
        //bitmap缩小的倍数
        options.inSampleSize = 10;
        //第二次采样
        options.inJustDecodeBounds = false;
        //设置像素点格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * @param videoPath 文件路径名
     * @param bitmap
     * @return
     */
    private boolean saveThumbnialToSDCard(String videoPath, Bitmap bitmap) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //文件缓存路径目录
            File cacheFile = context.getExternalCacheDir();
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            //文件绝对路径
            File file = new File(cacheFile, videoPath);
            if (!file.exists()) {
                BufferedOutputStream out = null;
                try {
                    out = new BufferedOutputStream(new FileOutputStream(file));
                    //将bitmap转化成为流写到相应路径下
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    out.flush();
                    out.close();
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    /**
     * 从sdcard获取缩略图
     *
     * @param videoPath
     * @return
     */
    private Bitmap getThumbnailFromSDCard(String videoPath) {
        String newName = MD5Encoder(videoPath);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //文件缓存路径目录
            File cacheFile = context.getExternalCacheDir();
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
                return null;
            }
            File file = new File(cacheFile, newName);
            if (file.exists()) {
                Bitmap bitmap = createImgThunbmail(file.getAbsolutePath());
                if (bitmap != null) {
                    mLrucache.put(newName, bitmap);
                    return bitmap;
                }
            }
        }
        return null;
    }


    /**
     * 将video的路径名转化成为缩略图的name，为了防止出现相同，使用MD5
     * MD5加密对字节进行加密
     */
    private static final char hexDigsits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String MD5Encoder(String videoPath) {
        //获取字节原文
        byte[] initData = videoPath.getBytes();

        try {
            //选择加密方式
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //把摘要对象中的数据设置为我们的内容
            messageDigest.update(initData);
            //拿到MD5计算的结果,
            byte[] message = messageDigest.digest();
            //我们把结果转化为16进制的格式
            char[] chararray = new char[message.length * 2];
            int k = 0;
            for (int i = 0; i < message.length; i++) {
                byte temp = message[i];
                chararray[k++] = hexDigsits[temp >>> 4 & 0xf];
                chararray[k++] = hexDigsits[temp & 0xf];
            }
            return new String(chararray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
