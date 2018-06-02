package com.hudezhi.freedom.homeapp.person.model;

import com.hudezhi.freedom.homeapp.person.bean.FormContentInfo;

import java.util.List;

/**
 * Created by boy on 2017/8/8.
 */

public interface IFormContentInfoModel {

    public FormContentInfo loadData(int id);

    public boolean saveFormData(FormContentInfo info);

    public List<FormContentInfo> loadDataList();
}
