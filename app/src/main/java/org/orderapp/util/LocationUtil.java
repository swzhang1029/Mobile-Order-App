package org.orderapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.orderapp.APP;

import java.nio.channels.AcceptPendingException;

public class LocationUtil {
    private static LocationManager mLocationManager = null;
    private static Location location = null;

    /**
     * 赤道半径（单位：米）
     */
    private static final double EQUATOR_RADIUS = 6378137;

    static {
        initGps();
    }

    public static void start() {
        if (ActivityCompat.checkSelfPermission(APP.sContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            L.d("没有定位权限");
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, listener);
    }

    public static void stop() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(listener);
        }
    }

    /**
     * 初始化
     */
    private static void initGps() {
        mLocationManager = (LocationManager) APP.sContext.getSystemService(Context.LOCATION_SERVICE);
    }

    private static LocationListener listener = location -> {
        APP.lat = location.getLatitude();
        APP.lng = location.getLongitude();

        L.d(APP.lat + "," + APP.lng);
    };

    /**
     * 打开google map导航
     *
     * @param activity
     * @param lat
     * @param lng
     */
    public static void loadNavigationView(Activity activity, double lat, double lng) {
        Uri navigation = Uri.parse("google.navigation:q=" + lat + "," + lng + "");
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navigation);
        navigationIntent.setPackage("com.google.android.apps.maps");
        activity.startActivity(navigationIntent);
    }

    /**
     * 计算两点间的距离
     *
     * @param longitude1 第一个点的经度
     * @param latitude1  第一个点的纬度
     * @param longitude2 第二个点的经度
     * @param latitude2  第二个点的纬度
     * @return 返回距离，单位m
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        // 纬度
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        // 经度
        double lon1 = Math.toRadians(longitude1);
        double lon2 = Math.toRadians(longitude2);
        // 纬度之差
        double a = lat1 - lat2;
        // 经度之差
        double b = lon1 - lon2;
        // 计算两点距离的公式
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(b / 2), 2)));
        // 弧长乘赤道半径, 返回单位: 米
        s = s * EQUATOR_RADIUS;
        return s;
    }

}
