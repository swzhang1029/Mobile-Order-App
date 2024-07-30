package org.orderapp.activity;

import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.base.BaseActivity;
import org.orderapp.adapter.LineDecoration;
import org.orderapp.adapter.OrderListAdapter;
import org.orderapp.entity.Order;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.util.L;
import org.orderapp.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单列表
 */
public class OrderListActivity extends BaseActivity {
    /**
     * 订单列表UI
     */
    private RecyclerView recyclerView;
    /**
     * 订单列表适配器
     */
    private OrderListAdapter orderListAdapter;

    /**
     * 操作列表
     */
    private final String[] opArray = new String[]{"Delete"};

    private String[] score = {"1", "2", "3", "4", "5"};


    @Override
    protected int provideLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        setTitleText("Order List");
        showBack();

        initList();
        getData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化列表适配器
     */
    private void initList() {
        recyclerView = findViewById(R.id.rv_list);

        orderListAdapter = new OrderListAdapter();
        orderListAdapter.setListener(new OrderListAdapter.Listener() {
            @Override
            public void itemClick(int position) {

            }

            @Override
            public void itemLongClick(int position) {
                onItemClickDialog(position);
            }

            @Override
            public void comment(int position) {
                OrderListActivity.this.comment(position);
            }
        });
        //配置适配器
        recyclerView.setAdapter(orderListAdapter);
        //配置线性布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineDecoration(this, LineDecoration.HORIZONTAL_LIST));

    }

    private void getData() {
        FirebaseUtil.getOrderRef()
                .get()
                .addOnCompleteListener(task -> {
                    orderListAdapter.clearData();
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        Order order = child.getValue(Order.class);
                        order.setKey(child.getKey());
                        orderListAdapter.insert(order, 0);
                    }
                });
    }

    private void onItemClickDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(opArray, (dialog, which) -> {
            switch (which) {
                //删除
                case 0:
                    delete(position);
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

    private void delete(int position) {
        Order order = orderListAdapter.getData(position);
        //再次确认弹窗
        new AlertDialog.Builder(this)
                .setTitle(String.format("Delete [no:%s]?", order.getOrdreNumber()))
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setPositiveButton("Confirm", (dialog, which) -> {
                    FirebaseUtil.getOrderRef()
                            .child(order.getKey())
                            .removeValue()
                            .addOnSuccessListener(unused -> {
                                ToastUtil.show("Delete success");
                                orderListAdapter.remove(position);
                            })
                            .addOnFailureListener(e -> ToastUtil.show("Delete failure"));
                })
                .show();
    }

    /**
     * 评论
     *
     * @param position
     */
    private void comment(int position) {
        Order order = orderListAdapter.getData(position);

        new AlertDialog.Builder(this)
                .setTitle("Please rate")
                .setItems(score, (dialog, which) -> {
                    order.setComment(Integer.parseInt(score[which]));

                    Map<String, Object> map = new HashMap<>();
                    map.put("/" + order.getKey(), order.toMap());
                    FirebaseUtil.getOrderRef().updateChildren(map, (error, ref) -> {
                        if (error == null) {
                            ToastUtil.show("Comment success");
                            orderListAdapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.show("Comment failure");
                        }
                    });
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .show();
    }

}
