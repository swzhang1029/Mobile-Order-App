package org.orderapp.adapter;


import android.view.View;

import org.orderapp.R;
import org.orderapp.adapter.base.BaseAdapter;
import org.orderapp.adapter.base.BaseViewHolder;
import org.orderapp.entity.Goods;
import org.orderapp.entity.Shop;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 商品适配器
 */
public class GoodsAdapter extends BaseAdapter<Goods> {
    public GoodsAdapter() {
    }

    @Override
    protected void onBindViewHolder(Goods goods, BaseViewHolder viewHolder, int type, int position) {
        //这里设置列表显示数据
        viewHolder.setText(R.id.tv_name, goods.getName());
        //随机销量
        viewHolder.setText(R.id.tv_sale, "sale: "+goods.getSale());
        //随机评分
        viewHolder.setText(R.id.tv_star, goods.getStar()+"");
        //价格
        viewHolder.setText(R.id.tv_price, goods.getPrice() + "");

        //图片显示名字的第一个字母
        viewHolder.setText(R.id.tv_pic, goods.getName().substring(0,1));

        viewHolder.hideView(R.id.tv_minus);
        viewHolder.hideView(R.id.tv_num);
        if (goods.getNum() > 0) {
            viewHolder.showView(R.id.tv_minus);
            viewHolder.showView(R.id.tv_num);
            viewHolder.setText(R.id.tv_num, goods.getNum() + "");
        }

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.itemClick(position);
            }
        });

        viewHolder.getView(R.id.tv_plus).setOnClickListener(v -> {
            if (listener != null) {
                listener.plus(position);
            }
            notifyItemChanged(position);
        });

        viewHolder.itemView.findViewById(R.id.tv_minus).setOnClickListener(v -> {
            if (listener != null) {
                listener.minus(position);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    protected int getLayoutId(int type) {
        return R.layout.rv_goods_item;
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void itemClick(int position);

        void plus(int position);

        void minus(int position);
    }

}
