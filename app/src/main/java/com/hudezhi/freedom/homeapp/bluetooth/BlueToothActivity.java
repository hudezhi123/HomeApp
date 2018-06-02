package com.hudezhi.freedom.homeapp.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.bluetooth.adapter.BlueToothListAdapter;
import com.hudezhi.freedom.homeapp.bluetooth.bean.DeviceInfo;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.ArrayList;

public class BlueToothActivity extends Activity implements View.OnClickListener, BlueToothListAdapter.OnItemClickListener {

    private BluetoothAdapter bluetoothAdapter;
    public static final int REQUEST_BLUE_TOOTH_PERMISSION = 1;
    public static final int REQUEST_BLUE_TOOTH_OPEN = 2;
    public static final int REQUEST_BLUE_TOOTH_DISCOVERY = 3;

    public static final String BLUE_TOOTH_STATE_RECEIVER = BluetoothAdapter.ACTION_STATE_CHANGED;

    public static final String BLUE_TOOTH_ALLOW_BE_DISCOVERY_RECEIVER = BluetoothAdapter.ACTION_SCAN_MODE_CHANGED;

    public static final String BLUE_TOOTH_START_DISCOVERY_RECEIVER = BluetoothAdapter.ACTION_DISCOVERY_STARTED;

    public static final String BLUE_TOOTH_FIND_RECEIVER = BluetoothDevice.ACTION_FOUND;

    public static final String BLUE_TOOTH_END_DISCOVERY_RECEIVER = BluetoothAdapter.ACTION_DISCOVERY_FINISHED;

    public static final String BLUE_TOOTH_BOND_STATE_CHANGED = BluetoothDevice.ACTION_BOND_STATE_CHANGED;

    private ArrayList<DeviceInfo> deviceList = null;

    private BlueToothBroadcastReceiver receiver;

    private String address;
    private String name;
    @BindView(id = R.id.text_name_blue_tooth)
    private TextView textName;
    @BindView(id = R.id.text_address_blue_tooth)
    private TextView textAddress;
    @BindView(id = R.id.btn_open_blue_tooth)
    private Button btnOpen;
    @BindView(id = R.id.btn_allow_discovery)
    private Button btnAllow;
    @BindView(id = R.id.btn_start_search_blue_tooth)
    private Button startSearch;
    @BindView(id = R.id.btn_cancel_searcch_discovery)
    private Button cancelSearch;
    @BindView(id = R.id.listView_bluetooth_device)
    private RecyclerView recyclerView;

    private BlueToothListAdapter adapter;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        AnnotateUtil.initBindView(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUE_TOOTH_OPEN);
            }
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        init();
        initView();
    }

    private void register() {
        receiver = new BlueToothBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BLUE_TOOTH_ALLOW_BE_DISCOVERY_RECEIVER);
        filter.addAction(BLUE_TOOTH_END_DISCOVERY_RECEIVER);
        filter.addAction(BLUE_TOOTH_START_DISCOVERY_RECEIVER);
        filter.addAction(BLUE_TOOTH_STATE_RECEIVER);
        filter.addAction(BLUE_TOOTH_FIND_RECEIVER);
        filter.addAction(BLUE_TOOTH_BOND_STATE_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        register();
    }

    @Override
    protected void onPause() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onPause();

    }

    @Override
    public void onItemClick(DeviceInfo info) {
        Intent intent = new Intent(BlueToothActivity.this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DeviceInfo", info);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class BlueToothBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String actionName = intent.getAction();
            if (TextUtils.isEmpty(actionName)) {
                return;
            } else {
                switch (actionName) {
                    case BLUE_TOOTH_STATE_RECEIVER:

                        break;
                    case BLUE_TOOTH_ALLOW_BE_DISCOVERY_RECEIVER:

                        break;
                    case BLUE_TOOTH_END_DISCOVERY_RECEIVER:

                        break;
                    case BLUE_TOOTH_START_DISCOVERY_RECEIVER:

                        break;
                    case BLUE_TOOTH_FIND_RECEIVER: {
                        String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        DeviceInfo info = new DeviceInfo();
                        info.setDevice(device);
                        info.setAddress(device.getAddress());
                        info.setName(name);
                        adapter.addItem(info);
                        deviceList.add(info);
                    }
                    break;
                    case BLUE_TOOTH_BOND_STATE_CHANGED: {
                        Log.i("---", "");
                        String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        DeviceInfo info = new DeviceInfo();
                        info.setDevice(device);
                        info.setAddress(device.getAddress());
                        info.setName(name);
                        replace(info);
                    }
                    break;
                }
            }
        }
    }

    private void replace(DeviceInfo deviceInfo) {
        for (int i = 0; i < deviceList.size(); i++) {
            DeviceInfo info = deviceList.get(i);
            if (deviceInfo.getAddress().equals(info.getAddress())) {
                adapter.notifyItemChanged(i, deviceInfo);
            } else {
                continue;
            }
        }
    }

    private void init() {
        deviceList = new ArrayList<>();
        adapter = new BlueToothListAdapter(this);
        manager = new LinearLayoutManager(this);

        btnOpen.setOnClickListener(this);
        btnAllow.setOnClickListener(this);
        startSearch.setOnClickListener(this);
        cancelSearch.setOnClickListener(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
    }

    private void initView() {
        boolean isEnable = bluetoothAdapter.isEnabled();
        if (isEnable) {
            address = bluetoothAdapter.getAddress();
            name = bluetoothAdapter.getName();
            textAddress.setText("地址：" + address);
            textName.setText("蓝牙：" + name);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_BLUE_TOOTH_PERMISSION:

                break;
            case REQUEST_BLUE_TOOTH_OPEN:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_BLUE_TOOTH_OPEN:
                address = bluetoothAdapter.getAddress();
                name = bluetoothAdapter.getName();
                textAddress.setText("地址：" + address);
                textName.setText("蓝牙：" + name);
                break;
            case REQUEST_BLUE_TOOTH_DISCOVERY:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "取消了", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_OK) {

                }
                break;
        }
    }

    private void openBlueTooth() {
        boolean isEnable = bluetoothAdapter.isEnabled();
        if (isEnable) {
            Toast.makeText(this, "已经启动", Toast.LENGTH_SHORT).show();
            address = bluetoothAdapter.getAddress();
            name = bluetoothAdapter.getName();
            textAddress.setText("地址：" + address);
            textName.setText("蓝牙：" + name);
        } else {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_BLUE_TOOTH_OPEN);
        }
    }

    private void allowDiscovery() {
        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), REQUEST_BLUE_TOOTH_DISCOVERY);
    }

    private void cancelDiscovery() {
        if (bluetoothAdapter.isEnabled() && bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        } else {
            return;
        }
    }

    private void startDiscovery() {
        if (bluetoothAdapter.isEnabled() && !bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.startDiscovery();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_blue_tooth:
                openBlueTooth();
                break;
            case R.id.btn_allow_discovery:
                allowDiscovery();
                break;
            case R.id.btn_start_search_blue_tooth:
                startDiscovery();
                break;
            case R.id.btn_cancel_searcch_discovery:
                cancelDiscovery();
                break;
        }
    }
}
