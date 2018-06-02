package com.hudezhi.freedom.homeapp.audio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudezhi.freedom.homeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayingImgFragment extends Fragment {


    public PlayingImgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playing_img, container, false);
        return view;
    }

}
