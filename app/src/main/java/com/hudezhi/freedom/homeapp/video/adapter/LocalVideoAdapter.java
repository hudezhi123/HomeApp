package com.hudezhi.freedom.homeapp.video.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.video.bean.LocalVideoBean;
import com.hudezhi.freedom.homeapp.video.impl.ItemClickCallBack;
import com.hudezhi.freedom.homeapp.video.utils.ThumbnailUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudezhi on 2017/3/12.
 */
public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.LocalHolder> {
    private Context context;
    private List<LocalVideoBean> list;
    private ItemClickCallBack itemClickCallBack;

    public void setItemClickCallBack(ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public LocalVideoAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<LocalVideoBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(LocalVideoBean localVideo) {
        this.list.add(localVideo);
        notifyItemInserted(0);
    }

    public void deleteItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public List<LocalVideoBean> getList() {
        return list;
    }

    @Override
    public LocalVideoAdapter.LocalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item_layout, parent, false);
        LocalHolder holder = new LocalHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LocalHolder holder, int position) {
        LocalVideoBean localVideo = list.get(position);
        if (holder instanceof LocalHolder) {
            SharedPreferences share = context.getSharedPreferences("record", Context.MODE_PRIVATE);
            long time = share.getLong(localVideo.getName(), 0);
            if (time != 0 && time != 1) {
                holder.seenTime.setText(formatTime(localVideo.getTimeSeenLength()));
            } else {
                holder.seenTime.setVisibility(View.GONE);
            }
            holder.timeLength.setText(formatTime(localVideo.getTimeLength()));
            holder.capcity.setText(formatCapcity(localVideo.getCapcity()) + "M");
            holder.name.setText(localVideo.getName());
            new ThumbnailUtils(context).display(localVideo.getPath(), holder.preThumbnail);
        }
    }

    /**
     * 将时间格式化为00:00:00
     *
     * @param time
     * @return
     */
    private String formatTime(long time) {
        time /= 1000;
        int seconde = (int) (time % 60);
        int minute = (int) (time / 60 % 60);
        int hour = (int) (time / 60 / 60 % 12);
        return formatMM(hour) + ":" + formatMM(minute) + ":" + formatMM(seconde);
    }

    private String formatMM(int data) {
        if (data >= 10) {
            return data + "";
        }
        return "0" + data;
    }

    /**
     * 把时间转化成为 M为单位
     *
     * @return
     */
    private String formatCapcity(long capcity) {
        return String.format("%.1f", capcity * 1.00f / 1024 / 1024);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class LocalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView seenTime, name, timeLength, capcity;
        ImageView preThumbnail;

        public LocalHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_name_video_item);
            timeLength = (TextView) itemView.findViewById(R.id.text_time_length_video_item);
            capcity = (TextView) itemView.findViewById(R.id.text_capcity_video_item);
            seenTime = (TextView) itemView.findViewById(R.id.text_video_time_seen_item);
            preThumbnail = (ImageView) itemView.findViewById(R.id.img_video_pre_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickCallBack != null) {
                itemClickCallBack.onItemClick(v, getLayoutPosition());
            }
        }
    }
}

