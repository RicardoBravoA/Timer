package com.rba.timer.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.rba.timer.R;
import com.rba.timer.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 29/01/17.
 */

public class TimerView extends LinearLayout {

    private Context mContext;
    private long mHours = 0;
    private long mMinutes = 0;
    private long mSeconds = 0;
    private long mMilliSeconds = 0;
    private long mDays = 0;
    private TimerInterface mListener;
    private CountDownTimer mCountDownTimer;
    private View view;
    @BindView(R.id.lblDay) AppCompatTextView lblDay;
    @BindView(R.id.lblHour) AppCompatTextView lblHour;
    @BindView(R.id.lblMinute) AppCompatTextView lblMinute;
    @BindView(R.id.lblSecond) AppCompatTextView lblSecond;
    @BindView(R.id.lblText) AppCompatTextView lblText;
    @BindView(R.id.linText) LinearLayout linText;
    @BindView(R.id.linTime) LinearLayout linTime;
    @BindView(R.id.lblDayText) AppCompatTextView lblDayText;
    @BindView(R.id.lblHourText) AppCompatTextView lblHourText;
    @BindView(R.id.lblMinuteText) AppCompatTextView lblMinuteText;
    @BindView(R.id.lblSecondText) AppCompatTextView lblSecondText;
    private boolean showDisplay = false;

    public TimerView(Context context) {
        super(context);
        mContext = context;
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public void setOnTimerListener(TimerInterface listener) {
        mListener = listener;
    }

    private void init(AttributeSet attrs) {

        view = inflate(mContext, R.layout.item_timer, this);

        ButterKnife.bind(this, view);

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.Timer, 0, 0);

        if (a != null) {

            showDisplay = a.getBoolean(R.styleable.Timer_showDisplay, false);
            String milliStr = a.getString(R.styleable.Timer_time);
            if (!TextUtils.isEmpty(milliStr) && TextUtils.isDigitsOnly(milliStr)) {
                mMilliSeconds = Long.parseLong(a.getString(R.styleable.Timer_time));
                setTime(mMilliSeconds);
                //startCountDown();
            }

            a.recycle();
        }

        displayText();

    }

    private void initCounter() {
        mCountDownTimer = new CountDownTimer(mMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                calculateTime(millisUntilFinished);
                if (mListener != null) {
                    mListener.onTimerDown(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                calculateTime(0);
                if (mListener != null) {
                    mListener.onTimerFinish();
                }
            }
        };
    }

    public void showLabelFinished(){
        showDisplay = false;
        linText.setVisibility(View.GONE);
        linTime.setVisibility(View.GONE);
        lblText.setVisibility(View.VISIBLE);
        stopCountDown();
    }

    public void startCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }
    }

    public void stopCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    public void setShowDisplay(boolean state){
        showDisplay = state;
    }

    public void setTime(long milliSeconds) {
        Log.i("z- setTime", ""+milliSeconds);
        mMilliSeconds = milliSeconds;
        initCounter();
        calculateTime(milliSeconds);
        startCountDown();
    }

    private void calculateTime(long milliSeconds) {

        mSeconds = (milliSeconds / 1000) % 60;
        mMinutes = ((milliSeconds / (1000 * 60)) % 60);
        mHours = ((milliSeconds / (1000 * 60 * 60)) % 24);
        mDays = (milliSeconds / (1000 * 60 * 60 * 24));

        displayText();
    }

    private void displayText() {

        Log.i("z- time", ""+mDays+" - "+mHours+" - "+mMinutes+" - "+mSeconds);

        if(mDays == 0 && mSeconds == 0 && mHours == 0 && mMinutes == 0){
            linText.setVisibility(View.GONE);
            linTime.setVisibility(View.GONE);
            lblText.setVisibility(View.VISIBLE);
            showLabelFinished();
        }else{

            if(showDisplay){
                linText.setVisibility(View.VISIBLE);
            }else{
                linText.setVisibility(View.GONE);
            }

            linTime.setVisibility(View.VISIBLE);
            lblText.setVisibility(View.GONE);


            if(mDays == 0){
                lblDay.setVisibility(View.GONE);
                lblDayText.setVisibility(View.GONE);
            }else{
                lblDay.setVisibility(View.VISIBLE);
                lblDayText.setVisibility(View.VISIBLE);
                lblDay.setText(Util.twoDigits(mContext, mDays));

                if(mDays == 1){
                    lblDayText.setText(mContext.getString(R.string.value_day));
                }else{
                    lblDayText.setText(mContext.getString(R.string.value_days));
                }
            }


            lblHour.setVisibility(View.VISIBLE);
            lblHourText.setVisibility(View.VISIBLE);
            lblHour.setText(Util.twoDigits(mContext, mHours));
            if(mHours == 1){
                lblHourText.setText(mContext.getString(R.string.value_hour));
            }else{
                lblHourText.setText(mContext.getString(R.string.value_hours));
            }

            lblMinute.setVisibility(View.VISIBLE);
            lblMinuteText.setVisibility(View.VISIBLE);
            lblMinute.setText(Util.twoDigits(mContext, mMinutes));
            if(mMinutes == 1){
                lblMinuteText.setText(mContext.getString(R.string.value_minute));
            }else{
                lblMinuteText.setText(mContext.getString(R.string.value_minutes));
            }

            lblSecond.setVisibility(View.VISIBLE);
            lblSecondText.setVisibility(View.VISIBLE);
            lblSecond.setText(Util.twoDigits(mContext, mSeconds));
            if(mSeconds == 1){
                lblSecondText.setText(mContext.getString(R.string.value_second));
            }else{
                lblSecondText.setText(mContext.getString(R.string.value_seconds));
            }

        }

    }


}
