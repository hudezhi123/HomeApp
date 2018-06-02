package com.hudezhi.freedom.homeapp.person.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;

public class PrivateModuleInfoActivity extends Activity implements IModuleInfoActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_module_info);
        AnnotateUtil.initBindView(this);
    }


}
