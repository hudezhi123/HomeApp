package com.hudezhi.freedom.homeapp.network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.network.bean.WifiItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/9/30.
 */

public class WifiItemAdapter extends BaseAdapter {
    private List<WifiItemInfo> wifiItemInfoList;
    private Context context;
    private LayoutInflater inflater;

    public WifiItemAdapter(Context context) {
        this.context = context;
        wifiItemInfoList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<WifiItemInfo> infoList) {
        this.wifiItemInfoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return wifiItemInfoList == null ? 0 : wifiItemInfoList.size();
    }

    @Override
    public WifiItemInfo getItem(int position) {
        return wifiItemInfoList.get(position);
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
            convertView = inflater.inflate(R.layout.wifi_item_layout, parent, false);
            holder.signalLevel = (ImageView) convertView.findViewById(R.id.img_signal_level);
            holder.name = (TextView) convertView.findViewById(R.id.text_wifi_name);
            holder.state = (TextView) convertView.findViewById(R.id.text_wifi_connect_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WifiItemInfo info = getItem(position);
        holder.name.setText(info.getSsId());
        String ic_name = "ic_sinal_level" + info.getSignalLevel();
        if (info.isConnect()) {
            holder.state.setText("已连接");
        } else {
            holder.state.setText("");
        }
        int id = context.getResources().getIdentifier(ic_name, "mipmap", context.getPackageName());
        holder.signalLevel.setImageResource(id);
        return convertView;
    }

    private class ViewHolder {
        TextView name, state;
        ImageView signalLevel;
    }
}
