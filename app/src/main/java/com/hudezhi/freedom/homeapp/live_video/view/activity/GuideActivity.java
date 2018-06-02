package com.hudezhi.freedom.homeapp.live_video.view.activity;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.live_video.bean.Constants;
import com.hudezhi.freedom.homeapp.live_video.bean.Flags;
import com.hudezhi.freedom.homeapp.live_video.presenter.BasePresenter;
import com.hudezhi.freedom.homeapp.live_video.view.fragment.BaseFragment;
import com.hudezhi.freedom.homeapp.live_video.view.fragment.FindFragment;
import com.hudezhi.freedom.homeapp.live_video.view.fragment.LiveFragment;
import com.hudezhi.freedom.homeapp.live_video.view.fragment.MineFragment;
import com.hudezhi.freedom.homeapp.live_video.view.fragment.RankListFragment;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity implements View.OnClickListener {


    private NetBarVisibilityChangeReceiver receiver;

    @BindView(id = R.id.toolbar_search_bar_guide)
    private LinearLayout toolBar;
    @BindView(id = R.id.linear_net_bar)
    private LinearLayout netBar;
    private List<BaseFragment> fragmentList;
    @BindView(id = R.id.radiogroup_tab_guide_main)
    private RadioGroup guideGroup;
    @BindView(id = R.id.img_logo_qianfan)
    private ImageView imgLogoLive;
    @BindView(id = R.id.linear_task_find)
    private LinearLayout linearTaskFind;
    @BindView(id = R.id.button_task_find)
    private Button btnTaskFind;
    @BindView(id = R.id.btn_search_live)
    private Button btSearch;
    @BindView(id = R.id.img_my_focus_live)
    private ImageView imgFocusLive;
    @BindView(id = R.id.button_shop_find)
    private Button btnMallFind;
    private FragmentTransaction mTransaction;
    private int oldOne = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasNet) {
            netBar.setVisibility(View.GONE);
        } else {
            netBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    private void initTitleBar() {
        toolBar.setVisibility(View.VISIBLE);
        btnTaskFind.setVisibility(View.GONE);
        btnMallFind.setVisibility(View.GONE);
        imgLogoLive.setVisibility(View.VISIBLE);
        imgFocusLive.setVisibility(View.VISIBLE);
        btnTaskFind.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        imgFocusLive.setOnClickListener(this);
        btnMallFind.setOnClickListener(this);
    }

    public void setItemVisible(int flag) {
        switch (flag) {
            case Flags.LIVE:
                toolBar.setVisibility(View.VISIBLE);
                btnTaskFind.setVisibility(View.GONE);
                btnMallFind.setVisibility(View.GONE);
                imgLogoLive.setVisibility(View.VISIBLE);
                imgFocusLive.setVisibility(View.VISIBLE);
                break;
            case Flags.FIND:
                toolBar.setVisibility(View.VISIBLE);
                btnTaskFind.setVisibility(View.VISIBLE);
                btnMallFind.setVisibility(View.VISIBLE);
                imgLogoLive.setVisibility(View.GONE);
                imgFocusLive.setVisibility(View.GONE);
                break;
            case Flags.MINE:
                toolBar.setVisibility(View.GONE);
                break;
            case Flags.RANK:
                toolBar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();
        LiveFragment liveFragment = new LiveFragment();
        initTitleBar();
        RankListFragment rankListFragment = new RankListFragment();
        FindFragment findFragment = new FindFragment();
        MineFragment mineFragment = new MineFragment();
        fragmentList.add(liveFragment);
        fragmentList.add(rankListFragment);
        fragmentList.add(findFragment);
        fragmentList.add(mineFragment);
        mTransaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            mTransaction.add(R.id.fragmentcontainer_main, fragmentList.get(i));
            mTransaction.hide(fragmentList.get(i));
        }
        mTransaction.show(liveFragment);
        mTransaction.commit();
        guideGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.hide(fragmentList.get(oldOne));
                switch (checkedId) {
                    case R.id.radio_live_main:
                        oldOne = 0;
                        break;
                    case R.id.radio_ranklist_main:
                        oldOne = 1;
                        break;
                    case R.id.radio_find_main:
                        oldOne = 2;
                        break;
                    case R.id.radio_mine_main:
                        oldOne = 3;
                        break;
                }
                fragmentList.get(oldOne).updateView();
                transaction.show(fragmentList.get(oldOne));
                transaction.commit();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public BasePresenter getBasePresenter() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new NetBarVisibilityChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.IntentType.NET_BAR_VISIBILITY_STATE_RECEIVER);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_task_find:

                break;
            case R.id.btn_search_live:
                Intent intent = new Intent(GuideActivity.this, SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, v, "tran").toBundle());
                }
                break;
            case R.id.img_my_focus_live:

                break;
            case R.id.button_shop_find:

                break;
        }
    }


    public class NetBarVisibilityChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (intent.getAction().equals(Constants.IntentType.NET_BAR_VISIBILITY_STATE_RECEIVER)) {
                if (bundle != null) {
                    boolean isVisible = bundle.getBoolean(Flags.NET_BAR_IS_VISIBLE);
                    //TODO: 2017/8/11   设置netBar
                    if (isVisible) {
                        netBar.setVisibility(View.VISIBLE);
                    } else {
                        netBar.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
}
