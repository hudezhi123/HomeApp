package com.hudezhi.freedom.homeapp.live_video.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by boy on 2017/8/11.
 */

public class ActivityContainer {

    private static ActivityContainer container;

    private ArrayList<Activity> actList = new ArrayList<>();

    private ActivityContainer() {
    }

    public static ActivityContainer getInstance() {
        if (container == null) {
            container = new ActivityContainer();
        }
        return container;
    }

    public void addActivity(Activity activity) {
        actList.add(activity);
    }

    public void removeActivity(Activity activity) {
        actList.remove(activity);
    }


    public int getSize() {
        return actList == null ? 0 : actList.size();
    }

    public void quitApp() {
        if (actList != null && actList.size() > 0) {
            for (Activity activity : actList) {
                activity.finish();
            }
            System.exit(0);
        }
    }
}

