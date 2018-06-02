package com.hudezhi.freedom.homeapp.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.person.bean.Flags;
import com.hudezhi.freedom.homeapp.person.view.OBFActivity;
import com.hudezhi.freedom.homeapp.person.view.ReceiptListActivity;

public class MainPrivateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_private);
    }

    public void showReceiptList(View view) {
        Intent intent = new Intent(MainPrivateActivity.this, ReceiptListActivity.class);
        startActivity(intent);
    }

    public void addReceipt(View view) {
        Intent intent = new Intent(MainPrivateActivity.this, OBFActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Flags.TO_OBF_FLAG, Flags.FLAG_FROM_ADD_RECEIPT);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
