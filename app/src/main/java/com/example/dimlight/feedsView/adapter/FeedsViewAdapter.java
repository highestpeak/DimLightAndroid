package com.example.dimlight.feedsView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.dimlight.R;

import java.util.List;

/**
 * todo: help page: https://www.cnblogs.com/whycxb/p/9323739.html
 *  https://github.com/daimajia/AndroidSwipeLayout
 *  https://juejin.cn/post/6844903862965387277
 *  https://www.jianshu.com/p/9cf13dc21b82
 *  https://github.com/daimajia/AndroidSwipeLayout/blob/master/demo/src/main/res/layout/sample1.xml
 *  https://github.com/daimajia/AndroidSwipeLayout/blob/master/demo/src/main/java/com/daimajia/swipedemo/MyActivity.java
 */
public class FeedsViewAdapter extends RecyclerSwipeAdapter<FeedsViewAdapter.FeedsViewHolder> {
    /**上下文*/
    private Context myContext;

    private List<String> feedsData;

    public FeedsViewAdapter(Context context, List<String> feedsData){
        this.myContext = context;
        this.feedsData = feedsData;
    }

    @Override
    public FeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.home_feed_item, parent, false);
        return new FeedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedsViewHolder feedsViewHolder, int position) {
        feedsViewHolder.textView.setText(feedsData.get(position));

        //设置侧滑显示模式
        feedsViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //设置侧滑显示位置方向
        //feedsViewHolder.parentLayout.addDrag(SwipeLayout.DragEdge.Right,feedsViewHolder.bottom_wrapper);

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            feedsViewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedsViewHolder.swipeLayout.close();//隐藏侧滑菜单区域
                    int position = feedsViewHolder.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
                    mOnItemClickLitener.onItemClick(feedsViewHolder.swipeLayout, position);
                }
            });
            //长按事件
            feedsViewHolder.swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    feedsViewHolder.swipeLayout.close();//隐藏侧滑菜单区域
                    int position = feedsViewHolder.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
                    mOnItemClickLitener.onItemLongClick(feedsViewHolder.swipeLayout, position);
                    return false;
                }
            });

            //置顶
            // feedsViewHolder.mTop.setOnClickListener(new View.OnClickListener() {
            //     @Override
            //     public void onClick(View view) {
            //         feedsViewHolder.swipeLayout.close();//隐藏侧滑菜单区域
            //         int position = feedsViewHolder.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
            //         mOnItemClickLitener.onTopClick(position);
            //     }
            // });

            //删除
            feedsViewHolder.delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedsViewHolder.swipeLayout.close();//隐藏侧滑菜单区域
                    int position = feedsViewHolder.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
                    mOnItemClickLitener.onDeleteClick(position);
                }
            });
        }
        
        mItemManger.bindView(feedsViewHolder.itemView, position);//实现只展现一条列表项的侧滑区域
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.home_feeds_swipeLayout; //实现只展现一条列表项的侧滑区域
    }

    /**
     * 添加Item--用于动画的展现*/
    public void addItem(int position,String listitemBean) {
        feedsData.add(position,listitemBean);
        notifyItemInserted(position);
    }

    /**
     * 删除Item--用于动画的展现*/
    public void removeItem(int position) {
        feedsData.remove(position);
        notifyItemRemoved(position);
    }

    // @NonNull
    // @Override
    // public FeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //     // create a new view
    //     View v = LayoutInflater.from(parent.getContext())
    //             .inflate(R.layout.home_feed_item, parent, false);
    //     return new FeedsViewHolder(v);
    // }
    //
    // @Override
    // public void onBindViewHolder(@NonNull FeedsViewHolder holder, int position) {
    //     holder.textView.setText(feedsData.get(position));
    // }
    //
    @Override
    public int getItemCount() {
        return feedsData.size();
    }

    public static class FeedsViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;

        public TextView textView;
        public TextView delButton;

        public FeedsViewHolder(View v) {
            super(v);
            swipeLayout = (SwipeLayout) v.findViewById(R.id.home_feeds_swipeLayout);

            textView = (TextView) v.findViewById(R.id.feed_item_test_text);
            delButton = (TextView) v.findViewById(R.id.feed_item_test_del);
        }
    }

    /*=====================添加OnItemClickListener回调================================*/

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
        /**置顶*/
        void onTopClick(int position);
        /**删除*/
        void onDeleteClick(int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
