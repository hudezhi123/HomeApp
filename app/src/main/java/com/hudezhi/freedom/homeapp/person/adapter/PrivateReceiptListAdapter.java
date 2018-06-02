package com.hudezhi.freedom.homeapp.person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.person.bean.FormContentInfo;

import java.util.List;

/**
 * Created by boy on 2017/8/10.
 */

public class PrivateReceiptListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FormContentInfo> infoList;

    public PrivateReceiptListAdapter(Context context, List<FormContentInfo> infoList) {
        mContext = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public FormContentInfo getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return infoList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.receipt_list_item, parent, false);
            holder = new Holder();
            holder.textGoStartDate = (TextView) convertView.findViewById(R.id.text_go_start_date);
            holder.textGoStartPlace = (TextView) convertView.findViewById(R.id.text_go_start_place);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        FormContentInfo info = getItem(position);
        holder.textGoStartPlace.setText(info.getGoStartPlace());
        holder.textGoStartDate.setText(info.getGoStartDate());
        return convertView;
    }

    private class Holder {
        TextView textGoStartDate;
        TextView textGoStartPlace;
    }
}
