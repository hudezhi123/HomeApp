package com.hudezhi.freedom.homeapp.audio.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.AudioMainActivity;
import com.hudezhi.freedom.homeapp.audio.BaseAudioActivity;
import com.hudezhi.freedom.homeapp.audio.adapter.LocalMusicAdapter;
import com.hudezhi.freedom.homeapp.audio.bean.MusicInfo;
import com.hudezhi.freedom.homeapp.audio.service.PlayMusicService;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;
import com.hudezhi.freedom.homeapp.audio.utils.MusicDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalMusicFragment extends Fragment {
    public static final String TAG = "我的歌曲";
    private ListView listViewLocalMusic;
    private LocalMusicAdapter adapter;
    private List<MusicInfo> musicInfoList;
    private static final int GET_DATA_SUCC = 1;
    private AudioMainActivity audioMainActivity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DATA_SUCC:
                    musicInfoList = (List<MusicInfo>) msg.obj;
                    adapter.setList(musicInfoList);
                    break;
            }
        }
    };

    public LocalMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        audioMainActivity = (AudioMainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        init(view);
        getData();
//        audioMainActivity.bindPlayerService();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void initView(View view) {
        listViewLocalMusic = (ListView) view.findViewById(R.id.listView_local_music);
    }

    private void init(View view) {
        initView(view);
        musicInfoList = new ArrayList<>();
        adapter = new LocalMusicAdapter(getContext());
        listViewLocalMusic.setAdapter(adapter);
        listViewLocalMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                audioMainActivity.playMusicService.playCurrentPosition(position);
            }
        });
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MusicInfo> tempList = PlayMusicService.musicList;
                if (tempList == null) {
                    return;
                }
                Message message = handler.obtainMessage();
                message.what = GET_DATA_SUCC;
                message.obj = tempList;
                handler.sendMessageAtFrontOfQueue(message);
            }
        }).start();
    }


}
