package com.lamcreations.scaffold.common.adapters.itemDecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    public GridSpaceItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mSpace;
        outRect.top = mSpace;
        outRect.bottom = mSpace;
        outRect.left = mSpace;
    }
}