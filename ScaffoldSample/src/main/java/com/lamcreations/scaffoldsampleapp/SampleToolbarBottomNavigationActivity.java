package com.lamcreations.scaffoldsampleapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import com.lamcreations.scaffold.common.activities.ToolbarBottomNavigationActivity;

public class SampleToolbarBottomNavigationActivity extends ToolbarBottomNavigationActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.scaffold_content, new EarthquakesFragment());
    }

    @Override
    protected int getBottomBarMenuResId() {
        return R.menu.scaffold_bottom_navigation_small;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onUpNavigation() {
        super.onUpNavigation();
        onBackPressed();
    }
}
