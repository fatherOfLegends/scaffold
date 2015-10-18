package com.lamcreations.scaffoldsampleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class SampleTabPagerAdapter extends FragmentStatePagerAdapter {

    public SampleTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new EarthquakesFragment();
        Bundle args = new Bundle();
        args.putInt(EarthquakesFragment.MAGNITUDE, (position + 5));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Magnitude " + (position + 5) + ".0";
    }
}
