package com.hudezhi.freedom.homeapp.audio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.bean.ImageInfo;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetLibraryFragment extends Fragment {

    public static final String TAG = "乐库";

    public NetLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_net_library, container, false);
    }

}
