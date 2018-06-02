package com.hudezhi.freedom.homeapp.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudezhi.freedom.homeapp.person.bean.PrivateModuleItem;

import java.util.List;

/**
 * Created by boy on 2017/8/10.
 */

public class PrivateModuleRAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PrivateModuleItem> moduleItemList;
    private Context mContext;
    private LayoutInflater inflater;

    public PrivateModuleRAdapter(Context context, List<PrivateModuleItem> moduleItemList) {
        this.mContext = context;
        this.moduleItemList = moduleItemList;
        inflater = LayoutInflater.from(mContext);
    }

    public void addModuleItem(int position) {
        notifyItemInserted(position);
    }

    public void removeModuleItem(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return moduleItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return moduleItemList.get(position).getId();
    }

    private class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);

        }
    }
}
