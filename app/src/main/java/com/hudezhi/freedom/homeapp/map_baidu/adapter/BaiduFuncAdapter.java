package com.hudezhi.freedom.homeapp.map_baidu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.map_baidu.bean.BaiduFuncInfo;

import java.util.List;

/**
 * Created by boy on 2017/7/31.
 */

public class BaiduFuncAdapter extends BaseAdapter {
    private Context mContext;
    private List<BaiduFuncInfo> infoList;

    public BaiduFuncAdapter(Context mContext, List<BaiduFuncInfo> infoList) {
        this.mContext = mContext;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }

    @Override
    public BaiduFuncInfo getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaiduFuncHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.baidu_func_module_item, parent, false);
            holder = new BaiduFuncHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.img_module_baidu);
            holder.textName = (TextView) convertView.findViewById(R.id.text_module_baidu);
            convertView.setTag(holder);
        } else {
            holder = (BaiduFuncHolder) convertView.getTag();
        }
        BaiduFuncInfo info = infoList.get(position);
        holder.textName.setText(info.getFuncName());
        holder.imgIcon.setImageResource(info.getImgId());
        return convertView;
    }

    private class BaiduFuncHolder {
        ImageView imgIcon;
        TextView textName;
    }
}
