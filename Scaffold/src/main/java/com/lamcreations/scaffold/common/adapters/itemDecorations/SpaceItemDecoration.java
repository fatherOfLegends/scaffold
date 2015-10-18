package com.lamcreations.scaffold.common.adapters.itemDecorations;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    private int mOrientation;

    public SpaceItemDecoration(int space, int orientation) {
        mSpace = space;
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = mSpace;
            outRect.left = 0;

            if(parent.getChildAdapterPosition(view) == 0){
                outRect.top = mSpace;
            }
        } else {
            outRect.right = mSpace;
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.left = 0;

            if(parent.getChildAdapterPosition(view) == 0){
                outRect.left = mSpace;
            }
        }
    }
}