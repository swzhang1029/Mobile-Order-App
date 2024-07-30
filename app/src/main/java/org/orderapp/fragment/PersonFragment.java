package org.orderapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.LoginActivity;
import org.orderapp.activity.OrderListActivity;
import org.orderapp.fragment.base.BaseFragment;


/**
 * 个人页面
 */
public class PersonFragment extends BaseFragment {
    /**
     * 未登录布局
     */
    private RelativeLayout rl_unlogin;
    /**
     * 登录布局
     */
    private RelativeLayout rl_login;
    private TextView tv_account;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_person, null);
            rl_login = view.findViewById(R.id.rl_login);
            rl_unlogin = view.findViewById(R.id.rl_unlogin);
            tv_account = view.findViewById(R.id.tv_account);

            view.findViewById(R.id.btn_login).setOnClickListener(this::onClick);
            view.findViewById(R.id.tv_order_list).setOnClickListener(this::onClick);
            view.findViewById(R.id.tv_exit).setOnClickListener(this::onClick);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rl_login.setVisibility(View.GONE);
        rl_unlogin.setVisibility(View.GONE);
        if (checkLogin()) {
            rl_login.setVisibility(View.VISIBLE);
            tv_account.setText(APP.sUser.getAccount());
        } else {
            rl_unlogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                isLogin();
                break;
            case R.id.tv_order_list:
                gotoActivity(OrderListActivity.class);
                break;
            case R.id.tv_exit:
                logout();
                break;
            default:
                break;
        }
    }
}
