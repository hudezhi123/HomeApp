package com.hudezhi.freedom.homeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hudezhi.freedom.homeapp.audio.AudioActivity;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;
import com.hudezhi.freedom.homeapp.bluetooth.BlueToothActivity;
import com.hudezhi.freedom.homeapp.book.MainReadActivity;
import com.hudezhi.freedom.homeapp.im.MainCommunicationActivity;
import com.hudezhi.freedom.homeapp.live_video.view.activity.LoginView;
import com.hudezhi.freedom.homeapp.map_baidu.BaiduMapMainActivity;
import com.hudezhi.freedom.homeapp.map_gaode.GaodeMapMainActivity;
import com.hudezhi.freedom.homeapp.more.MainMoreActivity;
import com.hudezhi.freedom.homeapp.network.NetworkMainActivity;
import com.hudezhi.freedom.homeapp.news.MainNewsActivity;
import com.hudezhi.freedom.homeapp.nfc.NFCPopActivity;
import com.hudezhi.freedom.homeapp.nfc.NfcMainActivity;
import com.hudezhi.freedom.homeapp.pay.MainPayActivity;
import com.hudezhi.freedom.homeapp.person.view.LoginActivity;
import com.hudezhi.freedom.homeapp.study.MainStudyActivity;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;
import com.hudezhi.freedom.homeapp.video.MainVideoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements MainItemAdapter.OnModuleItemClickListener {
    @BindView(id = R.id.recycler_activity_main)
    private RecyclerView recycler;
    private GridLayoutManager manager;
    private List<ModuleItem> moduleList;
    private MainItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaUtils.mContext = this.getApplicationContext();
        setContentView(R.layout.activity_main);
        AnnotateUtil.initBindView(this);
        init();
    }

    private void init() {
        initView();
        initList();
        adapter = new MainItemAdapter(this, moduleList);
        recycler.setAdapter(adapter);
        adapter.setModuleItemClickListener(this);
    }

    private void initView() {
        manager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
    }

    private void initList() {
        moduleList = new ArrayList<>();
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_AUDIO, getResources().getString(R.string.audio), R.mipmap.ic_audio));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_VIDEO, getResources().getString(R.string.video), R.mipmap.ic_video));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_NEWS, getResources().getString(R.string.news), R.mipmap.ic_news));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_PAY, getResources().getString(R.string.pay), R.mipmap.ic_pay));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_LIVE, getResources().getString(R.string.live_video), R.mipmap.live_video_icon));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_COMMUNICATION, getResources().getString(R.string.communication), R.mipmap.ic_communication));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_READ, getResources().getString(R.string.read), R.mipmap.ic_read));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_OFFICE, getResources().getString(R.string.office), R.mipmap.ic_office));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_PRIVATE, getResources().getString(R.string.privace), R.mipmap.ic_privacy));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_BAIDU_MAP, getResources().getString(R.string.baidu_map), R.mipmap.ic_baidu_map));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_GAODE_MAP, getResources().getString(R.string.gaode_map), R.mipmap.ic_gaode_map));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_BLUETOOTH, getResources().getString(R.string.bluetooth), R.mipmap.bluetooth_icon));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_WIFI, getResources().getString(R.string.wifi), R.mipmap.ic_wifi_icon));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_NFC, getResources().getString(R.string.nfc), R.mipmap.ic_nfc_icon));
        moduleList.add(new ModuleItem(Constants.ModuleName.MODULE_MORE, getResources().getString(R.string.more), R.mipmap.ic_more));


    }

    public void startModuleItem(String ModuleName) {
        Intent intent = new Intent();
        switch (ModuleName) {
            case Constants.ModuleName.MODULE_AUDIO:
                intent.setClass(this, AudioActivity.class);
                break;
            case Constants.ModuleName.MODULE_VIDEO:
                intent.setClass(this, MainVideoActivity.class);
                break;
            case Constants.ModuleName.MODULE_NEWS:
                intent.setClass(this, MainNewsActivity.class);
                break;
            case Constants.ModuleName.MODULE_PAY:
                intent.setClass(this, MainPayActivity.class);
                break;
            case Constants.ModuleName.MODULE_COMMUNICATION:
                intent.setClass(this, MainCommunicationActivity.class);
                break;
            case Constants.ModuleName.MODULE_READ:
                intent.setClass(this, MainReadActivity.class);
                break;
            case Constants.ModuleName.MODULE_OFFICE:
                intent.setClass(this, MainStudyActivity.class);
                break;
            case Constants.ModuleName.MODULE_PRIVATE:
                intent.setClass(this, LoginActivity.class);
                break;
            case Constants.ModuleName.MODULE_BAIDU_MAP:
                intent.setClass(this, BaiduMapMainActivity.class);
                break;
            case Constants.ModuleName.MODULE_GAODE_MAP:
                intent.setClass(this, GaodeMapMainActivity.class);
                break;
            case Constants.ModuleName.MODULE_LIVE:
                intent.setClass(this, LoginView.class);
                break;
            case Constants.ModuleName.MODULE_BLUETOOTH:
                intent.setClass(this, BlueToothActivity.class);
                break;
            case Constants.ModuleName.MODULE_WIFI:
                intent.setClass(this, NetworkMainActivity.class);
                break;
            case Constants.ModuleName.MODULE_NFC:
                intent.setClass(this, NfcMainActivity.class);
                break;
            case Constants.ModuleName.MODULE_MORE:
                intent.setClass(this, MainMoreActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        ModuleItem moduleItem = moduleList.get(position);
        startModuleItem(moduleItem.getTag());
    }
}
