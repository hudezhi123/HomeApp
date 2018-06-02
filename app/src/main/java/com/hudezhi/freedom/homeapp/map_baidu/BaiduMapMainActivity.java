package com.hudezhi.freedom.homeapp.map_baidu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.baidu.mapapi.map.MapView;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.map_baidu.activity.AroundMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.BaseBaiduActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.BasePOIActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.CalculatorMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.CharacterMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.CloudMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.ListenerMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.OfflineMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.PositionChangeMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.RideMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.RoomMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.SearchMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.SetDownMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.activity.WearMapActivity;
import com.hudezhi.freedom.homeapp.map_baidu.adapter.BaiduFuncAdapter;
import com.hudezhi.freedom.homeapp.map_baidu.bean.BaiduFuncInfo;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.ArrayList;
import java.util.List;

public class BaiduMapMainActivity extends AppCompatActivity {

    @BindView(id = R.id.gridview_baidu)
    private GridView gridView;
    private BaiduFuncAdapter adapter;
    private List<BaiduFuncInfo> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map_main);
        AnnotateUtil.initBindView(this);
        init();
    }

    private void init() {
        initView();
        initList();
        adapter = new BaiduFuncAdapter(this, infoList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiduFuncInfo info = infoList.get(position);
                openActivity(info.getTag());
            }
        });
    }

    private void initView() {

    }

    private void initList() {
        infoList = new ArrayList<>();
        infoList.add(new BaiduFuncInfo(0, "基础地图", "base"));
        infoList.add(new BaiduFuncInfo(0, "室内地图", "room"));
        infoList.add(new BaiduFuncInfo(0, "Android Wear", "wear"));
        infoList.add(new BaiduFuncInfo(0, "周边雷达", "around"));
        infoList.add(new BaiduFuncInfo(0, "离线地图", "offline"));
        infoList.add(new BaiduFuncInfo(0, "检索功能", "search"));
        infoList.add(new BaiduFuncInfo(0, "LBS云检索", "cloud"));
        infoList.add(new BaiduFuncInfo(0, "计算工具", "calculate"));
        infoList.add(new BaiduFuncInfo(0, "坐标转换", "position_change"));
        infoList.add(new BaiduFuncInfo(0, "定位", "set_down"));
        infoList.add(new BaiduFuncInfo(0, "事件监听", "listener"));
        infoList.add(new BaiduFuncInfo(0, "个性化地图", "character"));
        infoList.add(new BaiduFuncInfo(0, "骑行导航", "ride_guide"));
    }

    private void openActivity(String tag) {
        Intent intent = new Intent();
        switch (tag) {
            case "base":
                intent.setClass(this, BaseBaiduActivity.class);
                break;
            case "room":
                intent.setClass(this, RoomMapActivity.class);
                break;
            case "wear":
                intent.setClass(this, WearMapActivity.class);
                break;
            case "around":
                intent.setClass(this, AroundMapActivity.class);
                break;
            case "offline":
                intent.setClass(this, OfflineMapActivity.class);
                break;
            case "search":
                intent.setClass(this, BasePOIActivity.class);
                break;
            case "cloud":
                intent.setClass(this, CloudMapActivity.class);
                break;
            case "calculate":
                intent.setClass(this, CalculatorMapActivity.class);
                break;
            case "position_change":
                intent.setClass(this, PositionChangeMapActivity.class);
                break;
            case "set_down":
                intent.setClass(this, SetDownMapActivity.class);
                break;
            case "listener":
                intent.setClass(this, ListenerMapActivity.class);
                break;
            case "character":
                intent.setClass(this, CharacterMapActivity.class);
                break;
            case "ride_guide":
                intent.setClass(this, RideMapActivity.class);
                break;
        }
        startActivity(intent);
    }
}
