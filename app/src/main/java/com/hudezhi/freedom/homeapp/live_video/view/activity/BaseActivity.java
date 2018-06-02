package com.hudezhi.freedom.homeapp.live_video.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hudezhi.freedom.homeapp.MainApplication;
import com.hudezhi.freedom.homeapp.live_video.bean.Constants;
import com.hudezhi.freedom.homeapp.live_video.bean.Flags;
import com.hudezhi.freedom.homeapp.live_video.bean.NetworkBean;
import com.hudezhi.freedom.homeapp.live_video.presenter.BasePresenter;
import com.hudezhi.freedom.homeapp.live_video.utils.ActivityContainer;
import com.hudezhi.freedom.homeapp.live_video.utils.NetConnectUtil;
import com.hudezhi.freedom.homeapp.live_video.view.receiver.NetStateReceiver;
import com.hudezhi.freedom.homeapp.live_video.view.view_interface.BaseMethod;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;

/**
 * Created by boy on 2017/8/11.
 */


public abstract class BaseActivity extends FragmentActivity implements BaseMethod, NetStateReceiver.OnNetStateChangeListener {

    public static NetStateReceiver.OnNetStateChangeListener onNetListener;
    public Context mContext;
    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    public BasePresenter mPresenter;
    public FragmentManager mFragmentManager;
    public NetStateReceiver mReceiver;


    public boolean hasNet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        onNetListener = this;
        mPresenter = getBasePresenter();
        mContext = getApplicationContext();
        mActivity = this;
        mFragmentManager = getSupportFragmentManager();
        hasNet = isHasNet();
        AnnotateUtil.initBindView(this);
        ActivityContainer.getInstance().addActivity(this);
        initIntentData();
        initView();
        initData();
    }


    /**
     * @param message toast 弹出的消息内容
     */
    public void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param resId 资源文件中定义的string的id
     */
    public void showToast(int resId) {
        String message = mContext.getResources().getString(resId);
        showToast(message);
    }

    /**
     * @param resId 资源文件 string id
     */
    public void showProgressDialog(int resId) {
        String message = mContext.getResources().getString(resId);
        showProgressDialog(message);
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message, false);
    }

    public void showProgressDialog(String message, boolean flag) {
        if (mProgressDialog == null) {
            if (getParent() != null) {
                mProgressDialog = new ProgressDialog(getParent());
            } else {
                mProgressDialog = new ProgressDialog(this);
            }
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(false);  //设置ProgressDialog 的进度条是否不明确；这个属性对于ProgressDailog默认的转轮模式没有实际意义，默认下设置为true，它仅仅对带有ProgressBar的Dialog有作用。修改这个属性为false后可以实时更新进度条的进度。
            mProgressDialog.setCancelable(flag);   //设置ProgressDialog 是否可以按返回键取消
            mProgressDialog.show();
        } else {
            mProgressDialog.setMessage(message);
        }
    }

    public void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void LogMessage(String tag, String value) {
        if (MainApplication.isLog) {
            Log.i(tag, value);
        }
    }

    @Override
    public void onNetStateChanged(NetworkBean bean) {
        if (hasNet) {
            if (bean.getType() >= 0) {
                //do nothing
            } else {
                changeNetBarVisibility(true);
            }
        } else {
            if (bean.getType() < 0) {
                //do nothing
            } else {
                changeNetBarVisibility(false);
            }
        }
    }

    private void changeNetBarVisibility(boolean b) {
        Intent intent = new Intent();
        intent.setAction(Constants.IntentType.NET_BAR_VISIBILITY_STATE_RECEIVER);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Flags.NET_BAR_IS_VISIBLE, b);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    public boolean isHasNet() {
        NetworkBean networkBean = NetConnectUtil.getNetworkInfo(this);
        if (networkBean != null) {
            if (networkBean.getType() >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetReceiver();
        if (mPresenter != null) {
            mPresenter.onViewResume();
        }
    }

    private void registerNetReceiver() {
        mReceiver = new NetStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.IntentType.SYSTEM_NET_RECEIVER);
        registerReceiver(mReceiver, filter);
    }


    @Override
    protected void onPause() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        if (mPresenter != null) {
            mPresenter.onViewPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            mPresenter.onViewStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onViewDestroy();
        }
        super.onDestroy();
    }
}
