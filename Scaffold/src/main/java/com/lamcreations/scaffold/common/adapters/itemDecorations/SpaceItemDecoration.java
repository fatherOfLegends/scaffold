package com.lamcreations.scaffold.common.adapters.itemDecorations;
/*
 * Copyright 2015 LAM Creations
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