package org.orderapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.base.BaseActivity;
import org.orderapp.adapter.GoodsAdapter;
import org.orderapp.entity.Goods;
import org.orderapp.entity.Order;
import org.orderapp.entity.Shop;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.util.ToastUtil;
import org.orderapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 下单页面
 */
public class OrderActivity extends BaseActivity {
    /**
     * 当前商品
     */
    private Shop shop = null;

    /**
     * 桌号
     */
    private TextView tv_talbe_number;

    /**
     * 桌号
     */
    private int tableNum = -1;

    /**
     * 商品列表UI
     */
    private RecyclerView recyclerView;
    /**
     * 商品列表数据
     */
    private List<Goods> allShopList;
    /**
     * 商品列表适配器
     */
    private GoodsAdapter goodsAdapter;
    private TextView tv_list;

    private TextView tv_total_price;

    public static final String KEY_TABLE_NUM = "KEY_TALBE_NUM";

    @Override
    protected int provideLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        tv_talbe_number = findViewById(R.id.tv_talbe_number);
        tv_list = findViewById(R.id.tv_list);
        tv_total_price = findViewById(R.id.tv_total_price);

        showBack();

        findViewById(R.id.btn_order).setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {
        Intent data = getIntent();
        if (!data.hasExtra("data")) {
            ToastUtil.show("Data error");
            finish();
        }
        shop = data.getParcelableExtra("data");
        tableNum = data.getIntExtra(KEY_TABLE_NUM, -1);
        setShopData();

        initList();
        getData();
    }

    private void setShopData() {
        if (shop == null || tableNum <= 0) {
            ToastUtil.show("shop error");
            return;
        }


        setTitleText(shop.getName());
        //桌号
        tv_talbe_number.setText(String.format("Table Number: %s", tableNum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_order:
                order();
                break;
            default:

                break;
        }
    }

    /**
     * 初始化列表适配器
     */
    private void initList() {
        recyclerView = findViewById(R.id.rv_list);
        allShopList = new ArrayList<>();

        goodsAdapter = new GoodsAdapter();
        goodsAdapter.setListener(new GoodsAdapter.Listener() {
            @Override
            public void itemClick(int position) {

            }

            @Override
            public void plus(int position) {
                Goods data = goodsAdapter.getData(position);
                data.setNum(data.getNum() + 1);

                refreshOrderList();
            }

            @Override
            public void minus(int position) {
                Goods data = goodsAdapter.getData(position);
                if (data.getNum() > 0) {
                    data.setNum(data.getNum() - 1);
                }
                refreshOrderList();
            }
        });
        //配置适配器
        recyclerView.setAdapter(goodsAdapter);
        //配置线性布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getData() {
        goodsAdapter.clearData();
        for (Goods good : shop.getGoods()) {
            goodsAdapter.insert(good);
        }
        goodsAdapter.notifyDataSetChanged();
    }


    private void refreshOrderList() {
        List<Goods> datas = goodsAdapter.getDatas();
        StringBuffer sb = new StringBuffer();
        double totalPrice = 0;
        for (Goods goods : datas) {
            if (goods.getNum() > 0) {
                totalPrice += goods.getPrice() * goods.getNum();
                sb.append(goods.getName() + "  X " + goods.getNum() + "  ");
            }
        }
        tv_list.setText(sb.toString());
        tv_total_price.setText(String.format("Total: $%.2f",totalPrice));
    }

    /**
     * 下单
     */
    private void order() {
        List<Goods> datas = goodsAdapter.getDatas();
        double totalPrice = 0;
        StringBuffer sb = new StringBuffer();
        for (Goods goods : datas) {
            if (goods.getNum() > 0) {
                totalPrice += goods.getPrice() * goods.getNum();
                sb.append(goods.getName() + "  X " + goods.getNum() + "  ");
            }
        }

        if (totalPrice <= 0) {
            ToastUtil.show("Please select at least one!");
        } else {
            Order order = new Order();
            order.setContent(sb.toString());
            order.setOrderTime(System.currentTimeMillis());
            order.setTotalPrice(totalPrice);
            order.setOrdreNumber(Utils.genOrderNum());
            order.setUserUUid(APP.sUser.getUuid());
            order.setTableNum(tableNum + "");
            order.setShopName(shop.getName());

            //再次确认弹窗
            new AlertDialog.Builder(this)
                    .setTitle("Order")
                    .setMessage(sb.toString())
                    .setNegativeButton("Cancel", (dialog, which) -> {
                    })
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        FirebaseUtil.getOrderRef()
                                .push()
                                .setValue(order)
                                .addOnSuccessListener(unused -> {
                                    ToastUtil.show("Order success");
                                    finish();
                                })
                                .addOnFailureListener(e -> ToastUtil.show("Order failure"));

                        ToastUtil.show("Order success!");
                        finish();
                    })
                    .show();
        }
    }

}
