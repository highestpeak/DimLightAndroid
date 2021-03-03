package com.example.dimlight.drawer;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.dimlight.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author highestpeak
 * todo：完善首页的侧边滑出 Drawer，没有完全实现各种数据
 * https://github.com/mikepenz/MaterialDrawer/tree/v6.1.2
 * https://github.com/mikepenz/Android-Iconics/tree/v3.2.5
 */
public class MainDrawer {
    private Activity contextActivity;
    private Toolbar contextToolbar;

    private List<DrawerProfile> profiles;

    {
        /*
        todo: sample test data
            这个数据由本类自行获得
         */
        profiles = new ArrayList<>();
        profiles.add(new DrawerProfile("all", "all rss server", ""));
        profiles.add(new DrawerProfile("video", "video rss server", ""));
        profiles.add(new DrawerProfile("text", "text rss server", ""));
    }

    private AccountHeader accountHeader;

    public MainDrawer(Activity contextActivity, Toolbar contextToolbar) {
        this.contextActivity = contextActivity;
        this.contextToolbar = contextToolbar;
    }

    /**
     * 当前选中的账户名称
     *
     * @return DrawerProfile.name
     */
    public String activeProfileName() {
        return accountHeader.getActiveProfile().getName().getText(contextActivity);
    }


    public void initDrawer() {
        /*
        构造 accountHeader
        可以设置字体 withEmailTypeface
        accountHeader 中的 account 相当于指的是不同的订阅服务器 包括但不限于 huginn 的 rsshub 的 一般 rss 的
            account 只是服务器
         */
        AccountHeaderBuilder accountHeaderBuilder = new AccountHeaderBuilder()
                .withActivity(contextActivity)
                // 把账户名和图标挪到一行上
                .withCompactStyle(true)
                // 隐藏第二行 即电子邮件行
                // .withSelectionSecondLineShown(false)
                .withHeaderBackground(R.drawable.header)
                // 只显示一个 profile image
                .withOnlyMainProfileImageVisible(true);
        List<IProfile> profileDrawerItems = profiles.stream()
                .map(drawerProfile ->
                        new ProfileDrawerItem()
                                .withName(drawerProfile.name)
                                .withEmail(drawerProfile.desc)
                                .withIcon(drawerProfile.icon)
                ).collect(Collectors.toList());
        accountHeaderBuilder.withProfiles(profileDrawerItems);
        accountHeaderBuilder.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
            @Override
            public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                return false;
            }
        });
        accountHeader = accountHeaderBuilder.build();

        /*
        构造 左侧drawer
         */
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(contextActivity)
                .withToolbar(contextToolbar)
                .withAccountHeader(accountHeader)
                // 点击 item 时 抽屉不会关闭
                .withCloseOnClick(false);
        drawerBuilder.addDrawerItems(
                new PrimaryDrawerItem().withName("全部文章").withIcon(FontAwesome.Icon.faw_newspaper).withBadge("99+")
        );
        // 资料库应当可以按照标签查看持久化的内容
        // 资料库包括了 收藏夹/喜爱 inbox 搜索
        ExpandableBadgeDrawerItem feedDatabaseDrawerItem = new ExpandableBadgeDrawerItem()
                .withName("资料库")
                .withIcon(FontAwesome.Icon.faw_arrow_alt_circle_down)
                .withSubItems(
                        new PrimaryDrawerItem().withName("收藏夹").withIcon(FontAwesome.Icon.faw_star).withBadge("99+"),
                        new PrimaryDrawerItem().withName("inbox").withIcon(FontAwesome.Icon.faw_inbox).withBadge("99+"),
                        new PrimaryDrawerItem().withName("搜索").withIcon(FontAwesome.Icon.faw_search).withBadge("99+")
                );
        drawerBuilder.addDrawerItems(feedDatabaseDrawerItem);
        LeftSwipeButtonsDrawerItem contentModelsDrawerItem = new LeftSwipeButtonsDrawerItem();
        // 根据资源类型的模式划分
        List<String> modelStrs = Arrays.asList("混合", "文章", "视频", "小说/杂志", "播客", "论坛", "版本追踪","动态");
        contentModelsDrawerItem.addButtonText(modelStrs);
        drawerBuilder.addDrawerItems(
                new DividerDrawerItem(),
                // 切换内容模式 视频模式 文本模式 混合模式 论坛模式
                // 版本追踪模式（当有文章更改，在这个列表显示，这样的话我只能跟踪一部分的文章？）等
                contentModelsDrawerItem.withSetSelected(false)
        );
        // 主题 订阅源可以是主题，但是主题可以降低信息过载现象
        // 主题就是分组： 用户可以将一些订阅的频道弄成主题
        drawerBuilder.addDrawerItems(
                // name + 管理订阅源的图标
                // 该图标跳转到订阅源的增删查改页面
                // 许多这样的 ExpandableBadgeDrawerItem
                new ExpandableBadgeDrawerItem()
                        .withIcon(FontAwesome.Icon.faw_youtube)
                        .withName("youtube-channel")
                        .withBadge("19")
                        .withBadgeStyle(
                                new BadgeStyle()
                                        .withTextColor(Color.WHITE)
                                        .withColorRes(R.color.md_red_700))
                        // 订阅源下许多订阅项目
                        .withSubItems(
                                new PrimaryDrawerItem()
                                        .withName("IDEA-turtial")
                                        .withBadge("5")
                                        .withIcon(FontAwesome.Icon.faw_youtube)
                        )
        );
        drawerBuilder.addDrawerItems(
                new DividerDrawerItem(),
                // 包括算法\样式市场\主题-订阅市场
                new PrimaryDrawerItem().withName("开放市场").withIcon(FontAwesome.Icon.faw_store),
                new PrimaryDrawerItem().withName("开发者").withIcon(FontAwesome.Icon.faw_code),
                new PrimaryDrawerItem().withName("设置").withIcon(FontAwesome.Icon.faw_cogs)
        );
        drawerBuilder.addDrawerItems(
                // 切换APP显示模式-只显示几条常用的
                // 暂时定为这里只有主题切换
                new DividerDrawerItem(),
                new NightThemeDrawerItem()
        );

        Drawer drawer = drawerBuilder.build();

        /*
        构建 右侧drawer
         */
        // 视图模式、排序规则、对当前选定主题/feed列表的设置
        // 设置的过滤规则等（过滤指的是按照字符串或者特定逻辑的过滤）(过滤规则可以在服务器进行、也可以在本地进行)
        // 添加的标签等（标签是用户添上去的、或者根据生成规则添上去的,用户给每个文章添上标签）

        // 可以构建多个抽屉组件，例如左滑出现抽屉组件，右滑出现抽屉组件
        // new DrawerBuilder()
        //         .withActivity(this)
        //         .addDrawerItems(
        //                 new DividerDrawerItem(),
        //                 new SecondaryDrawerItem().withName("asdasdasdasd")
        //         )
        //         .withDrawerGravity(Gravity.END)
        //         .append(result);

        // result.addStickyFooterItem(new PrimaryDrawerItem().withName("StickyFooterItem"));
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class DrawerProfile {
        private String name;
        private String desc;

        /**
         * url
         */
        private String icon;
    }
}
