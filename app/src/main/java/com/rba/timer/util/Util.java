package com.rba.timer.util;

import android.content.Context;

import com.rba.timer.R;

/**
 * Created by Ricardo Bravo on 29/01/17.
 */


public class Util {

    public static String twoDigits(Context context, long number) {
        if (number >= 0 && number < 10) {
            return context.getString(R.string.concat_number, String.valueOf(number));
        }

        return String.valueOf(number);
    }

}
