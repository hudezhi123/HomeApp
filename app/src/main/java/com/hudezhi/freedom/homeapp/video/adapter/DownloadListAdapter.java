package com.hudezhi.freedom.homeapp.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.video.impl.DownloadObserver;

/**
 * Created by boy on 2017/8/25.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DownloadObserver mDownloadObserver;
    private Context mContext;

    public DownloadListAdapter(Context context) {
        this.mContext = context;
    }

    public void setDownloadObserver(DownloadObserver downloadObserver) {
        mDownloadObserver = downloadObserver;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.download_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Holder){

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
