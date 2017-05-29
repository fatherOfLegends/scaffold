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

package com.lamcreations.scaffold.navigation.fragments;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.users.views.NavigationViewHeader;

@SuppressWarnings("unused")
public abstract class NavigationViewFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView mNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scaffold_navigation_view_fragment, container, false);
        container.setFitsSystemWindows(view.getFitsSystemWindows());
        mNavigationView = (NavigationView) view.findViewById(R.id.scaffold_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.inflateMenu(getMenuResId());
        NavigationViewHeader header = getNavigationViewHeader();
        if (header != null) {
            mNavigationView.addHeaderView(header);
        }
        return view;
    }

    @MenuRes
    protected abstract int getMenuResId();

    protected abstract NavigationViewHeader getNavigationViewHeader();
}
