package org.orderapp.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Base ViewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    // 子View的集合
    private SparseArray<View> mViews;
    private Context context;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        mViews = new SparseArray<>();
    }

    public static BaseViewHolder getCommonViewHolder(Context context, View itemView) {
        return new BaseViewHolder(context, itemView);
    }

    /**
     * 提供给外部访问 View 的方法
     *
     * @param viewId id
     * @param <T>    泛型
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 隐藏控件
     * @param viewId
     */
    public void hideView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        view.setVisibility(View.GONE);
    }

    public void holdView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示控件
     * @param viewId
     */
    public void showView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 设置文本
     *
     * @param viewId id
     * @param text   文本
     * @return this
     */
    public BaseViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId id
     * @param resId  图片id
     * @return this
     */
    public BaseViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }
}
