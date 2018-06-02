package com.hudezhi.freedom.homeapp.bluetooth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.bluetooth.bean.HAPMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/8/21.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<HAPMessage> msgList;


    public ChatAdapter(Context context) {
        this.context = context;
        msgList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void addMsgItem(HAPMessage message) {
        msgList.add(message);
        notifyItemChanged(msgList.size() - 1, message);
    }

    @Override
    public int getItemViewType(int position) {
        HAPMessage message = msgList.get(position);
        return message.getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HAPMessage.VIEW_TYPE_RECEIVE:
                View receive_view = inflater.inflate(R.layout.receive_message_layout, parent, false);
                return new ReceiveHolder(receive_view);
            case HAPMessage.VIEW_TYPE_SEND:
                View send_view = inflater.inflate(R.layout.send_message_layout, parent, false);
                return new SendHolder(send_view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HAPMessage message = msgList.get(position);
        if (holder instanceof SendHolder) {
            if (TextUtils.isEmpty(message.getName())) {
                ((SendHolder) holder).textName.setText("未命名");
            } else {
                ((SendHolder) holder).textName.setText(message.getName());
            }
            ((SendHolder) holder).textContent.setText(message.getContent());
            switch (message.getMsgType()) {
                case HAPMessage.MSG_TYPE_TXT:
                    ((SendHolder) holder).textContent.setText(message.getContent());
                    break;
                case HAPMessage.MSG_TYPE_IMG:

                    break;
                case HAPMessage.MSG_TYPE_AUDIO:

                    break;
                case HAPMessage.MSG_TYPE_VIDEO:

                    break;
            }
        } else if (holder instanceof ReceiveHolder) {
            if (TextUtils.isEmpty(message.getName())) {
                ((ReceiveHolder) holder).textName.setText("未命名");
            } else {
                ((ReceiveHolder) holder).textName.setText(message.getName());
            }
            ((ReceiveHolder) holder).textContent.setText(message.getContent());
            switch (message.getMsgType()) {
                case HAPMessage.MSG_TYPE_TXT:
                    ((ReceiveHolder) holder).textContent.setText(message.getContent());
                    break;
                case HAPMessage.MSG_TYPE_IMG:

                    break;
                case HAPMessage.MSG_TYPE_AUDIO:

                    break;
                case HAPMessage.MSG_TYPE_VIDEO:

                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return msgList == null ? 0 : msgList.size();
    }


//    private class Holder extends RecyclerView.ViewHolder {
//        TextView textName;
//        TextView textContent;
//
//        public Holder(View itemView) {
//            super(itemView);
//            textName = (TextView) itemView.findViewById(R.id.text_blue_name_user_send);
//            textContent = (TextView) itemView.findViewById(R.id.text_blue_content_user_send);
//        }
//    }

    private class SendHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textContent;

        public SendHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_blue_name_user_send);
            textContent = (TextView) itemView.findViewById(R.id.text_blue_content_user_send);
        }
    }

    private class ReceiveHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textContent;

        public ReceiveHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_blue_name_user_receive);
            textContent = (TextView) itemView.findViewById(R.id.text_blue_content_user_receive);
        }
    }
}
