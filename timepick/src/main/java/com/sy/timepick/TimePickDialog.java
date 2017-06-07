package com.sy.timepick;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Date: 2017-06-07 09:49
 */
public class TimePickDialog extends Dialog implements View.OnClickListener {
    /**
     * 回调的时间类型
     */
    public static final String TIME_TYPE = "yyyy.MM.dd HH.mm";
    /**
     * 五个滑动 选择器
     */
    private EasyPickerView mEpvYear;
    private EasyPickerView mEpvMonth;
    private EasyPickerView mEpvDay;
    private EasyPickerView mEpvHour;
    private EasyPickerView mEpvMinute;
    /**
     * 滑动选择器对应的集合
     */
    private List<String> mYearList;
    private List<String> mMonthList;
    private List<String> mDayList;
    private List<String> mHourList;
    private List<String> mMinuteList;

    private Context mContext;
    /**
     * 当前时间 年月日
     */
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    /**
     * dialog标题栏
     */
    private String mTitle;

    /**
     * 构造,传入标题 和 上下文
     */
    public TimePickDialog(Context context, String title) {
        super(context);
        this.mContext = context;
        this.mTitle = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_picker);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化控件
     *
     * 设置dialog宽度
     */
    private void initView() {
        mEpvYear = (EasyPickerView) findViewById(R.id.epv_year);
        mEpvMonth = (EasyPickerView) findViewById(R.id.epv_month);
        mEpvDay = (EasyPickerView) findViewById(R.id.epv_day);
        mEpvHour = (EasyPickerView) findViewById(R.id.epv_hour);
        mEpvMinute = (EasyPickerView) findViewById(R.id.epv_minute);

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) mContext).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display display = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (display.getWidth() * 1); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(params);
    }

    /**
     * 初始化数据
     * 获取当前年月日
     * 获取时间集合  年月日 时分
     */
    private void initData() {
        Calendar now = Calendar.getInstance();
        mCurrentYear = now.get(Calendar.YEAR);
        mCurrentMonth = (now.get(Calendar.MONTH) + 1);
        mCurrentDay = now.get(Calendar.DAY_OF_MONTH);


        mYearList = new ArrayList<>();
        mMonthList = new ArrayList<>();
        mDayList = new ArrayList<>();
        mHourList = new ArrayList<>();
        mMinuteList = new ArrayList<>();


        for (int i = now.get(Calendar.YEAR); i >= 2000; i--) {
            mYearList.add(i + "");
        }


        for (int i = 12; i >= 1; i--) {
            if (i < 10) {
                mMonthList.add("0" + i + "");
            } else {
                mMonthList.add(i + "");
            }
        }

        for (int i = 31; i >= 1; i--) {
            if (i < 10) {
                mDayList.add("0" + i + "");
            } else {
                mDayList.add(i + "");
            }
        }

        for (int i = 23; i >= 0; i--) {
            if (i < 10) {
                mHourList.add("0" + i + "");
            } else {
                mHourList.add(i + "");
            }
        }

        for (int i = 59; i >= 0; i--) {
            if (i < 10) {
                mMinuteList.add("0" + i + "");
            } else {
                mMinuteList.add(i + "");
            }
        }


    }

    /**
     * 设置滑动列表数据
     * 设置标题
     * 设置点击事件
     */
    private void initEvent() {
        initEasyPickerView(mEpvYear, mYearList);
        initEasyPickerView(mEpvMonth, mMonthList);
        initEasyPickerView(mEpvDay, mDayList);
        initEasyPickerView(mEpvHour, mHourList);
        initEasyPickerView(mEpvMinute, mMinuteList);
        //设置标题
        ((TextView) findViewById(R.id.tv_title)).setText(mTitle);
        //设置下方三个按钮监听
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_today).setOnClickListener(this);
        findViewById(R.id.tv_yes).setOnClickListener(this);

    }

    /**
     * 设置数据
     */
    private void initEasyPickerView(EasyPickerView epvYear, List<String> yearList) {
        epvYear.setDataList(yearList);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel) {
            this.dismiss();
        } else if (id == R.id.tv_today) {
            setCurrentDay();
        } else if (id == R.id.tv_yes) {
            this.dismiss();

            if (mTimePickListener != null) {
                mTimePickListener.choseTime(
                        mYearList.get(mEpvYear.getCurIndex()) +
                                mMonthList.get(mEpvMonth.getCurIndex()) +
                                mDayList.get(mEpvDay.getCurIndex()) +
                                mHourList.get(mEpvHour.getCurIndex()) +
                                mMinuteList.get(mEpvMinute.getCurIndex())
                );
            }
        }
    }

    /**
     * 设置滑动选择框为当天时间
     */
    private void setCurrentDay() {
        //设置当天
        for (int i = 0; i < mDayList.size(); i++) {
            if (mDayList.get(i).equals(mCurrentDay <= 9 ? "0" + mCurrentDay : mCurrentDay)) {
                mEpvDay.moveTo(i);
                break;
            }
        }
        //设置当月
        for (int i = 0; i < mMonthList.size(); i++) {
            if (mMonthList.get(i).equals(
                    mCurrentMonth <= 9 ? "0" + mCurrentMonth : mCurrentMonth)) {
                mEpvMonth.moveTo(i);
                break;
            }
        }
        //设置当年
        mEpvYear.moveTo(0);
        //初始化小时 和 分钟
        mEpvHour.moveTo(mHourList.size() - 1);
        mEpvMinute.moveTo(mMinuteList.size() - 1);
    }

    /**
     * 时间选择回调监听
     */
    private TimePickListener mTimePickListener;


    public interface TimePickListener {
        void choseTime(String time);
    }

    public void setTimePickListener(TimePickListener timePickListener) {
        mTimePickListener = timePickListener;
    }
}
