package com.hudezhi.freedom.homeapp.nfc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.nfc.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private PackageManager packageManage;
    private ListView mListView;
    private List<AppInfo> appInfoList;
    private MyAdapter mAdapter;
    private static final int RESULT_CODE_APP_LIST = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        prepareAdapter();
    }

    /**
     * 准备listview的显示
     */
    private void prepareAdapter() {
        mListView = (ListView) findViewById(R.id.listview_app_list);
        appInfoList = new ArrayList<>();
        mAdapter = new MyAdapter(this);
        getAppList();
    }

    /**
     * 获取已经安装的app的相关信息
     */
    private void getAppList() {
        packageManage = getPackageManager();
        List<PackageInfo> packageInfoList = packageManage.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        for (PackageInfo packageInfo : packageInfoList) {
            AppInfo appInfo = new AppInfo();
            appInfo.packageName = packageInfo.packageName;
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManage);
            appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManage).toString();
            appInfo.versionCode = packageInfo.versionCode;
            appInfo.versionName = packageInfo.versionName;
            appInfoList.add(appInfo);
        }
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo info = mAdapter.getItem(position);
                Intent intent = new Intent(AppListActivity.this, NfcMainActivity.class);
                intent.putExtra("package_name", info.packageName);
                setResult(RESULT_CODE_APP_LIST, intent);
                AppListActivity.this.finish();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return appInfoList == null ? 0 : appInfoList.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return appInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.app_info_item_layout, parent, false);
                holder.appIcon = (ImageView) convertView.findViewById(R.id.img_app_icon);
                holder.appName = (TextView) convertView.findViewById(R.id.text_app_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AppInfo appInfo = getItem(position);
            holder.appIcon.setImageDrawable(appInfo.appIcon);
            holder.appName.setText(appInfo.appName);
            return convertView;
        }

        class ViewHolder {
            ImageView appIcon;
            TextView appName;
        }
    }


}
