package org.orderapp.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.LoginActivity;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View view;
    protected TextView tv_title;

    /**
     * 标题栏右边按钮，默认不显示
     */
    private ImageView iv_title_right;

    protected void setTitle(String title) {
        tv_title = view.findViewById(R.id.tv_title);
        if (tv_title != null) {
            tv_title.setText(title);
        }
    }

    /**
     * 设置右边按钮，并显示
     *
     * @param res
     */
    public void setRightAction(Integer res) {
        iv_title_right = view.findViewById(R.id.iv_title_right);
        if (iv_title_right != null) {
            iv_title_right.setVisibility(View.VISIBLE);
            iv_title_right.setImageResource(res);
            iv_title_right.setOnClickListener(this);
        }
    }

    protected void gotoActivity(Class clazz) {
        Intent intentFav = new Intent(getContext(), clazz);
        startActivity(intentFav);
    }

    /**
     * 页面跳转
     *
     * @param clazz
     */
    protected void gotoActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 检测登录状态，如果未登录跳转到登录页面
     */
    protected boolean isLogin() {
        if (APP.sUser == null) {
            gotoActivity(LoginActivity.class);
        }
        return APP.sUser != null;
    }

    /**
     * 检测登录状态
     */
    protected boolean checkLogin() {
        return APP.sUser != null;
    }

    /**
     * 登出
     */
    protected void logout() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout account?")
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setPositiveButton("Confirm", (dialog, which) -> {
                    //清除用户信息
                    APP.sUser = null;
                    onResume();
                })
                .show();
    }
}
