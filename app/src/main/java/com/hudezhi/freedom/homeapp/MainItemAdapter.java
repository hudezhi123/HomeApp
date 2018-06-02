package com.hudezhi.freedom.homeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.List;

/**
 * Created by boy on 2017/7/28.
 */

public class MainItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnModuleItemClickListener moduleItemClickListener;

    public void setModuleItemClickListener(OnModuleItemClickListener moduleItemClickListener) {
        this.moduleItemClickListener = moduleItemClickListener;
    }

    public interface OnModuleItemClickListener {
        public void onItemClick(int position);
    }

    private Context mContext;
    private List<ModuleItem> moduleItemList;

    public MainItemAdapter(Context context, List<ModuleItem> moduleItemList) {
        mContext = context;
        this.moduleItemList = moduleItemList;
    }

    public void insertModuleItem(int position) {
        notifyItemInserted(position);
    }

    public void deleteModuleItem(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.module_item_layout, parent, false);
        return new ModuleHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModuleItem moduleItem = moduleItemList.get(position);
        ((ModuleHolder) holder).imgModuleIcon.setImageResource(moduleItem.getImgId());
        ((ModuleHolder) holder).textModuleName.setText(moduleItem.getModuleName());
    }

    @Override
    public int getItemCount() {
        return moduleItemList == null ? 0 : moduleItemList.size();
    }

    private class ModuleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgModuleIcon;
        TextView textModuleName;

        public ModuleHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgModuleIcon = (ImageView) itemView.findViewById(R.id.img_module_icon);
            textModuleName = (TextView) itemView.findViewById(R.id.text_module_name);
        }

        @Override
        public void onClick(View v) {
            if (moduleItemClickListener != null) {
                moduleItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }
}
