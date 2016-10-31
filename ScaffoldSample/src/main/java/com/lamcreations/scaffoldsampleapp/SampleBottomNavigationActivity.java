package com.lamcreations.scaffoldsampleapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import com.lamcreations.scaffold.common.activities.BottomNavigationActivity;

public class SampleBottomNavigationActivity extends BottomNavigationActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.scaffold_content, new EarthquakesFragment());
    }

    @Override
    protected int getBottomBarMenuResId() {
        return R.menu.scaffold_bottom_navigation_large;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
