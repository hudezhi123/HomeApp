package com.hudezhi.freedom.homeapp.person.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by boy on 2017/7/31.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showToast(int stringId) {
        String message = getResources().getString(stringId);
        showToast(message);
    }

    public void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
        } else {
            return;
        }
    }

    public void showDialog(String message) {

    }
}
