package com.example.dimlight.drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dimlight.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;

public class NightThemeDrawerItem extends AbstractDrawerItem<NightThemeDrawerItem, NightThemeDrawerItem.NightThemeViewHolder> {

    @Override
    public NightThemeViewHolder getViewHolder(View v) {
        return new NightThemeViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public int getType() {
        return 1;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.night_theme_drawer_item;
    }

    public static class NightThemeViewHolder extends RecyclerView.ViewHolder{

        public NightThemeViewHolder(@NonNull View itemView) {
            super(itemView);

            Context context = itemView.getContext();

            ImageView dayImageView = itemView.findViewById(R.id.day_view_image);
            dayImageView.setImageDrawable(new IconicsDrawable(context)
                    .icon(FontAwesome.Icon.faw_sun)
                    .color(Color.parseColor("#767676"))
                    .sizeDp(36));

            ImageView nightImageView = itemView.findViewById(R.id.night_view_image);
            nightImageView.setImageDrawable(new IconicsDrawable(context)
                    .icon(FontAwesome.Icon.faw_moon)
                    .color(Color.parseColor("#767676"))
                    .sizeDp(36));

            ImageView autoImageView = itemView.findViewById(R.id.auto_view_image);
            autoImageView.setImageDrawable(new IconicsDrawable(context)
                    .icon(FontAwesome.Icon.faw_clock)
                    .color(Color.parseColor("#767676"))
                    .sizeDp(36));
        }
    }
}
