package com.hudezhi.freedom.homeapp.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.MainActivity;
import com.hudezhi.freedom.homeapp.R;

public class BaseActivity extends AppCompatActivity {
    public View view;
    public TextView logo, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 初始化titlebar
     */
    public void initTitleBar() {
        view = findViewById(R.id.titlebar);
        logo = (TextView) findViewById(R.id.img_logo_titlebar);
        close = (TextView) findViewById(R.id.close_titlebar);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(intent);
                BaseActivity.this.finish();
            }
        });
    }


}
