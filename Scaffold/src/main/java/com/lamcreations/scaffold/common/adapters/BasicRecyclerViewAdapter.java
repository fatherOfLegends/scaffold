package com.lamcreations.scaffold.common.adapters;
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

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public abstract class BasicRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    public static int NONE_SELECTED = -1;
    // Used to indicate selection
    // Only one item can be selected at a time.
    private int mSelectedItem = NONE_SELECTED;
    public static int NONE_ACTIVATED = -1;
    protected abstract List<T> getDataList();
    protected boolean mMoreItemsAvailable = false;

    // Used to indicate Activation
    // ActivationMode indicates how many items can be active at one time.
    private ActivationMode mActivationMode;
    private List<T> mActivatedItems;

    public BasicRecyclerViewAdapter(ActivationMode mode) {
        mActivationMode = mode;
        mActivatedItems = new ArrayList<T>();
    }

    public T getItemAtPosition(int position){
        if(getDataList().size() > position && position >= 0){
            return getDataList().get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return getDataList().size() + (canLoadMore() ? 1 : 0);
    }

    public int getActualItemCount() {
        return (getItemCount() - (canLoadMore() ? 1 : 0));
    }

    public boolean canLoadMore() {
        return mMoreItemsAvailable;
    }

    public void setMoreItemsAvailable(boolean moreItemsAvailable) {
        if (mMoreItemsAvailable != moreItemsAvailable) {
            mMoreItemsAvailable = moreItemsAvailable;
            if (moreItemsAvailable) {
                notifyItemInserted(getDataList().size());
            } else {
                notifyItemRemoved(getDataList().size());
            }
        }
    }

    public boolean isSelected(int position) {
        return mSelectedItem == position;
    }

    public ActivationMode getActivationMode() {
        return mActivationMode;
    }

    public void setActivationMode(ActivationMode mode) {
        mActivationMode = mode;
    }

    public boolean isActivated(int position) {
        return mActivatedItems.contains(getDataList().get(position));
    }

    public void setActivated(int pos, boolean activated) {
        if (mActivationMode == ActivationMode.NONE) {
            throw new IllegalStateException("Activation mode must be SINGLE or MULTIPLE");
        }

        if (activated) {
            if (mActivationMode != ActivationMode.MULTIPLE) {
                int oldPos = getActivatedItemPosition();
                mActivatedItems.clear();
                notifyItemChanged(oldPos);
            }
            mActivatedItems.add(getDataList().get(pos));
        } else {
            mActivatedItems.remove(getDataList().get(pos));
        }
        notifyItemChanged(pos);
    }

    public void toggleActivation(int pos) {
        if (mActivationMode == ActivationMode.NONE) {
            throw new IllegalStateException("Activation mode must be SINGLE or MULTIPLE");
        }
        if (isActivated(pos)) {
            mActivatedItems.remove(getDataList().get(pos));
        } else {
            if (mActivationMode != ActivationMode.MULTIPLE && mActivatedItems.size() > 0) {
                int oldPos = getActivatedItemPosition();
                mActivatedItems.clear();
                notifyItemChanged(oldPos);
            }
            mActivatedItems.add(getDataList().get(pos));
        }
        notifyItemChanged(pos);
    }

    public void clearActivations() {
        int[] positions = new int[mActivatedItems.size()];
        for (int i = 0; i < mActivatedItems.size(); i++) {
            positions[i] = getDataList().indexOf(mActivatedItems.get(i));
        }
        mActivatedItems.clear();
        for(int position : positions){
            notifyItemChanged(position);
        }
    }

    public int getActivatedItemCount() {
        return mActivatedItems.size();
    }

    public int getActivatedItemPosition() {
        if (mActivationMode != ActivationMode.SINGLE) {
            throw new IllegalStateException("Activation mode must be SINGLE");
        }
        if (mActivatedItems.size() == 1) {
            return getDataList().indexOf(mActivatedItems.get(0));
        }
        return NONE_ACTIVATED;
    }

    public int[] getActivatedItemPositions() {
        if (mActivationMode != ActivationMode.MULTIPLE) {
            throw new IllegalStateException("Activation mode must be MULTIPLE");
        }
        int[] activatedItemPositions = new int[mActivatedItems.size()];
        for(int i = 0; i< mActivatedItems.size(); i++){
            activatedItemPositions[i] = getDataList().indexOf(mActivatedItems.get(i));
        }
        return activatedItemPositions;
    }

    public void setSelected(int position) {
        if (mSelectedItem != NONE_SELECTED) {
            notifyItemChanged(mSelectedItem);
        }
        mSelectedItem = position;
        if (mSelectedItem != NONE_SELECTED) {
            notifyItemChanged(mSelectedItem);
        }
    }

    public int indexOf(T ad) {
        return getDataList().indexOf(ad);
    }

    public void clear(){
        getDataList().clear();
        mActivatedItems.clear();
        notifyDataSetChanged();
    }

    public enum ActivationMode {
        NONE,
        SINGLE,
        MULTIPLE
    }

    public interface ItemInteractionListener {

        /**
         * This will notify the listener that an Item has been clicked.
         *  @param position         This position represents the position in the list
         *
         */
        void onItemClick(int position);

        /**
         * This will notify the listener that an Item has been long pressed.
         *
         * @param position This position represents the position in the list
         */
        void onItemLongPressed(int position);

        /**
         * @param position This position represents the position in the list
         */
        void onActionModeToggleClick(int position);

        /**
         * @param position This position represents the position in the list
         * @param view This is the id of the view that was clicked on
         */
        void onGenericActionClick(int position, View view);


        class Simple implements ItemInteractionListener {

            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onItemLongPressed(int position) {

            }
            @Override
            public void onActionModeToggleClick(int position) {

            }
            @Override
            public void onGenericActionClick(int position, View view) {

            }
        }
    }
}

