package org.orderapp.adapter;


import org.orderapp.R;
import org.orderapp.adapter.base.BaseAdapter;
import org.orderapp.adapter.base.BaseViewHolder;
import org.orderapp.entity.Shop;

/**
 * 店铺适配器
 */
public class ShopAdapter extends BaseAdapter<Shop> {
    public ShopAdapter() {
    }

    @Override
    protected void onBindViewHolder(Shop shop, BaseViewHolder viewHolder, int type, int position) {
        //这里设置列表显示数据
        viewHolder.setText(R.id.tv_title, shop.getName());
        //随机销量
        viewHolder.setText(R.id.tv_sale, "sale: " + shop.getSale());
        //随机评分
        viewHolder.setText(R.id.tv_star, shop.getStar() + "");
        //距离
        if(shop.getDistance()==0) {
            viewHolder.holdView(R.id.tv_distance);
        }else{
            viewHolder.showView(R.id.tv_distance);
            viewHolder.setText(R.id.tv_distance, String.format("%.2fkm", shop.getDistance()));
        }

        //图片显示名字的第一个字母
        viewHolder.setText(R.id.tv_pic, shop.getName().substring(0,1));

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.itemClick(position);
            }
        });

        viewHolder.getView(R.id.tv_navigation).setOnClickListener(v -> {
            if (listener != null) {
                listener.navigation(position);
            }
        });
    }

    @Override
    protected int getLayoutId(int type) {
        return R.layout.rv_shop_item;
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void itemClick(int position);

        void navigation(int position);
    }

}
