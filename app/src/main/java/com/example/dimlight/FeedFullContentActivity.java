package com.example.dimlight;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.mikepenz.fastadapter.utils.AdapterUtil;

import java.util.ArrayList;

/**
 * @author highestpeak
 * todo:
 *  1. 可以左右滑动 切换不同 feed
 *  2. 向下滑动可以展示出底部的按钮组 包括但不限于 分享 标记未读 收藏 打标签
 *  3. 长按可以 mark 做笔记
 *  4. 最上方 toolbar 右侧可以显示该文所有笔记等
 *  5. 一些菜单的详细内容从底部弹出
 *  6. 快捷指令：到印象笔记、到某个我自己的服务器接口
 */
public class FeedFullContentActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private static final String TAG = "FeedFullContentActivity";

    private ObservableScrollView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_full_content);

        StringBuilder builder = new StringBuilder();
        for (int i=0;i<10;i++){
            builder.append("这是重复的字符串片段，这是用来测试内容的,可以左右滑动 切换不同 feed," +
                    "向下滑动可以展示出底部的按钮组 包括但不限于 分享 标记未读 收藏 打标签" +
                    "长按可以 mark 做笔记最上方 toolbar 右侧可以显示该文所有笔记等一些菜单的" +
                    "详细内容从底部弹出快捷指令：到印象笔记、到某个我自己的服务器接口\n");
        }
        // https://www.runoob.com/w3cnote/android-tutorial-textview.html
        // 这里说不定需要自定义 textview 以支持一些富文本
        // https://medium.com/androiddevelopers/custom-text-selection-actions-with-action-process-text-191f792d2999
        // https://www.jianshu.com/p/89970f098012
        // https://www.jianshu.com/p/eb2f436c7716
        // https://www.jianshu.com/p/5202b29cbf55
        // https://github.com/sendtion/XRichText
        // https://github.com/SufficientlySecure/html-textview
        // 需要一些富文本扩展
        TextView contentView = new TextView(this);
        contentView.setText(builder.toString());
        contentView.setTextIsSelectable(true);
        ActionMode.Callback2 textSelectionActionModeCallback;
        textSelectionActionModeCallback = new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.select_popup_menu,menu);
                return true;//返回false则不会显示弹窗
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                //根据item的ID处理点击事件
                switch (menuItem.getItemId()){
                    case R.id.Informal22:
                        Toast.makeText(FeedFullContentActivity.this, "点击的是22", Toast.LENGTH_SHORT).show();
                        actionMode.finish();//收起操作菜单
                        break;
                    case R.id.Informal33:
                        Toast.makeText(FeedFullContentActivity.this, "点击的是33", Toast.LENGTH_SHORT).show();
                        actionMode.finish();
                        break;
                    default:
                        break;
                }
                return false;//返回true则系统的"复制"、"搜索"之类的item将无效，只有自定义item有响应
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }

            @Override
            public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
                //可选  用于改变弹出菜单的位置
                super.onGetContentRect(mode, view, outRect);
            }
        };
        contentView.setCustomSelectionActionModeCallback(textSelectionActionModeCallback);
        // contentView.setTextIsSelectable(true);

        this.listView = (ObservableScrollView) findViewById(R.id.content_area);
        // ArrayList<String> items = new ArrayList<String>();
        // for (int i = 1; i <= 100; i++) {
        //     items.add("数据 " + i);
        // }
        // //为listview设置Adapter
        // listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
        listView.addView(contentView);
        // listView.setAdapter();
        //为listview设置滚动回调观察者
        //即使调用了listview.setScrollViewCallbacks(this)方法，listview还可以调用listview.setOnScrollListener()，它们会同时起作用
        //https://www.jianshu.com/p/f392d16bdf30
        listView.setScrollViewCallbacks(this);
    }

    /**
     * 滚动时
     * @param scrollY
     * @param firstScroll
     * @param dragging
     */
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Log.i("onScrollChanged","Y轴的坐标："+scrollY);
    }

    /**
     * 按下时
     */
    @Override
    public void onDownMotionEvent() {
        Log.i("onDownMotionEvent","按下时");
    }

    /**
     * 拖曳结束或者取消时
     * @param scrollState
     */
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if(scrollState==ScrollState.DOWN){
            Log.i("onUpOrCancelMotionEvent","向下滚动");
        }else if(scrollState==ScrollState.UP){
            Log.i("onUpOrCancelMotionEvent","向上滚动");
        }else {
            Log.i("onUpOrCancelMotionEvent","停止滚动");
        }
    }
}
