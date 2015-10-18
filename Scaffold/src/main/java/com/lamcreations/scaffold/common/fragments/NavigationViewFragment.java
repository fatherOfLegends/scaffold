package com.lamcreations.scaffold.common.fragments;


import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.views.NavigationViewHeader;

public abstract class NavigationViewFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView mNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_view, container, false);
        container.setFitsSystemWindows(view.getFitsSystemWindows());
        mNavigationView = (NavigationView) view.findViewById(R.id.navigation_view);
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
