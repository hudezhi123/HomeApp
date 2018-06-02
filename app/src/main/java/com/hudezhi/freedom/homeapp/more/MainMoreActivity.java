package com.hudezhi.freedom.homeapp.more;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.more.bean.NetBean;

import java.util.ArrayList;
import java.util.List;

public class MainMoreActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_more);
        initList();
    }

    private void initList() {
        NetBean netBean = new NetBean();
        netBean.setCode(1);
        netBean.setFileName("小明");
        netBean.setFilePath("ddddd");
        List<NetBean.ChildBean> childBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NetBean.ChildBean childBean = new NetBean.ChildBean();
            childBean.setName("dd" + i);
            childBean.setAge(5);
            childBeanList.add(childBean);
        }
        netBean.setChildBeanList(childBeanList);
        String json = new Gson().toJson(netBean);
        Log.i("dd", "" + json);
    }


}
