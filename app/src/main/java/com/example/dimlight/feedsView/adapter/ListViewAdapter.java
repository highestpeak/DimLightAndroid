package com.example.dimlight.feedsView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.dimlight.R;

/**
 * @author highestpeak
 * 列表视图
 * 每项只有简单的文字内容，标题和简单的描述等
 * 同时可以左右滑动进行快捷操作
 */
public class ListViewAdapter extends RecyclerSwipeAdapter<ListViewAdapter.ListViewHolder> {
    private Context myContext;

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.home_feeds_list_view_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int positon) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    @LayoutRes
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
