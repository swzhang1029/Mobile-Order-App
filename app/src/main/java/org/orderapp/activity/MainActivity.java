package org.orderapp.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.orderapp.R;
import org.orderapp.activity.base.BaseActivity;
import org.orderapp.entity.Shop;
import org.orderapp.util.L;
import org.orderapp.util.LocationUtil;
import org.orderapp.util.ToastUtil;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    /**
     * 导航
     */
    private BottomNavigationView navigation;

    /**
     * @return
     */
    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }



    @Override
    protected void initView() {

        initNavigation();
        initPermissions();

    }

    @Override
    protected void initData() {
        //开始定位
        LocationUtil.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束定位
        LocationUtil.stop();
    }

    @Override
    public void onClick(View v) {
    }


    /**
     * 初始化导航栏
     *
     * @return
     */
    private void initNavigation() {
        navigation = findViewById(R.id.navigation);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        NavController navController = navHostFragment.getNavController();
        navController.setGraph(R.navigation.main_navigation);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.fragment_home) {

            } else if (navDestination.getId() == R.id.fragment_person) {

            }
        });
        NavigationUI.setupWithNavController(navigation, navController);
    }

    /**
     * 判断是否需要申请权限
     */
    private void initPermissions() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "The following permissions must be granted :1. Camera permissions. \\n2. Location permission", 1001, perms);
        }
    }

    /**
     * 需要申请的权限
     */
    private String[] perms = {
            Manifest.permission.ACCESS_FINE_LOCATION,//定位权限
            Manifest.permission.CAMERA//相机权限
    };


}