package com.hudezhi.freedom.homeapp.audio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.bean.MusicInfo;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/6/2.
 */

public class LocalMusicAdapter extends BaseAdapter {

    private List<MusicInfo> list;
    private Context context;

    public LocalMusicAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<MusicInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<MusicInfo> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MusicInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.music_info_layout, parent, false);
            holder.imgAlbum = (ImageView) convertView.findViewById(R.id.img_album_icon_music_info);
            holder.textSongName = (TextView) convertView.findViewById(R.id.text_song_name_music_info);
            holder.textSingAndSong = (TextView) convertView.findViewById(R.id.text_singer_and_song_music_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MusicInfo musicInfo = getItem(position);
        holder.textSongName.setText(musicInfo.songName);
        holder.textSingAndSong.setText(musicInfo.artistName);
        // TODO: 2017/6/2  为img设置值
//        Bitmap bitmap = MediaUtils.getArtWork(context, musicInfo.id, musicInfo.albumId, true, true);
//        Bitmap bitmap = MediaUtils.getArtWork(context, musicInfo.url);
//        String  path=MediaUtils.getAlbumArt((int) musicInfo.albumId);
//        Bitmap bitmap=BitmapFactory.decodeFile(path);
//        if (bitmap != null) {
//            holder.imgAlbum.setImageBitmap(bitmap);
//            bitmap.recycle();
//        }else{
//            holder.imgAlbum.setImageResource(R.mipmap.music_icon_blue);
//        }
        if (!TextUtils.isEmpty(musicInfo.albumIMGUri)) {
            Bitmap bitmap = MediaUtils.getThumbnail(musicInfo.albumIMGUri);
            if (bitmap != null) {
                holder.imgAlbum.setImageBitmap(bitmap);
            } else {
                holder.imgAlbum.setImageResource(R.mipmap.video_logo);
            }
        } else {
            holder.imgAlbum.setImageResource(R.mipmap.video_logo);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imgAlbum;
        TextView textSongName;
        TextView textSingAndSong;
    }
}
