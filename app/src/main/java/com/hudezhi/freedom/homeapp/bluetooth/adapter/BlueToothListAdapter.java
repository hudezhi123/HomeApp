package com.hudezhi.freedom.homeapp.bluetooth.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.bluetooth.bean.DeviceInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.BluetoothDevice.BOND_BONDED;

/**
 * Created by boy on 2017/8/21.
 */

public class BlueToothListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<DeviceInfo> blueList = null;
    protected Context context;

    public BlueToothListAdapter(Context context) {
        this.context = context;
        blueList = new ArrayList<>();
    }

    public void addItem(DeviceInfo deviceInfo) {
        if (blueList.contains(deviceInfo)) {
            return;
        }
        blueList.add(deviceInfo);
        notifyItemChanged(blueList.size() - 1, deviceInfo);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.device_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Holder) {
            final DeviceInfo info = blueList.get(position);
            if (info == null) {
                return;
            }
            ((Holder) holder).textName.setText(info.getName());
            ((Holder) holder).textAddress.setText(info.getAddress());
            if (isBond(info)) {
                ((Holder) holder).btnBindState.setText("解绑");
            } else {
                ((Holder) holder).btnBindState.setText("绑定");
            }

            ((Holder) holder).btnBindState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBond(info)) {
                        unBindDevice(info);
                    } else {
                        bindDevice(info);
                    }
                }
            });
        }
    }

    private void unBindDevice(DeviceInfo info) {
        Class bluetoothDevice = BluetoothDevice.class;
        Method removeBond = null;
        boolean isSuccess;
        try {
            removeBond = bluetoothDevice.getMethod("removeBond");
            removeBond.setAccessible(true);
            removeBond.invoke(info.getDevice());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * BluetoothDevice 绑定方法是开放的，取消绑定的方法是隐藏的，只对系统app开放，
     * 所以需要用到反射
     *
     * @param info
     */
    private void bindDevice(DeviceInfo info) {
        BluetoothDevice device = info.getDevice();
        device.createBond();
    }

    private boolean isBond(DeviceInfo info) {
        int state = info.getDevice().getBondState();
        if (state == BluetoothDevice.BOND_BONDED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return blueList == null ? 0 : blueList.size();
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textName;
        TextView textAddress;
        Button btnBindState;

        public Holder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_device_name);
            textAddress = (TextView) itemView.findViewById(R.id.text_device_address);
            btnBindState = (Button) itemView.findViewById(R.id.btn_bind_state);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(blueList.get(getLayoutPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(DeviceInfo deviceInfo);
    }
}
