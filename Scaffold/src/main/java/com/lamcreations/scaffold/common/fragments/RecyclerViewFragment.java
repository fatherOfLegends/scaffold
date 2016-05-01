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

package com.lamcreations.scaffold.common.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.adapters.BasicRecyclerViewAdapter;
import com.lamcreations.scaffold.common.adapters.itemDecorations.SpaceItemDecoration;


public abstract class RecyclerViewFragment<Adapter extends BasicRecyclerViewAdapter> extends BaseFragment
        implements BasicRecyclerViewAdapter.ItemInteractionListener,
        ActionMode.Callback, SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView mRecyclerView;
    protected ActionMode mActionMode;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected abstract Adapter getRecyclerViewAdapter();

    protected abstract RecyclerView.ItemAnimator getItemAnimator();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract BasicRecyclerViewAdapter.ActivationMode getActionMode();

    private static final String SCROLL_POSITION = "scrollPosition";
    private static final String SCROLL_OFFSET = "scrollOffset";

    private int mScrollPosition = 0;
    private int mScrollOffset = 0;

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            saveScrollPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mScrollPosition = savedInstanceState.getInt(SCROLL_POSITION);
            mScrollOffset = savedInstanceState.getInt(SCROLL_OFFSET);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scaffold_recycler_view_fragment, container, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView(view);
        assert mRecyclerView != null;

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.scaffold_swipe_refresh_layout);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(getSwipeRefreshColorScheme());
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setEnabled(isSwipeRefreshEnabled());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(SCROLL_POSITION, mScrollPosition);
        savedInstanceState.putInt(SCROLL_OFFSET, mScrollOffset);
    }

    @Override
    public void onItemLongPressed(int position) {
        if (getActionMode() != BasicRecyclerViewAdapter.ActivationMode.NONE) {
            if (position == -1) {
                return;
            }
            getRecyclerViewAdapter().toggleActivation(position);
            if (mActionMode == null) {
                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(this);
            } else {
                mActionMode.invalidate();
            }

            if (getRecyclerViewAdapter().getActivatedItemCount() <= 0 && mActionMode != null) {
                mActionMode.finish();
            }
        }
    }

    @Override
    public final void onItemClick(int position) {
        if (mActionMode == null) {
            if (position == -1) {
                return;
            }
            onClick(position);
        } else {
            onItemLongPressed(position);
        }
    }

    /**
     * By default we do not do anything when an item is clicked.
     * Subclasses should override this to receive and handle click events.
     *
     * @param position The position of the item in the adapter.
     */
    @SuppressWarnings("unused")
    protected void onClick(int position) {
    }

    @Override
    public void onActionModeToggleClick(int position) {
        onItemLongPressed(position);
    }

    @Override
    public void onGenericActionClick(int position, View view) {
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        // By default we do not have a menu to inflate, use subclass implementations.
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (actionMode != null) {
            actionMode.finish();
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.mActionMode = null;
        getRecyclerViewAdapter().clearActivations();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mActionMode != null) {
            mActionMode.finish();
        }

        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(mScrollListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreScrollPosition();

        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
    }

    private void saveScrollPosition() {
        if (mRecyclerView != null) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] firstItems = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(firstItems);
                mScrollPosition = firstItems[0];
            } else {
                mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
            View child = layoutManager.getChildAt(0);
            mScrollOffset = child != null ? child.getTop() : 0;
        }
    }

    private void restoreScrollPosition() {
        if (mRecyclerView != null) {
            if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager())
                        .scrollToPositionWithOffset(mScrollPosition, mScrollOffset);
            } else {
                ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .scrollToPositionWithOffset(mScrollPosition, mScrollOffset);
            }
        }
    }

    @CallSuper
    protected void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.scaffold_recycler_view);
        mRecyclerView.setItemAnimator(getItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(getRecyclerViewAdapter());
        mRecyclerView.addItemDecoration(getItemDecoration());
        restoreScrollPosition();
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.scaffold_default_item_spacing),
                LinearLayoutManager.VERTICAL);
    }

    protected int[] getSwipeRefreshColorScheme() {
        return new int[]{R.color.scaffold_primary};
    }

    protected abstract boolean isSwipeRefreshEnabled();

    @Override
    public void onRefresh() {
    }
}
