package com.example.dimlight.drawer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.example.dimlight.R;
import com.google.android.flexbox.FlexboxLayout;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author highestpeak
 */
public class LeftSwipeButtonsDrawerItem extends AbstractDrawerItem<LeftSwipeButtonsDrawerItem, LeftSwipeButtonsDrawerItem.ButtonsDrawerViewHolder> {

    private Map<String, Button> buttons;

    {
        buttons = new HashMap<>();
    }

    private List<String> buttonNames;
    private boolean modified = false;

    public void addButtonText(List<String> buttonNames) {
        modified = true;
        this.buttonNames = buttonNames;
    }

    private String currCheckButtonName;
    private boolean isSwiped = false;
    public boolean getIsSwiped(){
        return isSwiped;
    }

    public LeftSwipeButtonsDrawerItem() {
        // 必须设置 selectable 否则事件不能传递到子组件
        withSelectable(false);
    }

    @Override
    public ButtonsDrawerViewHolder getViewHolder(View v) {
        return new ButtonsDrawerViewHolder(v);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.left_swipe_buttons_drawer_item;
    }

    @Override
    public void bindView(ButtonsDrawerViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        System.out.println("bindview in");
        Context context = holder.itemView.getContext();

        SwipeLayout swipeLayout = holder.swipeLayout;
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper));
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                isSwiped = true;
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                isSwiped = false;
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        if ((modified || buttons.size() == 0) && buttonNames.size() != 0) {
            buttons.clear();
            FlexboxLayout flexboxLayout = holder.flexboxLayout;

            // 下面几行注释不要删，一个暂时的参考依据
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            // params.setMargins(3, 0, 3, 0);
            int materialBlue = Color.parseColor("#1476D2");
            buttonNames.forEach(buttonName -> {
                Button materialButton = new Button(
                        new ContextThemeWrapper(context,R.style.Widget_MaterialComponents_Button_OutlinedButton),
                        null, R.style.Widget_MaterialComponents_Button_OutlinedButton
                );
                // Button materialButton = new Button(context);

                materialButton.setLayoutParams(params);
                materialButton.setText(buttonName);
                materialButton.setMinimumWidth(0);
                materialButton.setMinimumHeight(0);
                materialButton.setMinWidth(0);
                materialButton.setMinHeight(0);
                materialButton.setPadding(20, 20, 20, 20);
                materialButton.setOnClickListener(v -> {
                    if (isSwiped){
                        swipeLayout.close();
                        return;
                    }

                    Button prevButton = buttons.get(currCheckButtonName);
                    if (v.getId() == prevButton.getId()) {
                        currCheckButtonName = buttonName;
                        prevButton.setBackgroundColor(Color.WHITE);
                        prevButton.setTextColor(Color.BLACK);
                        materialButton.setBackgroundColor(materialBlue);
                        materialButton.setTextColor(Color.WHITE);
                    }
                });
                materialButton.setBackgroundColor(Color.WHITE);
                materialButton.setTextColor(Color.BLACK);
                buttons.put(buttonName, materialButton);
                flexboxLayout.addView(materialButton);
            });
            currCheckButtonName = buttonNames.get(0);
            Button currCheckButton = buttons.get(currCheckButtonName);
            currCheckButton.setBackgroundColor(materialBlue);
            currCheckButton.setTextColor(Color.WHITE);
            modified = false;

            flexboxLayout.setOnClickListener(v -> {
                if (isSwiped){
                    swipeLayout.close();
                }
            });
        }

        onPostBindView(this, holder.itemView);
    }

    public static class ButtonsDrawerViewHolder extends RecyclerView.ViewHolder {
        private FlexboxLayout flexboxLayout;
        private SwipeLayout swipeLayout;

        public ButtonsDrawerViewHolder(@NonNull View itemView) {
            super(itemView);
            flexboxLayout = itemView.findViewById(R.id.flex_box);
            swipeLayout = itemView.findViewById(R.id.swipe_box);
        }
    }
}
