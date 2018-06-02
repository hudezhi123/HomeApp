package com.hudezhi.freedom.homeapp.audio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.adapter.AudioPagerAdapter;
import com.hudezhi.freedom.homeapp.audio.fragment.LocalMusicFragment;
import com.hudezhi.freedom.homeapp.audio.fragment.NetLibraryFragment;
import com.hudezhi.freedom.homeapp.audio.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class AudioMainActivity extends BaseAudioActivity {

    private AudioPagerAdapter adapter;
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private LocalMusicFragment localMusicFragment;
    private NetLibraryFragment netLibraryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        initView();
        fragmentList = new ArrayList<>();
        initFragmentList();
        adapter = new AudioPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabStrip.setTextSize(16);
        tabStrip.setViewPager(viewPager);
    }

    private void initView() {
        initTitleBar();
        viewPager = (ViewPager) findViewById(R.id.viewpager_audio_tab_content);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_audio_guide);
    }

    private void initFragmentList() {
//        playingFragment = new PlayingFragment();
        localMusicFragment = new LocalMusicFragment();
        netLibraryFragment = new NetLibraryFragment();
//        fragmentList.add(playingFragment);
        fragmentList.add(localMusicFragment);
        fragmentList.add(netLibraryFragment);
    }


}
