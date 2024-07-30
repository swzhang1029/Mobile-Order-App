package org.orderapp.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器基类
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<T> mList;


    // 单类型
    public BaseAdapter() {
        this.mList = new ArrayList<>();
    }

    public void setData(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    //绑定数据
    protected abstract void onBindViewHolder(T model, BaseViewHolder viewHolder, int type, int position);

    protected abstract int getLayoutId(int type);


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return BaseViewHolder.getCommonViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBindViewHolder(mList.get(position), holder, getItemViewType(position), position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public T getData(int position) {
        if (mList != null && mList.size() > 0) {
            return mList.get(position);
        }
        return null;
    }

    public List<T> getDatas() {
        return mList;
    }

    public void insert(T item) {
        insert(item, -1);
    }

    public void insertAll(List<T> item) {
        mList.addAll(item);
        notifyDataSetChanged();
    }

    /**
     * 插入一项数据
     *
     * @param item
     * @param position
     */
    public void insert(T item, int position) {
        if (position == -1) {
            mList.add(item);
            notifyDataSetChanged();
        } else {
            mList.add(position, item);
            notifyItemInserted(position);
        }
    }

    // 删除一项数据
    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

}

