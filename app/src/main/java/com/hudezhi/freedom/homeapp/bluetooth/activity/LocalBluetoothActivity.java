package com.hudezhi.freedom.homeapp.bluetooth.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;

public class LocalBluetoothActivity extends Activity implements View.OnClickListener {

    private BluetoothAdapter bluetooth;

    private TextView textLocalBluetoothName;
    private TextView textLocalBluetoothAddress;
    private ImageView imgOpenBluetooth;
    private ImageView imgAllowDiscovery;
    private TextView textScan;

    private static final int REQUEST_BLUETOOTH_ENABLE_CODE = 1;
    private static final int REQUEST_BLUETOOTH_DISCOVERY_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_bluetooth);
        initBluetoothAdapter();
        initView();
    }

    private void initView() {
        textLocalBluetoothName = (TextView) findViewById(R.id.text_local_bluetooth_name);
        textLocalBluetoothAddress = (TextView) findViewById(R.id.text_local_bluetooth_address);
        imgOpenBluetooth = (ImageView) findViewById(R.id.img_turn_on_bluetooth);
        imgAllowDiscovery = (ImageView) findViewById(R.id.img_allow_be_discovery);
        textScan = (TextView) findViewById(R.id.text_scan);
        imgOpenBluetooth.setOnClickListener(this);
        imgAllowDiscovery.setOnClickListener(this);
        textScan.setOnClickListener(this);
    }

    private void initBluetoothAdapter() {
        bluetooth = BluetoothAdapter.getDefaultAdapter();
    }

    // 当蓝牙开启时，更新UI
    private void initBluetoothUI() {
        textLocalBluetoothName.setText(bluetooth.getName());
        textLocalBluetoothAddress.setText(bluetooth.getAddress());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_BLUETOOTH_ENABLE_CODE:
                if (resultCode == RESULT_OK) {
                    initBluetoothUI();
                }
                break;
            case REQUEST_BLUETOOTH_DISCOVERY_CODE:
                if (resultCode == RESULT_OK) {

                } else if (resultCode == RESULT_CANCELED) {

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_turn_on_bluetooth:
                onOpenBluetooth();
                initBluetoothUI();
                break;
            case R.id.img_allow_be_discovery:
                onAllowBeDiscovery();
                break;
            case R.id.text_scan:
                findRemoteBluetooth();
                break;
        }
    }

    private void onOpenBluetooth() {
        if (!bluetooth.isEnabled()) {
            // 蓝牙未启动，请求启动
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_BLUETOOTH_ENABLE_CODE);
        }
        return;
    }

    private void onAllowBeDiscovery() {
        onOpenBluetooth();
        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), REQUEST_BLUETOOTH_DISCOVERY_CODE);
    }

    private void findRemoteBluetooth() {

    }
}
