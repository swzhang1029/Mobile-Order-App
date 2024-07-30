package org.orderapp.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.orderapp.R;
import org.orderapp.activity.base.BaseActivity;
import org.orderapp.entity.User;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.util.ToastUtil;

import java.util.UUID;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {
    /**
     * 账号
     */
    private EditText et_account;
    /**
     * 密码
     */
    private EditText et_password;
    /**
     * 确认密码
     */
    private EditText et_confirm_password;



    @Override
    protected int provideLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);


        findViewById(R.id.btn_sign_up).setOnClickListener(this::onClick);
        findViewById(R.id.iv_back).setOnClickListener(this::onClick);

        setTitleText("SIGN UP");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_up) {
            register();
        } else if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    /**
     * 注册方法
     */
    private void register() {
        //账号
        String account = et_account.getText().toString();
        //密码
        String password = et_password.getText().toString();
        //确认密码
        String confirmPassword = et_confirm_password.getText().toString();


        if (TextUtils.isEmpty(account)) {
            ToastUtil.show("Please enter account");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("Please enter password");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtil.show("Please enter confirmPassword");
            return;
        }

        if (!password.equals(confirmPassword)) {
            ToastUtil.show("The two passwords are different");
            return;
        }

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setAccount(account);
        user.setPassword(password);

        showLoading("register...");
        FirebaseUtil.getUserRef()
                .push()
                .setValue(user)
                .addOnSuccessListener(unused -> {
                    ToastUtil.show("Register success");
                    hideLoading();
                    finish();
                })
                .addOnFailureListener(e -> {
                    ToastUtil.show("Register failure");
                    hideLoading();
                });


    }
}
