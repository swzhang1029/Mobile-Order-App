package org.orderapp.util;

import android.util.Log;

import org.orderapp.APP;


/**
 * 日志工具类
 */
public class L {
    /**
     * 日志打印的TAG
     */
    public final static String TAG = "testlog";


    /**
     * debug log
     *
     * @param message
     */
    public static void d(String message) {
        if (APP.sDebug) {
            Log.d(TAG, message);
        }
    }
}
