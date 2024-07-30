package org.orderapp.adapter;


import org.orderapp.R;
import org.orderapp.adapter.base.BaseAdapter;
import org.orderapp.adapter.base.BaseViewHolder;
import org.orderapp.entity.Order;

import java.text.SimpleDateFormat;

/**
 * 订单列表适配器
 */
public class OrderListAdapter extends BaseAdapter<Order> {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public OrderListAdapter() {
    }

    @Override
    protected void onBindViewHolder(Order order, BaseViewHolder viewHolder, int type, int position) {
        //这里设置列表显示数据
        viewHolder.setText(R.id.tv_title, order.getShopName());
        viewHolder.setText(R.id.tv_content, order.getContent());
        viewHolder.setText(R.id.tv_no, String.format("no: %s", order.getOrdreNumber()));
        viewHolder.setText(R.id.tv_price, String.format("$ %.2f", order.getTotalPrice()));
        viewHolder.setText(R.id.tv_time, sdf.format(order.getOrderTime()));

        //评论
        if (order.getComment() <= 0) {
            viewHolder.showView(R.id.tv_comment);
        } else {
            viewHolder.holdView(R.id.tv_comment);
        }
        viewHolder.getView(R.id.tv_comment).setOnClickListener(v -> {
            if (listener != null) {
                listener.comment(position);
            }
        });

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.itemClick(position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.itemLongClick(position);
                return true;
            }
            return false;
        });
    }

    @Override
    protected int getLayoutId(int type) {
        return R.layout.rv_order_list_item;
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void itemClick(int position);

        void itemLongClick(int position);

        void comment(int position);
    }

}
