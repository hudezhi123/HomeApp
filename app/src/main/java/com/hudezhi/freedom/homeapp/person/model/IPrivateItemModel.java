package com.hudezhi.freedom.homeapp.person.model;

import com.hudezhi.freedom.homeapp.person.bean.PrivateModuleItem;

import java.util.List;

/**
 * Created by boy on 2017/8/7.
 */

public interface IPrivateItemModel {
    public List<PrivateModuleItem> loadPrivateItemList();

    public PrivateModuleItem loadPrivateItem(int id);

    public void savePrivateItem(String tag, String name, int img_id);
}
