package com.hudezhi.freedom.homeapp.person.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.person.adapter.PrivateReceiptListAdapter;
import com.hudezhi.freedom.homeapp.person.bean.Flags;
import com.hudezhi.freedom.homeapp.person.bean.FormContentInfo;
import com.hudezhi.freedom.homeapp.person.presenter.FormContentInfoPresenter;
import com.hudezhi.freedom.homeapp.person.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ReceiptListActivity extends Activity implements IReceiptListActivity, DBHelper.OnDBChangedListener {
    private PrivateReceiptListAdapter adapter;
    private List<FormContentInfo> infoList;
    private ListView listView;
    private FormContentInfoPresenter presenter;
    private static boolean isOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnCreate = true;
        setContentView(R.layout.activity_receipt_list);
        presenter = new FormContentInfoPresenter(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isOnCreate) {

        } else {
            presenter.getDataList();
        }
    }

    private void initView() {
        infoList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview_receipt_list);
        presenter.getDataList();
    }

    @Override
    protected void onPause() {
        isOnCreate = false;
        super.onPause();
    }

    @Override
    public void showToView(List<FormContentInfo> infos) {
        this.infoList = infos;
        adapter = new PrivateReceiptListAdapter(this, infoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReceiptListActivity.this, OBFActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Flags.TO_OBF_FLAG, Flags.FLAG_FROM_LIST_RECEIPT);
                bundle.putInt(Flags.FLAG_INFO_ID, (int) id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDBChanged() {

    }
}
