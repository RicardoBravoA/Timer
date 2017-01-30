package com.rba.timer.component;

/**
 * Created by Ricardo Bravo on 29/01/17.
 */


public interface TimerInterface {

    void onTimerDown(long milliseconds);

    void onTimerFinish();
}
