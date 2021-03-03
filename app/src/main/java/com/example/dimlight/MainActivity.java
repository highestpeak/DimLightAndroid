package com.example.dimlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Space;

import com.daimajia.swipe.SwipeLayout;
import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.example.dimlight.drawer.MainDrawer;

import java.util.Objects;

/**
 * @author highestpeak
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private MainDrawer mainDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        initToolbar();

        mainDrawer = new MainDrawer(this,toolbar);
        mainDrawer.initDrawer();

        // https://github.com/donkingliang/ConsecutiveScroller
        ConsecutiveScrollerLayout feedItemContainer = findViewById(R.id.feed_item_container);

        // view
        View listViewItem = getLayoutInflater().inflate(R.layout.home_feeds_list_view_item,null);
        SwipeLayout swipeLayout = listViewItem.findViewById(R.id.swipe_box);
        ConsecutiveScrollerLayout.LayoutParams params = new ConsecutiveScrollerLayout.LayoutParams(
                ConsecutiveScrollerLayout.LayoutParams.MATCH_PARENT,
                ConsecutiveScrollerLayout.LayoutParams.WRAP_CONTENT
        );
        swipeLayout.setLayoutParams(params);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.left_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.right_wrapper));
        swipeLayout.getSurfaceView().setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedFullContentActivity.class);
            startActivity(intent);
        });

        // view
        ConstraintLayout cardViewItem = (ConstraintLayout) getLayoutInflater().inflate(R.layout.home_feeds_card_view_item,null);

        // view
        // https://github.com/CarGuo/GSYVideoPlayer 视频订阅
        ConstraintLayout twoColsLineWrapper = (ConstraintLayout) getLayoutInflater().inflate(R.layout.home_feeds_two_cols_line_item,null);
        twoColsLineWrapper.setLayoutParams(params);
        // ConstraintLayout.LayoutParams colInLineParams = new ConstraintLayout.LayoutParams(0,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        // int margin = 5;
        // colInLineParams.setMargins(margin,margin,margin,margin);
        // colInLineParams.horizontalWeight = 1;
        // ConstraintLayout videoCardViewItem1 = (ConstraintLayout) getLayoutInflater().inflate(R.layout.home_feeds_video_card_item,null);
        // ConstraintLayout videoCardViewItem2 = (ConstraintLayout) getLayoutInflater().inflate(R.layout.home_feeds_video_card_item,null);

        // view
        // ConstraintLayout videoInfoInlineViewItem = (ConstraintLayout) getLayoutInflater().inflate(R.layout.home_feeds_video_info_inline_item,null);

        ConsecutiveScrollerLayout.LayoutParams layoutParams = new ConsecutiveScrollerLayout.LayoutParams(0,20);
        Space space = new Space(this);
        space.setLayoutParams(layoutParams);
        feedItemContainer.addView(space);
        feedItemContainer.addView(swipeLayout);

        space = new Space(this);
        space.setLayoutParams(layoutParams);
        feedItemContainer.addView(space);
        feedItemContainer.addView(cardViewItem);

        space = new Space(this);
        space.setLayoutParams(layoutParams);
        feedItemContainer.addView(space);
        feedItemContainer.addView(twoColsLineWrapper);

        // space = new Space(this);
        // space.setLayoutParams(layoutParams);
        // feedItemContainer.addView(space);
        // feedItemContainer.addView(videoInfoInlineViewItem);

        // space = new Space(this);
        // space.setLayoutParams(layoutParams);
        // feedItemContainer.addView(space);
        // feedItemContainer.addView((ConstraintLayout) getLayoutInflater().inflate(R.layout.home_feeds_video_info_inline_item,null));

    }

    private void initToolbar(){

        @SuppressLint("NonConstantResourceId")
        final Toolbar.OnMenuItemClickListener toolbarMenuItemClickListener = item -> {
            switch (item.getItemId()){
                case R.id.home_toolbar_view_change:
                    break;
                case R.id.home_toolbar_search:
                    search();
                    break;
                default:
                    break;
            }
            return false;
        };

        toolbar = findViewById(R.id.home_feed_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setTint(Color.WHITE);
        toolbar.setTitle(R.string.home_page);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        toolbar.inflateMenu(R.menu.home_feed_toolbar_menu);
        toolbar.setOnMenuItemClickListener(toolbarMenuItemClickListener);
    }

    private void search(){

    }

    // /**
    //  *  设置每项可以选定为 webview
    //  */
    // private List<String> feedData;
    // private void initRecyclerView(){
    //     // init data
    //     feedData = new ArrayList<String>();
    //     for (int i = 0; i < 20; i++) {
    //         feedData.add("feedTestData-feedTestData-feedTestData-feedTestData-feedTestData-feedTestData-feedTestData-feedTestData-"+i);
    //     }
    //
    //     RecyclerView feedsView = (RecyclerView) findViewById(R.id.home_feed_recycler_view);
    //     // layoutmanager 处理布局，因此可以利用这个来处理图片、视频、文字等不同的内容
    //     RecyclerView.LayoutManager feedsLayoutManager = new LinearLayoutManager(this);
    //     feedsView.setLayoutManager(feedsLayoutManager);
    //     FeedsViewAdapter feedsViewAdapter = new FeedsViewAdapter(this,feedData);
    //     feedsViewAdapter.setMode(Attributes.Mode.Single);//设置只有一个拖拽打开的时候，其他的关闭
    //     feedsView.setAdapter(feedsViewAdapter);
    //     //添加分割线
    //     //设置添加删除动画
    //     //调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
    //     feedsView.setSelected(true);
    //     feedsViewAdapter.setOnItemClickLitener(new FeedsViewAdapter.OnItemClickLitener() {
    //         @Override
    //         public void onItemClick(View view, int position) {
    //             Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_SHORT).show();
    //         }
    //
    //         @Override
    //         public void onItemLongClick(View view, int position) {
    //             Toast.makeText(MainActivity.this,"长按",Toast.LENGTH_SHORT).show();
    //         }
    //
    //         @Override
    //         public void onTopClick(int position) {
    //             Toast.makeText(MainActivity.this,"置顶",Toast.LENGTH_SHORT).show();
    //         }
    //
    //         @Override
    //         public void onDeleteClick(int position) {
    //             Toast.makeText(MainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
    //             //Remove swiped item from list and notify the RecyclerView
    //             feedData.remove(position);
    //             feedsViewAdapter.notifyDataSetChanged();
    //         }
    //     });
    //
    // }


}