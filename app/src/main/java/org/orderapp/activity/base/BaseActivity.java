package org.orderapp.activity.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.LoginActivity;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.util.ToastUtil;


/**
 * Activity基类，封装了一些共用方法
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 标题栏左边按钮，默认显示
     */
    private ImageView iv_title_left;
    /**
     * 标题
     */
    private TextView tv_title;
    /**
     * 标题栏右边按钮，默认不显示
     */
    private ImageView iv_title_right;

    private RelativeLayout rl_loading;
    private TextView tv_loading;
    private TextView tv_loading_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayout());
        //适配UI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        initTitle();


        rl_loading = findViewById(R.id.rl_loading);
        tv_loading = findViewById(R.id.tv_loading);
        tv_loading_content = findViewById(R.id.tv_loading_content);

        initView();
        initData();
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        iv_title_left = findViewById(R.id.iv_title_left);
        tv_title = findViewById(R.id.tv_title);

        //设置点击事件
        if (iv_title_left != null) {
            iv_title_left.setOnClickListener(v -> finish());
        }
    }


    /**
     * 设置右边按钮，并显示
     *
     * @param res
     */
    public void setRightAction(Integer res) {
        iv_title_right = findViewById(R.id.iv_title_right);
        if (iv_title_right != null) {
            iv_title_right.setVisibility(View.VISIBLE);
            iv_title_right.setImageResource(res);
            iv_title_right.setOnClickListener(this);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitleText(String title) {
        if (tv_title != null) {
            tv_title.setText(title);
        }
    }

    /**
     * 隐藏返回按钮
     */
    protected void hideBack() {
        if (iv_title_left != null) {
            iv_title_left.setVisibility(View.GONE);
        }
    }

    /**
     * 显示返回按钮
     */
    protected void showBack() {
        if (iv_title_left != null) {
            iv_title_left.setVisibility(View.VISIBLE);
        }
    }

    protected void showLoading(String tip) {
        if (tv_loading_content != null) {
            tv_loading_content.setVisibility(View.GONE);
        }
        if (tv_loading != null) {
            runOnUiThread(() -> tv_loading.setText(tip));
        }
        if (rl_loading != null) {
            runOnUiThread(() -> rl_loading.setVisibility(View.VISIBLE));
        }
    }

    protected void hideLoading() {
        if (rl_loading != null) {
            runOnUiThread(() -> rl_loading.setVisibility(View.GONE));
        }
    }


    /**
     * 页面跳转
     *
     * @param clazz
     */
    protected void gotoActivity(Class clazz) {
        Intent intentFav = new Intent(this, clazz);
        startActivity(intentFav);
    }

    /**
     * 页面跳转
     *
     * @param clazz
     */
    protected void gotoActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    //下面的是抽象方法,继承的时候需要实现

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int provideLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 登出
     */
    protected void logout(){
        new AlertDialog.Builder(this)
                .setTitle("Logout account?")
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setPositiveButton("Confirm", (dialog, which) -> {
                    //清除用户信息
                    APP.sUser = null;
                })
                .show();
    }

    /**
     * 检测登录状态
     */
    protected boolean isLogin() {
        if (APP.sUser == null) {
            gotoActivity(LoginActivity.class);
        }
        return APP.sUser != null;
    }
}