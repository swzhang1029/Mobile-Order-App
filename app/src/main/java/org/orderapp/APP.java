package org.orderapp;

import android.app.Application;
import android.content.Context;


import org.orderapp.entity.User;
import org.orderapp.firebase.FirebaseUtil;

import java.net.PortUnreachableException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 全局配置类
 */
public class APP extends Application {
    public static Context sContext = null;
//AIzaSyBejKmio-q511hHTEV8M-0yPhMDZFbRUyU
//    /**
//     * 当前登录的用户信息
//     */
//    public static User user;

    /**
     * 是否debug环境
     */
    public final static boolean sDebug = true;

    /**
     * 当前登录用户信息
     */
    public static User sUser = null;

    public static double lat = 0.0;
    public static double lng = 0.0;

    /**
     * 异步线程池
     */
    public static ThreadPoolExecutor sThreadPoolExecutor = null;
    /**
     * 异步线程队列
     */
    private LinkedBlockingQueue<Runnable> threadPoolQueue = new LinkedBlockingQueue();

    @Override
    public void onCreate() {
        super.onCreate();

        APP.sContext = this;

        //初始化线程池
        sThreadPoolExecutor = new ThreadPoolExecutor(
                5,
                50,
                200,
                TimeUnit.MILLISECONDS,
                threadPoolQueue
        );

        //初始化firebase
        FirebaseUtil.initFirebaseSdk();

        //生成测试数据
//        TestData.genGoodsData();
    }

}
