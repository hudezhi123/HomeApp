package com.hudezhi.freedom.homeapp.network;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.network.adapter.WifiItemAdapter;
import com.hudezhi.freedom.homeapp.network.bean.WifiItemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WifiActivity extends Activity implements View.OnClickListener {


    private WifiManager wifiManager;
    private WifiHotBroadcast receiver;

    private Button btnOpen;
    private Button btnSearch;
    private Button btnScan;
    private ListView listView;
    private WifiItemAdapter adapter;
    private List<WifiItemInfo> wifiItemInfos;
    private static final int REQUEST_CODE_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_main);
        initWifiManager();
        initView();

//        testMethod();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview_wifi);
        adapter = new WifiItemAdapter(this);
        wifiItemInfos = new ArrayList<>();
        btnOpen = (Button) findViewById(R.id.btn_open_wifi);
        btnSearch = (Button) findViewById(R.id.btn_search_wifi);
        btnScan = (Button) findViewById(R.id.btn_scan_wifi);
        if (wifiManager.isWifiEnabled()) {
            btnOpen.setText(getResources().getString(R.string.close_wifi));
            btnSearch.setEnabled(true);
            btnSearch.setAlpha(1f);
        } else {
            btnOpen.setText(getResources().getString(R.string.open_wifi));
            btnSearch.setEnabled(false);
            btnSearch.setAlpha(0.8f);
        }
        btnOpen.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiItemInfo info = adapter.getItem(position);
                String ssId = "\"" + info.getSsId() + "\"";
                if (configMap.containsKey(ssId)) {
                    WifiConfiguration config = configMap.get(ssId);
                    connectConfigWifi(config);
                    wifiItemInfos.get(position).setConnect(true);
                    adapter.notifyDataSetChanged();
                } else {
                    WifiActivity.this.showDialogO(info.getSsId(), position);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                WifiItemInfo info = adapter.getItem(position);
                String ssId = "\"" + info.getSsId() + "\"";
                if (configMap.containsKey(ssId)) {
                    WifiConfiguration config = configMap.get(ssId);
                    wifiManager.disableNetwork(config.networkId);
                    wifiManager.saveConfiguration();
                    wifiItemInfos.get(position).setConnect(false);
                    adapter.notifyDataSetChanged();
                } else {

                }

                return true;
            }
        });
    }

    private AlertDialog.Builder mDialog = null;

    private void showDialogO(final String SSID, final int position) {
        mDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.connect_wifi_dialog_layout, null, false);
