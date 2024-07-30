package org.orderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.orderapp.R;
import org.orderapp.activity.base.BaseActivity;
import org.orderapp.entity.ScanResult;
import org.orderapp.entity.Shop;
import org.orderapp.util.JsonUtil;
import org.orderapp.util.L;
import org.orderapp.util.ToastUtil;

/**
 * 店铺页面
 */
public class ShopActivity extends BaseActivity {

    private Shop shop = null;

    @Override
    protected int provideLayout() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_scan).setOnClickListener(this::onClick);

        showBack();
    }

    @Override
    protected void initData() {
        Intent data = getIntent();
        if (data != null && data.hasExtra("data")) {
            shop = data.getParcelableExtra("data");

            setShopData();
        } else {
            ToastUtil.show("error");
            finish();
        }
    }

    private void setShopData() {
        if (shop != null) {
            setTitleText(shop.getName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:
                if(isLogin()) {
                    barcodeLauncher.launch(new ScanOptions());
                }
                break;
            default:

                break;
        }
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    ToastUtil.show("Cancelled");
                } else {
                    L.d(result.getContents());
                    //解释识别的字符串
                    ScanResult scanResult = JsonUtil.fromJson(result.getContents(), ScanResult.class);

                    //判断店铺名是否相同
                    if (shop.getKey().equals(scanResult.getShop())) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("data", shop);
                        bundle.putInt(OrderActivity.KEY_TABLE_NUM, scanResult.getTableNum());
                        gotoActivity(OrderActivity.class, bundle);
                    } else {
                        ToastUtil.show("error shop");
                        finish();
                    }
                }
            });


}