package org.orderapp.util;

import android.widget.Toast;

import org.orderapp.APP;


/**
 * 提示类
 */
public class ToastUtil {
    /**
     * 短提示显示
     * @param message 显示信息
     */
    public static void show(String message) {
        Toast.makeText(APP.sContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短提示显示
     * @param res 显示信息,对应string.xml的id
     */
    public static void show(int res) {
        Toast.makeText(APP.sContext, res, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长提示显示
     * @param message 显示信息
     */
    public static void showLong(String message) {
        Toast.makeText(APP.sContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长提示显示
     * @param res 显示信息,对应string.xml的id
     */
    public static void showLong(int res) {
        Toast.makeText(APP.sContext, res, Toast.LENGTH_LONG).show();
    }
}
