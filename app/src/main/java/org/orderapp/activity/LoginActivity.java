package org.orderapp.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.base.BaseActivity;
import org.orderapp.entity.User;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.util.ToastUtil;


/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    /**
     * 账号
     */
    private EditText et_account;
    /**
     * 密码
     */
    private EditText et_password;


    @Override
    protected int provideLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(this::onClick);
        findViewById(R.id.btn_create_account).setOnClickListener(this::onClick);

        setTitleText("OrderApp");

        showBack();

        if (APP.sDebug) {
            et_account.setText("user1");
            et_password.setText("123456");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            login();
        } else if (v.getId() == R.id.btn_create_account) {
            gotoActivity(RegisterActivity.class);
        }
    }

    /**
     * 登录方法
     */
    private void login() {
        //账号
        String account = et_account.getText().toString();
        //密码
        String password = et_password.getText().toString();

        if (TextUtils.isEmpty(account)) {
            ToastUtil.show("Please enter account");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("Please enter password");
            return;
        }

        showLoading("login...");
        FirebaseUtil.getUserRef()
                .get()
                .addOnCompleteListener(task -> {
                    User user = null;
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        User tUser = child.getValue(User.class);
                        //判断账号密码是否匹配
                        if (account.equalsIgnoreCase(tUser.getAccount()) && password.equals(tUser.getPassword())) {
                            user = tUser;
                            break;
                        }
                    }

                    hideLoading();
                    if (user == null) {
                        ToastUtil.show("account or password wrong");
                        return;
                    }

                    APP.sUser = user;

                    finish();
                });

    }
}
