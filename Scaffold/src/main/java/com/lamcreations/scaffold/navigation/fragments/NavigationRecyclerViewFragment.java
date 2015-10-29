package com.lamcreations.scaffold.navigation.fragments;
/*
 * Copyright (C) 2015 LAM Creations
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.adapters.BasicRecyclerViewAdapter;
import com.lamcreations.scaffold.common.adapters.itemDecorations.SpaceItemDecoration;
import com.lamcreations.scaffold.common.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationRecyclerViewFragment extends RecyclerViewFragment<NavigationRecyclerViewFragment.NavigationRecyclerViewAdapter> {

    private ViewGroup mRootView;
    private NavigationRecyclerViewAdapter mNavigationRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(R.layout.scaffold_navigation_recycler_view_fragment, container, false);
        container.setFitsSystemWindows(mRootView.getFitsSystemWindows());
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View navigationViewHeader = getNavigationViewHeader();
        if (navigationViewHeader != null) {
            mRootView.addView(navigationViewHeader, 0);
        }
    }

    @Override
    protected NavigationRecyclerViewAdapter getRecyclerViewAdapter() {
        if (mNavigationRecyclerViewAdapter == null) {
            Menu navigationMenu = new NavigationMenu(getContext());
            getActivity().getMenuInflater().inflate(getMenuResId(), navigationMenu);
            mNavigationRecyclerViewAdapter = new NavigationRecyclerViewAdapter(navigationMenu, getActionMode());
        }
        return mNavigationRecyclerViewAdapter;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new SpaceItemDecoration(0, LinearLayoutManager.VERTICAL);
    }

    @Override
    protected RecyclerView.ItemAnimator getItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected BasicRecyclerViewAdapter.ActivationMode getActionMode() {
        return BasicRecyclerViewAdapter.ActivationMode.NONE;
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    @MenuRes
    protected abstract int getMenuResId();

    protected abstract View getNavigationViewHeader();

    protected class NavigationRecyclerViewAdapter extends BasicRecyclerViewAdapter<NavigationItemViewHolder, NavigationMenuItem> {

        @NonNull
        private List<NavigationMenuItem> mNavigationMenuItems = new ArrayList<>();

        public NavigationRecyclerViewAdapter(Menu menu, ActivationMode mode) {
            super(mode);
            int size = menu.size();
            for (int i = 0; i < size; i++) {
                mNavigationMenuItems.add(new NavigationMenuItem(menu.getItem(i)));
            }
        }

        @Override
        protected List<NavigationMenuItem> getDataList() {
            return mNavigationMenuItems;
        }

        @Override
        public NavigationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            switch (viewType) {
                case NavigationMenuItem.HEADER:
                    view = inflater.inflate(R.layout.scaffold_navigation_item_header, parent, false);
                    break;
                case NavigationMenuItem.DIVIDER:
                    view = inflater.inflate(R.layout.scaffold_navigation_item_divider, parent, false);
                    break;
                case NavigationMenuItem.NAVIGABLE_ITEM:
                    view = inflater.inflate(R.layout.scaffold_navigation_item_navigable_item, parent, false);
                    break;
                default:
                    view = new View(getContext());
            }
            return new NavigationItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NavigationItemViewHolder holder, int position) {
            holder.bind(mNavigationMenuItems.get(position));
        }


        @Override
        public int getItemViewType(int position) {
            return mNavigationMenuItems.get(position).getType();
        }
    }

    protected class NavigationItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIconImageView;
        private TextView mTitleTextView;
        private TextView mBadgeTextView;

        public NavigationItemViewHolder(View itemView) {
            super(itemView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.navigation_item_icon);
            mTitleTextView = (TextView) itemView.findViewById(R.id.navigation_item_title);
            mBadgeTextView = (TextView) itemView.findViewById(R.id.navigation_item_badge);
        }

        public void bind(NavigationMenuItem item) {
            setIcon(item.getIcon());
            setTitle(item.getTitle());
            setBadgeCount(item.getBadgeCount());
            if (item.getType() == NavigationMenuItem.NAVIGABLE_ITEM) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationRecyclerViewFragment.this.onItemClick(getAdapterPosition());
                    }
                });
            } else {
                itemView.setOnClickListener(null);
            }
        }

        private void setIcon(Drawable icon) {
            if (mIconImageView != null) {
                mIconImageView.setImageDrawable(icon);
                mIconImageView.setVisibility(icon != null ? View.VISIBLE : View.GONE);
            }
        }

        private void setTitle(CharSequence title) {
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
                mTitleTextView.setVisibility(title != null ? View.VISIBLE : View.GONE);
            }
        }

        private void setBadgeCount(int badgeCount) {
            if (mBadgeTextView != null) {
                mBadgeTextView.setText(String.valueOf(badgeCount));
                mBadgeTextView.setVisibility(badgeCount > 0 ? View.VISIBLE : View.GONE);
            }
        }
    }

    protected class NavigationMenuItem implements MenuItem {

        public static final int HEADER = 0;
        public static final int DIVIDER = 1;
        public static final int NAVIGABLE_ITEM = 2;

        private MenuItem mMenuItem;
        private int mBadgeCount;

        public NavigationMenuItem(@NonNull MenuItem menuItem) {
            mMenuItem = menuItem;
        }

        @Override
        public int getItemId() {
            return mMenuItem.getItemId();
        }

        @Override
        public int getGroupId() {
            return mMenuItem.getGroupId();
        }

        @Override
        public int getOrder() {
            return mMenuItem.getOrder();
        }

        @Override
        public MenuItem setTitle(CharSequence title) {
            return mMenuItem.setTitle(title);
        }

        @Override
        public MenuItem setTitle(int title) {
            return mMenuItem.setTitle(title);
        }

        @Override
        public CharSequence getTitle() {
            return mMenuItem.getTitle();
        }

        @Override
        public MenuItem setTitleCondensed(CharSequence title) {
            return mMenuItem.setTitleCondensed(title);
        }

        @Override
        public CharSequence getTitleCondensed() {
            return mMenuItem.getTitleCondensed();
        }

        @Override
        public MenuItem setIcon(Drawable icon) {
            return mMenuItem.setIcon(icon);
        }

        @Override
        public MenuItem setIcon(int iconRes) {
            return mMenuItem.setIcon(iconRes);
        }

        @Override
        public Drawable getIcon() {
            return mMenuItem.getIcon();
        }

        @Override
        public MenuItem setIntent(Intent intent) {
            return mMenuItem.setIntent(intent);
        }

        @Override
        public Intent getIntent() {
            return mMenuItem.getIntent();
        }

        @Override
        public MenuItem setShortcut(char numericChar, char alphaChar) {
            return mMenuItem.setShortcut(numericChar, alphaChar);
        }

        @Override
        public MenuItem setNumericShortcut(char numericChar) {
            return mMenuItem.setNumericShortcut(numericChar);
        }

        @Override
        public char getNumericShortcut() {
            return mMenuItem.getNumericShortcut();
        }

        @Override
        public MenuItem setAlphabeticShortcut(char alphaChar) {
            return mMenuItem.setAlphabeticShortcut(alphaChar);
        }

        @Override
        public char getAlphabeticShortcut() {
            return mMenuItem.getAlphabeticShortcut();
        }

        @Override
        public MenuItem setCheckable(boolean checkable) {
            return mMenuItem.setCheckable(checkable);
        }

        @Override
        public boolean isCheckable() {
            return mMenuItem.isCheckable();
        }

        @Override
        public MenuItem setChecked(boolean checked) {
            return mMenuItem.setChecked(checked);
        }

        @Override
        public boolean isChecked() {
            return mMenuItem.isChecked();
        }

        @Override
        public MenuItem setVisible(boolean visible) {
            return mMenuItem.setVisible(visible);
        }

        @Override
        public boolean isVisible() {
            return mMenuItem.isVisible();
        }

        @Override
        public MenuItem setEnabled(boolean enabled) {
            return mMenuItem.setEnabled(enabled);
        }

        @Override
        public boolean isEnabled() {
            return mMenuItem.isEnabled();
        }

        @Override
        public boolean hasSubMenu() {
            return mMenuItem.hasSubMenu();
        }

        @Override
        public SubMenu getSubMenu() {
            return mMenuItem.getSubMenu();
        }

        @Override
        public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
            return mMenuItem.setOnMenuItemClickListener(menuItemClickListener);
        }

        @Override
        public ContextMenu.ContextMenuInfo getMenuInfo() {
            return mMenuItem.getMenuInfo();
        }

        @Override
        public void setShowAsAction(int actionEnum) {
            mMenuItem.setShowAsAction(actionEnum);
        }

        @Override
        public MenuItem setShowAsActionFlags(int actionEnum) {
            return mMenuItem.setShowAsActionFlags(actionEnum);
        }

        @Override
        public MenuItem setActionView(View view) {
            return mMenuItem.setActionView(view);
        }

        @Override
        public MenuItem setActionView(int resId) {
            return mMenuItem.setActionView(resId);
        }

        @Override
        public View getActionView() {
            return mMenuItem.getActionView();
        }

        @Override
        public MenuItem setActionProvider(ActionProvider actionProvider) {
            return mMenuItem.setActionProvider(actionProvider);
        }

        @Override
        public ActionProvider getActionProvider() {
            return mMenuItem.getActionProvider();
        }

        @Override
        public boolean expandActionView() {
            return mMenuItem.expandActionView();
        }

        @Override
        public boolean collapseActionView() {
            return mMenuItem.collapseActionView();
        }

        @Override
        public boolean isActionViewExpanded() {
            return mMenuItem.isActionViewExpanded();
        }

        @Override
        public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
            return mMenuItem.setOnActionExpandListener(listener);
        }

        public int getType() {
            if (getGroupId() == R.id.scaffold_navigation_item_header) {
                return HEADER;
            } else if (getGroupId() == R.id.scaffold_navigation_item_divider) {
                return DIVIDER;
            } else if (getGroupId() == R.id.scaffold_navigation_item_navigable_item) {
                return NAVIGABLE_ITEM;
            }
            return -1;
        }

        public int getBadgeCount() {
            return mBadgeCount;
        }

        public void setBadgeCount(int badgeCount) {
            mBadgeCount = badgeCount;
        }
    }
}
