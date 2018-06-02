package com.hudezhi.freedom.homeapp.person.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hudezhi.freedom.homeapp.person.bean.FormContentInfo;
import com.hudezhi.freedom.homeapp.person.model.FormContentInfoModel;
import com.hudezhi.freedom.homeapp.person.model.IFormContentInfoModel;
import com.hudezhi.freedom.homeapp.person.view.IOBFActivity;
import com.hudezhi.freedom.homeapp.person.view.IReceiptListActivity;
import com.hudezhi.freedom.homeapp.person.view.OBFActivity;
import com.hudezhi.freedom.homeapp.person.view.ReceiptListActivity;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by boy on 2017/8/8.
 */

public class FormContentInfoPresenter implements OBFActivity.OnEditTextEdibleListener {

    private IOBFActivity IBOFView;
    private IReceiptListActivity ReceiptView;
    private IFormContentInfoModel IModel;
    private boolean isEditable = false;
    private Context mContext;

    public boolean isEditable() {
        return isEditable;
    }

    public FormContentInfoPresenter(IOBFActivity view) {
        IBOFView = view;
        IModel = new FormContentInfoModel();
        mContext = ((OBFActivity) IBOFView).getApplicationContext();
        FormContentInfoModel.mContext = mContext;
        ((OBFActivity) IBOFView).setOnEditTextEdibleListener(this);
    }

    public FormContentInfoPresenter(IReceiptListActivity receiptView) {
        ReceiptView = receiptView;
        IModel = new FormContentInfoModel();
        mContext = ((ReceiptListActivity) ReceiptView).getApplicationContext();
        FormContentInfoModel.mContext = mContext;
    }

    public void getDataList() {
        List<FormContentInfo> infoList = IModel.loadDataList();
        ReceiptView.showToView(infoList);
    }

    /**
     * 点击提交按钮后，实现的逻辑
     */
    public void saveData() {
        FormContentInfo info = IBOFView.getFromView();
        IModel.saveFormData(info);
    }

    public void getData() {
        int id = IBOFView.getId();
        FormContentInfo info = IModel.loadData(id);
        IBOFView.setToView(info);
    }


    /**
     * 改变表单中的EditText的可编辑状态
     *
     * @param isEditable
     */
    @Override
    public void edibleStateChanged(boolean isEditable) {
        this.isEditable = isEditable;
        if (IBOFView instanceof OBFActivity) {
            View rootView = ((OBFActivity) IBOFView).getWindow().getDecorView();
            Field[] fields = ((OBFActivity) IBOFView).getClass().getDeclaredFields();
            int count = fields.length;
            for (int i = 0; i < count; i++) {
                Field field = fields[i];
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView != null) {
                    int viewId = bindView.id();
                    field.setAccessible(true);
                    View view = rootView.findViewById(viewId);
                    if (view instanceof EditText) {
                        if (isEditable) {
                            view.setFocusableInTouchMode(isEditable);
                            view.requestFocus();
                        } else {
                            view.setFocusableInTouchMode(false);
                            view.clearFocus();
                        }
                    } else if (view instanceof Button) {
                        if (isEditable) {
                            view.setEnabled(isEditable);
                            view.setAlpha(0.88f);
                        } else {
                            view.setEnabled(isEditable);
                            view.setAlpha(1);
                        }
                    }
                    try {
                        field.set((OBFActivity) IBOFView, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