//            Button conn = (Button) view.findViewById(R.id.btn_connect_wifi);
        mDialog.setView(view);
        final TextView titleSSID = (TextView) view.findViewById(R.id.text_ssid_wifi);
        final EditText editPass = (EditText) view.findViewById(R.id.edit_pass_connect);
        titleSSID.setText(SSID);
        mDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton("连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pass = editPass.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(WifiActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = connectNotConfigWifi(SSID, pass, EncryptType.WIFICIPHER_WPA);
                    if (flag) {
                        wifiItemInfos.get(position).setConnect(true);
                        Toast.makeText(getApplicationContext(), "正在连接，请稍后", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "正在连接，请稍后", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mDialog.create();
        mDialog.show();
    }


    private void initWifiManager() {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver == null) {
            receiver = new WifiHotBroadcast();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiver, filter);
    }

    private void testMethod() {
        WifiInfo info = wifiManager.getConnectionInfo();
        Log.i("--", info.toString());
    }


    private class WifiHotBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
                boolean flag = checkPermission();
                if (flag) {
                    getScanResult();
                }
            }
        }
    }


    private void getScanResult() {
        if (wifiItemInfos != null && wifiItemInfos.size() > 0) {
            wifiItemInfos.clear();
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        WifiItemInfo info = null;
        for (ScanResult result : scanResults) {
            int level = WifiManager.calculateSignalLevel(result.level, 6);
            info = new WifiItemInfo();
            info.setSignalLevel(level);
            info.setSsId(result.SSID);
            info.setBssId(result.BSSID);
            info.setConnect(false);
            wifiItemInfos.add(info);
        }
        adapter.setList(wifiItemInfos);
    }

    /**
     * 加密类型
     */
    public enum EncryptType {
        WIFICIPHER_WEP,
        WIFICIPHER_WPA,
        WIFICIPHER_NOPASS,
        WIFICIPHER_INVALID
    }

    private HashMap<String, WifiConfiguration> configMap = null;

    private void getConfigurationWifi() {
        if (wifiItemInfos != null && wifiItemInfos.size() > 0) {
            wifiItemInfos.clear();
        }
        configMap = new HashMap<>();
        List<WifiConfiguration> wifiConfigurationList = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration : wifiConfigurationList) {
            configMap.put(configuration.SSID, configuration);
        }
    }

    /**
     * 连接已经配置的wifi
     *
     * @param config
     * @return
     */
    private boolean connectConfigWifi(WifiConfiguration config) {
        if (!isWifiTurnOn()) {
            return false;
        }
        while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            try {
                // 为了避免程序一直while循环，让它睡个100毫秒在检测……
                Thread.currentThread();
                Thread.sleep(100);
            } catch (InterruptedException ie) {
            }
        }
        boolean isSuccess = wifiManager.enableNetwork(config.networkId, true);
        wifiManager.saveConfiguration();
        return isSuccess;
    }


    /**
     * 无配置记录连接wifi
     *
     * @param SSID     the name of wanted wifi
     * @param password the password needed to connect
     * @param type     the encryption type of the wanted wifi
     * @return true or false
     */

    private boolean connectNotConfigWifi(String SSID, String password, EncryptType type) {
        if (!this.isWifiTurnOn()) {
            return false;
        }
        while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        WifiConfiguration wifiConfig = createWifiConfig(SSID, password, type);
        int netId = wifiManager.addNetwork(wifiConfig);
        boolean isSuccess = wifiManager.enableNetwork(netId, true);
        wifiManager.saveConfiguration();
        return isSuccess;
    }

    private WifiConfiguration createWifiConfig(String SSID, String password, EncryptType type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        if (type == EncryptType.WIFICIPHER_NOPASS) {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == EncryptType.WIFICIPHER_WEP) {
            config.wepKeys[0] = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == EncryptType.WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            // 用来判断加密方法。
            // 可选参数：LEAP只用于leap,
            // OPEN 被wpa/wpa2需要,
            // SHARED需要一个静态的wep key
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            // 用来判断加密方法。可选参数：CCMP,TKIP,WEP104,WEP40
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            // WifiConfiguration.KeyMgmt 键管理机制（keymanagerment），使用KeyMgmt 进行。
            // 可选参数IEEE8021X,NONE,WPA_EAP,WPA_PSK
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            // WifiConfiguration.PairwiseCipher 设置加密方式。
            // 可选参数 CCMP,NONE,TKIP
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            // WifiConfiguration.Protocol 设置一种协议进行加密。
            // 可选参数 RSN,WPA,
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA); // for WPA
            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN); // for WPA2
            // wifiConfiguration.Status 获取当前网络的状态。
        } else {
            return null;
        }
        return config;
    }

    /**
     * 打开wifi功能
     *
     * @return
     */
    public boolean isWifiTurnOn() {
        boolean isOpen = true;
        if (!wifiManager.isWifiEnabled()) {
            isOpen = wifiManager.setWifiEnabled(true);
        }
        return isOpen;
    }


    @Override
    protected void onPause() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_wifi:
                changeUI();
                break;
            case R.id.btn_search_wifi:
                getConfigurationWifi();
                break;
            case R.id.btn_scan_wifi:
                wifiManager.startScan();
                break;
        }
    }

    private void changeUI() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
            btnOpen.setText(getResources().getString(R.string.open_wifi));
            btnSearch.setEnabled(false);
            btnSearch.setAlpha(0.8f);
        } else {
            wifiManager.setWifiEnabled(true);
            btnOpen.setText(getResources().getString(R.string.close_wifi));
            btnSearch.setEnabled(true);
            btnSearch.setAlpha(1);
        }
    }

    private boolean checkPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_SCAN);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SCAN: {
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        || (permissions.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    getScanResult();
                }
            }
            break;
        }
    }
}
