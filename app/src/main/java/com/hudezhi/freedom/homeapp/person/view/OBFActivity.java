package com.hudezhi.freedom.homeapp.person.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.person.bean.Flags;
import com.hudezhi.freedom.homeapp.person.bean.FormContentInfo;
import com.hudezhi.freedom.homeapp.person.presenter.FormContentInfoPresenter;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OBFActivity extends Activity implements IOBFActivity, View.OnClickListener {

    private FormContentInfoPresenter mPresenter;

    @BindView(id = R.id.right_title_bar)
    private TextView edit;
    @BindView(id = R.id.btn_go_start_date_private_obf)
    private Button GoStartDate;
    @BindView(id = R.id.btn_go_start_time_private_obf)
    private Button GoStartTime;
    @BindView(id = R.id.edit_go_start_place)
    private EditText GoStartPlace;
    @BindView(id = R.id.edit_go_arrive_place)
    private EditText GoArrivePlace;
    @BindView(id = R.id.btn_go_arrive_date_private_obf)
    private Button GoArriveDate;
    @BindView(id = R.id.btn_go_arrive_time_private_obf)
    private Button GoArriveTime;
    @BindView(id = R.id.spinner_go_tools_name_private)
    private Spinner GoTools;
    @BindView(id = R.id.edit_go_fee)
    private EditText GoFees;

    @BindView(id = R.id.btn_back_start_date_private_obf)
    private Button BackStartDate;
    @BindView(id = R.id.btn_back_start_time_private_obf)
    private Button BackStartTime;
    @BindView(id = R.id.edit_back_start_place)
    private EditText BackStartPlace;
    @BindView(id = R.id.edit_back_arrive_place)
    private EditText BackArrivePlace;
    @BindView(id = R.id.btn_back_arrive_date_private_obf)
    private Button BackArriveDate;
    @BindView(id = R.id.btn_back_arrive_time_private_obf)
    private Button BackArriveTime;
    @BindView(id = R.id.spinner_back_tools_name_danger)
    private Spinner BackTools;
    @BindView(id = R.id.edit_back_fee)
    private EditText BackFees;

    @BindView(id = R.id.edit_bonus_obf)
    private EditText Bonus;
    @BindView(id = R.id.edit_room_fee_obf)
    private EditText RoomFees;
    @BindView(id = R.id.edit_other_fee_obf)
    private EditText OtherFees;
    @BindView(id = R.id.edit_addition_private_obf)
    private EditText Remarks;
    @BindView(id = R.id.recycler_pic_listview_private)
    private RecyclerView imgListView;
    @BindView(id = R.id.btn_submit_private_form_obf)
    private Button submit;

    private List<String> imgPathList;   //receipt图片路径
    private int _id;
    private FormContentInfo info = new FormContentInfo();


    private DatePickerDialog dateDialog;
    private TimePickerDialog timeDialog;

    private int Year;
    private int Month;
    private int Day;
    private int Hour;
    private int Minute;

    private OnEditTextEdibleListener mOnEditTextEdibleListener;

    public void setOnEditTextEdibleListener(OnEditTextEdibleListener onEditTextEdibleListener) {
        this.mOnEditTextEdibleListener = onEditTextEdibleListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obf);
        AnnotateUtil.initBindView(this);
        mPresenter = new FormContentInfoPresenter(this);
        imgPathList = new ArrayList<>();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String flag = bundle.getString(Flags.TO_OBF_FLAG);
            if (Flags.FLAG_FROM_ADD_RECEIPT.equals(flag)) {
                edit.setVisibility(View.GONE);
                setEditable(true);
                initView();
                initListener();
            } else if (Flags.FLAG_FROM_LIST_RECEIPT.equals(flag)) {
                _id = bundle.getInt(Flags.FLAG_INFO_ID);
                edit.setVisibility(View.VISIBLE);
                setEditable(false);
                mPresenter.getData();
                initListener();
            }
        } else {
            edit.setVisibility(View.GONE);
            setEditable(true);
            initView();
            initListener();
        }
    }


    private void initListener() {
        edit.setOnClickListener(this);

        GoStartDate.setOnClickListener(this);
        GoArriveDate.setOnClickListener(this);
        GoStartTime.setOnClickListener(this);
        GoArriveTime.setOnClickListener(this);

        BackStartDate.setOnClickListener(this);
        BackArriveDate.setOnClickListener(this);
        BackStartTime.setOnClickListener(this);
        BackArriveTime.setOnClickListener(this);

        GoTools.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tools = (String) parent.getItemAtPosition(position);
                info.setGoTools(tools);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (info != null) {
                    int position = -1;
                    String tools[] = getResources().getStringArray(R.array.transport_tools);
                    for (int i = 0; i < tools.length; i++) {
                        if (info.getGoTools().equals(tools[i])) {
                            position = i;
                            parent.setSelection(position);
                        }
                    }
                }
            }
        });

        BackTools.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tools = (String) parent.getItemAtPosition(position);
                info.setBackTools(tools);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(this);
    }


    private void initView() {
        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        GoStartDate.setText(formatDate(Year, Month, Day));
        GoStartTime.setText(formatTime(Hour, Minute));
        GoArriveDate.setText(formatDate(Year, Month, Day));
        GoArriveTime.setText(formatTime(Hour, Minute));
        BackStartDate.setText(formatDate(Year, Month, Day));
        BackStartTime.setText(formatTime(Hour, Minute));
        BackArriveDate.setText(formatDate(Year, Month, Day));
        BackArriveTime.setText(formatTime(Hour, Minute));
    }

    @Override
    public void setToView(FormContentInfo info) {
        GoStartDate.setText(info.getGoStartDate());
        GoStartTime.setText(info.getGoStartTime());
        GoArriveDate.setText(info.getGoArriveDate());
        GoArriveTime.setText(info.getGoArriveTime());
        GoStartPlace.setText(info.getGoStartPlace());
        GoArrivePlace.setText(info.getGoArrivePlace());
        GoFees.setText(info.getGoToolsFees() + "");

        BackStartDate.setText(info.getBackStartDate());
        BackStartTime.setText(info.getBackStartTime());
        BackArriveDate.setText(info.getBackArriveDate());
        BackArriveTime.setText(info.getBackArriveTime());
        BackStartPlace.setText(info.getBackStartPlace());
        BackArrivePlace.setText(info.getBackArrivePlace());
        BackFees.setText(info.getBackToolsFees() + "");

        Bonus.setText(info.getBonus() + "");
        RoomFees.setText(info.getRoomFees() + "");
        Remarks.setText(info.getMarks());

        imgPathList = info.getReceiptsPath();
    }

    @Override
    public FormContentInfo getFromView() {

        info.setGoStartDate(GoStartDate.getText().toString());
        info.setGoStartTime(GoStartTime.getText().toString());
        info.setGoStartPlace(GoStartPlace.getText().toString());
        info.setGoArriveDate(GoArriveDate.getText().toString());
        info.setGoArriveTime(GoArriveTime.getText().toString());
        info.setGoArrivePlace(GoArrivePlace.getText().toString());
        info.setGoToolsFees(Double.parseDouble(TextUtils.isEmpty(GoFees.getText().toString()) ? "0" : GoFees.getText().toString()));

        info.setBackStartDate(BackStartDate.getText().toString());
        info.setBackStartTime(BackStartTime.getText().toString());
        info.setBackStartPlace(BackStartPlace.getText().toString());
        info.setBackArriveDate(BackArriveDate.getText().toString());
        info.setBackArriveTime(BackArriveTime.getText().toString());
        info.setBackArrivePlace(BackArrivePlace.getText().toString());
        info.setBackToolsFees(Double.parseDouble(TextUtils.isEmpty(BackFees.getText().toString()) ? "0" : BackFees.getText().toString()));

        info.setBonus(Double.parseDouble(TextUtils.isEmpty(Bonus.getText().toString()) ? "0" : Bonus.getText().toString()));
        info.setRoomFees(Double.parseDouble(TextUtils.isEmpty(RoomFees.getText().toString()) ? "0" : RoomFees.getText().toString()));
        info.setMarks(Remarks.getText().toString());

        info.setReceiptsPath(imgPathList);
        return info;
    }

    private String formatTime(int hour, int minute) {
        String strHour = "" + hour;
        String strMinute = "" + minute;
        if (hour < 10) {
            strHour = "0" + hour;
        }
        if (minute < 10) {
            strMinute = "0" + minute;
        }
        return strHour + ":" + strMinute;
    }

    private String formatDate(int year, int month, int day) {
        String strMonth = "" + month;
        String strDay = "" + day;
        if (month < 10) {
            strMonth = "0" + month;
        }
        if (day < 10) {
            strDay = "0" + day;
        }
        return year + "-" + strMonth + "-" + strDay;
    }

    private void initDateDialog(DatePickListener listener, int year, int month, int day) {
        dateDialog = new DatePickerDialog(this, listener, year, month, day);
        dateDialog.show();
    }

    private void initTimeDialog(TimePickListener listener, int hour, int minute) {
        timeDialog = new TimePickerDialog(this, listener, hour, minute, true);
        timeDialog.show();
    }

    @Override
    public int getId() {
        return _id;
    }


    public interface OnEditTextEdibleListener {
        public void edibleStateChanged(boolean isEdible);
    }

    public void setEditable(boolean flag) {
        if (mOnEditTextEdibleListener != null) {
            mOnEditTextEdibleListener.edibleStateChanged(flag);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_title_bar:
                boolean flag = mPresenter.isEditable();
                setEditable(!flag);
                break;
            case R.id.btn_go_start_date_private_obf:
                DatePickListener goStartListener = new DatePickListener(GoStartDate);
                if (info == null) {
                    initDateDialog(goStartListener, Year, Month, Day);
                } else {
                    if (!TextUtils.isEmpty(info.getGoStartDate())) {
                        List<Integer> list = getDateList(info.getGoStartDate());
                        initDateDialog(goStartListener, list.get(0), list.get(1), list.get(2));
                    } else {
                        initDateDialog(goStartListener, Year, Month, Day);
                    }
                }
                break;
            case R.id.btn_go_start_time_private_obf:
                TimePickListener goStartTimeListener = new TimePickListener(GoStartTime);
                if (info == null) {
                    initTimeDialog(goStartTimeListener, Hour, Minute);
                } else {
                    if (!TextUtils.isEmpty(info.getGoStartTime())) {
                        List<Integer> list = getDateList(info.getGoStartTime());
                        initTimeDialog(goStartTimeListener, list.get(0), list.get(1));
                    } else {
                        initTimeDialog(goStartTimeListener, Hour, Minute);
                    }
                }
                break;
            case R.id.btn_go_arrive_date_private_obf:
                DatePickListener goArriveListener = new DatePickListener(GoArriveDate);
                if (info == null) {
                    initDateDialog(goArriveListener, Year, Month, Day);
                } else {
                    if (!TextUtils.isEmpty(info.getGoArriveDate())) {
                        List<Integer> list = getDateList(info.getGoArriveDate());
                        initDateDialog(goArriveListener, list.get(0), list.get(1), list.get(2));
                    } else {
                        initDateDialog(goArriveListener, Year, Month, Day);
                    }
                }
                break;
            case R.id.btn_go_arrive_time_private_obf:
                TimePickListener goArriveTimeListener = new TimePickListener(GoArriveTime);
                if (info == null) {
                    initTimeDialog(goArriveTimeListener, Hour, Minute);
                } else {
                    if (!TextUtils.isEmpty(info.getGoArriveTime())) {
                        List<Integer> list = getDateList(info.getGoArriveTime());
                        initTimeDialog(goArriveTimeListener, list.get(0), list.get(1));
                    } else {
                        initTimeDialog(goArriveTimeListener, Hour, Minute);
                    }
                }
                break;
            case R.id.btn_back_start_date_private_obf:
                DatePickListener backStartListener = new DatePickListener(BackStartDate);
                if (info == null) {
                    initDateDialog(backStartListener, Year, Month, Day);
                } else {
                    if (!TextUtils.isEmpty(info.getBackStartDate())) {
                        List<Integer> list = getDateList(info.getBackStartDate());
                        initDateDialog(backStartListener, list.get(0), list.get(1), list.get(2));
                    } else {
                        initDateDialog(backStartListener, Year, Month, Day);
                    }

                }
                break;
            case R.id.btn_back_start_time_private_obf:
                TimePickListener backStartTimeListener = new TimePickListener(BackStartTime);
                if (info == null) {
                    initTimeDialog(backStartTimeListener, Hour, Minute);
                } else {
                    if (!TextUtils.isEmpty(info.getBackStartTime())) {
                        List<Integer> list = getDateList(info.getBackStartTime());
                        initTimeDialog(backStartTimeListener, list.get(0), list.get(1));
                    } else {
                        initTimeDialog(backStartTimeListener, Hour, Minute);
                    }
                }
                break;
            case R.id.btn_back_arrive_date_private_obf:
                DatePickListener backArriveListener = new DatePickListener(BackArriveDate);
                if (info == null) {
                    initDateDialog(backArriveListener, Year, Month, Day);
                } else {
                    if (!TextUtils.isEmpty(info.getBackArriveDate())) {
                        List<Integer> list = getDateList(info.getBackArriveDate());
                        initDateDialog(backArriveListener, list.get(0), list.get(1), list.get(2));
                    } else {
                        initDateDialog(backArriveListener, Year, Month, Day);
                    }

                }
                break;
            case R.id.btn_back_arrive_time_private_obf:
                TimePickListener backArriveTimeListener = new TimePickListener(BackArriveTime);
                if (info == null) {
                    initTimeDialog(backArriveTimeListener, Hour, Minute);
                } else {
                    if (!TextUtils.isEmpty(info.getBackArriveTime())) {
                        List<Integer> list = getDateList(info.getBackArriveTime());
                        initTimeDialog(backArriveTimeListener, list.get(0), list.get(1));
                    } else {
                        initTimeDialog(backArriveTimeListener, Hour, Minute);
                    }

                }
                break;
            case R.id.btn_submit_private_form_obf:
                mPresenter.saveData();
                break;
        }
    }

    private List<Integer> getDateList(String date) {
        List<Integer> dateList = new ArrayList<>();
        String[] parts = date.split("-");
        for (int i = 0; i < parts.length; i++) {
            dateList.add(Integer.parseInt(parts[i]));
        }
        return dateList;
    }

    private List<Integer> getTimeList(String time) {
        List<Integer> dateList = new ArrayList<>();
        String[] parts = time.split("-");
        for (int i = 0; i < parts.length; i++) {
            dateList.add(Integer.parseInt(parts[i]));
        }
        return dateList;
    }

    public class DatePickListener implements DatePickerDialog.OnDateSetListener {
        private int mYear;
        private int mMonth;
        private int mDay;
        private Button btn;

        public DatePickListener() {
        }

        public DatePickListener(Button btn) {
            this.btn = btn;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            setDate();
        }

        public void setDate() {
            String strMonth = "" + mMonth;
            String strDay = "" + mDay;
            if (mMonth < 10) {
                strMonth = "0" + mMonth;
            }
            if (mDay < 10) {
                strDay = "0" + mDay;
            }
            btn.setText(mYear + "-" + strMonth + "-" + strDay);
        }
    }

    public class TimePickListener implements TimePickerDialog.OnTimeSetListener {

        private int mHour;
        private int mMinute;
        private Button btn;

        public TimePickListener() {
        }

        public TimePickListener(Button btn) {
            this.btn = btn;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            setTime();
        }

        public void setTime() {
            String strHour = "" + mHour;
            String strMinute = "" + mMinute;
            if (mHour < 10) {
                strHour = "0" + mHour;
            }
            if (mMinute < 10) {
                strMinute = "0" + mMinute;
            }
            btn.setText(strHour + ":" + strMinute);
        }
    }


}
