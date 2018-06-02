package com.hudezhi.freedom.homeapp.video;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;
import com.hudezhi.freedom.homeapp.video.bean.FileUrl;

import java.util.List;

public class MainVideoActivity extends Activity {

    @BindView(id = R.id.edit_server_configure)
    private EditText editServer;
    @BindView(id = R.id.btn_server_configure)
    private Button btnSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_video);
        AnnotateUtil.initBindView(this);
    }

    private void init() {

    }

    public void onConfigure(View view) {
        String server = editServer.getText().toString();
        FileUrl.SERVER_ADDRESS = server;
        Intent intent = new Intent(MainVideoActivity.this, VideoActivity.class);
        startActivity(intent);
        this.finish();
    }
}
