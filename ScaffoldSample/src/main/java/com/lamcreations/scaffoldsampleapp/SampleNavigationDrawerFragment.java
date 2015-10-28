package com.lamcreations.scaffoldsampleapp;

import com.lamcreations.scaffold.common.fragments.NavigationRecyclerViewFragment;
import com.lamcreations.scaffold.common.views.NavigationViewHeader;


public class SampleNavigationDrawerFragment extends NavigationRecyclerViewFragment {

    @Override
    protected void onClick(int position) {
        super.onClick(position);
        //TODO
    }

    @Override
    protected int getMenuResId() {
        return R.menu.scaffold_navigation_recycler_view;
    }

    @Override
    protected NavigationViewHeader getNavigationViewHeader() {
        NavigationViewHeader navigationViewHeader = new NavigationViewHeader(getContext());
        navigationViewHeader.addUserAccount(new SampleUserAccount());
        return navigationViewHeader;
    }
}
