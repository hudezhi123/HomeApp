package com.hudezhi.freedom.homeapp.person.model;

import android.content.Context;
import android.database.Cursor;

import com.hudezhi.freedom.homeapp.person.bean.FormContentInfo;
import com.hudezhi.freedom.homeapp.person.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/8/8.
 */

public class FormContentInfoModel implements IFormContentInfoModel {
    public static Context mContext;
    private DBHelper dbHelper = null;

    @Override
    public FormContentInfo loadData(int id) {
        dbHelper = new DBHelper(mContext);
        String sql = "select * from "
                + DBHelper.TB_BUSINESS_RECEIPT + " where "
                + DBHelper._ID + "='" + id + "';";
        Cursor cursor = dbHelper.querySql(sql);
        FormContentInfo info = null;
        if (cursor != null) {
            cursor.moveToFirst();
            info = new FormContentInfo();
            info.set_id(cursor.getInt(cursor.getColumnIndex(DBHelper._ID)));
            info.setGoStartDate(cursor.getString(cursor.getColumnIndex(DBHelper.GO_START_DATE)));
            info.setGoStartTime(cursor.getString(cursor.getColumnIndex(DBHelper.GO_START_TIME)));
            info.setGoStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.GO_START_PLACE)));
            info.setGoArriveDate(cursor.getString(cursor.getColumnIndex(DBHelper.GO_ARRIVE_DATE)));
            info.setGoArriveTime(cursor.getString(cursor.getColumnIndex(DBHelper.GO_ARRIVE_TIME)));
            info.setGoStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.GO_ARRIVE_PLACE)));
            info.setGoTools(cursor.getString(cursor.getColumnIndex(DBHelper.GO_TOOLS)));
            info.setGoToolsFees(cursor.getDouble(cursor.getColumnIndex(DBHelper.GO_FEES)));

            info.setBackStartDate(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_START_DATE)));
            info.setBackStartTime(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_START_TIME)));
            info.setBackStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_START_PLACE)));
            info.setBackArriveDate(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_ARRIVE_DATE)));
            info.setBackArriveTime(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_ARRIVE_TIME)));
            info.setBackStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_ARRIVE_PLACE)));
            info.setBackTools(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_TOOLS)));
            info.setBackToolsFees(cursor.getInt(cursor.getColumnIndex(DBHelper.BACK_FEES)));

            info.setBonus(cursor.getInt(cursor.getColumnIndex(DBHelper.BONUS)));
            info.setOtherFees(cursor.getInt(cursor.getColumnIndex(DBHelper.OTHER_FEES)));
            info.setRoomFees(cursor.getInt(cursor.getColumnIndex(DBHelper.ROOM_FEES)));
            info.setMarks(cursor.getString(cursor.getColumnIndex(DBHelper.REMARKS)));
            String pathList = cursor.getString(cursor.getColumnIndex(DBHelper.IMG_PATH_LIST));
            String[] paths = pathList.split(";");
            List<String> img_list = new ArrayList<>();
            for (int i = 0; i < paths.length - 1; i++) {
                img_list.add(paths[i]);
            }
            info.setReceiptsPath(img_list);
            cursor.close();
            dbHelper.close();
            return info;
        }
        dbHelper.close();
        return null;
    }


    @Override
    public boolean saveFormData(FormContentInfo info) {
        dbHelper = new DBHelper(mContext);
        List<String> lists = info.getReceiptsPath();
        String pathList = "";
        StringBuilder stringBuilder = new StringBuilder("");
        if (lists != null && lists.size() > 0) {
            for (int i = 0; i < lists.size(); i++) {
                stringBuilder.append(lists.get(i) + ";");
                pathList = stringBuilder.toString();
            }
        }
        String insert_sql = "insert into "
                + DBHelper.TB_BUSINESS_RECEIPT + "("
                + DBHelper.GO_START_DATE + ", "
                + DBHelper.GO_START_TIME + ", "
                + DBHelper.GO_ARRIVE_DATE + ", "
                + DBHelper.GO_ARRIVE_TIME + ", "
                + DBHelper.GO_START_PLACE + ", "
                + DBHelper.GO_ARRIVE_PLACE + ", "
                + DBHelper.GO_TOOLS + " , "
                + DBHelper.GO_FEES + " , "
                + DBHelper.BACK_START_DATE + ", "
                + DBHelper.BACK_START_TIME + ", "
                + DBHelper.BACK_ARRIVE_DATE + ", "
                + DBHelper.BACK_ARRIVE_TIME + ", "
                + DBHelper.BACK_START_PLACE + ", "
                + DBHelper.BACK_ARRIVE_PLACE + ", "
                + DBHelper.BACK_TOOLS + ", "
                + DBHelper.BACK_FEES + ", "
                + DBHelper.BONUS + ", "
                + DBHelper.ROOM_FEES + ", "
                + DBHelper.OTHER_FEES + ", "
                + DBHelper.REMARKS + ", "
                + DBHelper.IMG_PATH_LIST
                + ")values("
                + "'" + info.getGoStartDate() + "',"
                + "'" + info.getGoStartTime() + "',"
                + "'" + info.getGoArriveDate() + "',"
                + "'" + info.getGoArriveTime() + "',"
                + "'" + info.getGoStartPlace() + "',"
                + "'" + info.getGoStartPlace() + "',"
                + "'" + info.getGoTools() + "',"
                + "'" + info.getGoToolsFees() + "',"
                + "'" + info.getBackStartDate() + "',"
                + "'" + info.getBackStartTime() + "',"
                + "'" + info.getBackArriveDate() + "',"
                + "'" + info.getBackArriveTime() + "',"
                + "'" + info.getBackStartPlace() + "',"
                + "'" + info.getBackStartPlace() + "',"
                + "'" + info.getBackTools() + "',"
                + "'" + info.getBackToolsFees() + "',"
                + "'" + info.getBonus() + "',"
                + "'" + info.getRoomFees() + "',"
                + "'" + info.getOtherFees() + "',"
                + "'" + info.getMarks() + "',"
                + "'" + pathList
                + "');";
        dbHelper.exeSql(insert_sql);
        dbHelper.close();
        return false;
    }

    @Override
    public List<FormContentInfo> loadDataList() {
        dbHelper = new DBHelper(mContext);
        String sql = "select * from "
                + DBHelper.TB_BUSINESS_RECEIPT + ";";
        Cursor cursor = dbHelper.querySql(sql);
        FormContentInfo info = null;
        List<FormContentInfo> infoList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                info = new FormContentInfo();
                info.set_id(cursor.getInt(cursor.getColumnIndex(DBHelper._ID)));
                info.setGoStartDate(cursor.getString(cursor.getColumnIndex(DBHelper.GO_START_DATE)));
                info.setGoStartTime(cursor.getString(cursor.getColumnIndex(DBHelper.GO_START_TIME)));
                info.setGoStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.GO_START_PLACE)));
                info.setGoArriveDate(cursor.getString(cursor.getColumnIndex(DBHelper.GO_ARRIVE_DATE)));
                info.setGoArriveTime(cursor.getString(cursor.getColumnIndex(DBHelper.GO_ARRIVE_TIME)));
                info.setGoStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.GO_ARRIVE_PLACE)));
                info.setGoTools(cursor.getString(cursor.getColumnIndex(DBHelper.GO_TOOLS)));
                info.setGoToolsFees(cursor.getDouble(cursor.getColumnIndex(DBHelper.GO_FEES)));

                info.setBackStartDate(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_START_DATE)));
                info.setBackStartTime(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_START_TIME)));
                info.setBackStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_START_PLACE)));
                info.setBackArriveDate(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_ARRIVE_DATE)));
                info.setBackArriveTime(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_ARRIVE_TIME)));
                info.setBackStartPlace(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_ARRIVE_PLACE)));
                info.setBackTools(cursor.getString(cursor.getColumnIndex(DBHelper.BACK_TOOLS)));
                info.setBackToolsFees(cursor.getInt(cursor.getColumnIndex(DBHelper.BACK_FEES)));

                info.setBonus(cursor.getInt(cursor.getColumnIndex(DBHelper.BONUS)));
                info.setOtherFees(cursor.getInt(cursor.getColumnIndex(DBHelper.OTHER_FEES)));
                info.setRoomFees(cursor.getInt(cursor.getColumnIndex(DBHelper.ROOM_FEES)));
                info.setMarks(cursor.getString(cursor.getColumnIndex(DBHelper.REMARKS)));
                String pathList = cursor.getString(cursor.getColumnIndex(DBHelper.IMG_PATH_LIST));
                String[] paths = pathList.split(";");
                List<String> img_list = new ArrayList<>();
                for (int i = 0; i < paths.length - 1; i++) {
                    img_list.add(paths[i]);
                }
                info.setReceiptsPath(img_list);
                infoList.add(info);
            }
            cursor.close();
            dbHelper.close();
            return infoList;
        }
        dbHelper.close();
        return null;
    }


}
