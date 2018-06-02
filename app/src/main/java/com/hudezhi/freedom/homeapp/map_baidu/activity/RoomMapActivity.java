package com.hudezhi.freedom.homeapp.map_baidu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

public class RoomMapActivity extends AppCompatActivity {

    @BindView(id = R.id.bdmap_room)
    private MapView bdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_map);
        AnnotateUtil.initBindView(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bdMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bdMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdMap.onDestroy();
    }
}
