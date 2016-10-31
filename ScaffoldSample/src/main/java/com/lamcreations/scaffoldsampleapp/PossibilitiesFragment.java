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

package com.lamcreations.scaffoldsampleapp;

import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lamcreations.scaffold.common.adapters.BasicRecyclerViewAdapter;
import com.lamcreations.scaffold.common.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class PossibilitiesFragment extends RecyclerViewFragment<PossibilitiesFragment.PossibilitiesRecyclerViewAdapter> {

    private PossibilitiesRecyclerViewAdapter mPossibilitiesRecyclerViewAdapter;

    @Override
    protected PossibilitiesRecyclerViewAdapter getRecyclerViewAdapter() {
        if (mPossibilitiesRecyclerViewAdapter == null) {
            mPossibilitiesRecyclerViewAdapter =
                    new PossibilitiesRecyclerViewAdapter(R.menu.possibilities, getActionMode());
        }
        return mPossibilitiesRecyclerViewAdapter;
    }

    @Override
    protected RecyclerView.ItemAnimator getItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected BasicRecyclerViewAdapter.ActivationMode getActionMode() {
        return BasicRecyclerViewAdapter.ActivationMode.NONE;
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    @Override
    protected void onClick(int position) {
        MenuItem item = getRecyclerViewAdapter().getItemAtPosition(position);
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.toolbar_activity:
                intent.setClass(getActivity(), SampleToolbarActivity.class);
                break;
            case R.id.coordinator_activity:
                intent.setClass(getActivity(), SampleCoordinatorActivity.class);
                break;
            case R.id.tab_activity:
                intent.setClass(getActivity(), SampleTabActivity.class);
                break;
            case R.id.drawer_activity:
                intent.setClass(getActivity(), SampleDrawerActivity.class);
                break;
            case R.id.tab_drawer_activity:
                intent.setClass(getActivity(), SampleTabDrawerActivity.class);
                break;
            case R.id.collapsing_toolbar_activity:
                intent.setClass(getActivity(), SampleCollapsingToolbarActivity.class);
                break;
            case R.id.collapsing_toolbar_drawer_activity:
                intent.setClass(getActivity(), SampleCollapsingToolbarDrawerActivity.class);
                break;
            case R.id.collapsing_toolbar_tab_drawer_activity:
                intent.setClass(getActivity(), SampleCollapsingToolbarTabDrawerActivity.class);
                break;
            case R.id.searchable_activity:
                intent.setClass(getActivity(), SampleSearchableActivity.class);
                break;
            case R.id.settings_activity:
                intent.setClass(getActivity(), SampleSettingsActivity.class);
                break;
            case R.id.settings_activity_headers:
                intent.setClass(getActivity(), SampleSettingsActivityWithHeaders.class);
                break;
            case R.id.toolbar_bottom_navigation_activity:
                intent.setClass(getActivity(), SampleToolbarBottomNavigationActivity.class);
                break;
            case R.id.bottom_navigation_activity:
                intent.setClass(getActivity(), SampleBottomNavigationActivity.class);
                break;
        }
        startActivity(intent);
    }

    public class PossibilitiesRecyclerViewAdapter extends BasicRecyclerViewAdapter<PossibilitiesViewHolder, MenuItem> {

        private List<MenuItem> mPossibilities = new ArrayList<>();

        PossibilitiesRecyclerViewAdapter(int menuResId, ActivationMode mode) {
            super(mode);
            Menu menu = new MenuBuilder(getActivity());
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(menuResId, menu);
            int size = menu.size();
            for (int i = 0; i < size; ++i) {
                mPossibilities.add(menu.getItem(i));
            }
        }

        @Override
        protected List<MenuItem> getDataList() {
            return mPossibilities;
        }

        @Override
        public PossibilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PossibilitiesViewHolder(
                    PossibilitiesFragment.this,
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.possibility_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PossibilitiesViewHolder holder, int position) {
            holder.bind(getDataList().get(position));
        }
    }

    public class PossibilitiesViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;

        PossibilitiesViewHolder(
                final BasicRecyclerViewAdapter.ItemInteractionListener itemInteractionListener,
                View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemInteractionListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemInteractionListener.onItemLongPressed(getAdapterPosition());
                    return true;
                }
            });
        }

        void bind(MenuItem item) {
            mTitleTextView.setText(item.getTitle());
        }
    }
}
