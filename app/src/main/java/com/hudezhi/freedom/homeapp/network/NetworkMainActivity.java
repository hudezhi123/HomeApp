package com.hudezhi.freedom.homeapp.network;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.live_video.utils.ActivityContainer;

public class NetworkMainActivity extends Activity implements View.OnClickListener {
    private Button connectivity, wifi, wifiP2p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        connectivity = (Button) findViewById(R.id.btn_connectivity_main);
        wifi = (Button) findViewById(R.id.btn_wifi_main);
        wifiP2p = (Button) findViewById(R.id.btn_wifiP2p_main);
        connectivity.setOnClickListener(this);
        wifi.setOnClickListener(this);
        wifiP2p.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_connectivity_main:
                intent.setClass(this, ConnectivityActivity.class);
                break;
            case R.id.btn_wifi_main:
                intent.setClass(this, WifiActivity.class);
                break;
            case R.id.btn_wifiP2p_main:
                intent.setClass(this, WifiP2pActivity.class);
                break;
        }
        startActivity(intent);
    }
}
