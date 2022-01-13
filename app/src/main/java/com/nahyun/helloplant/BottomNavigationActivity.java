package com.nahyun.helloplant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getContentViewId());

            navigationView = findViewById(R.id.navigation);
            navigationView.setOnNavigationItemSelectedListener(this);
            navigationView.bringToFront();
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_camera) {
                startActivity(new Intent(BottomNavigationActivity.this, searchPlant.class));
                overridePendingTransition(0,0);
            } else if (itemId == R.id.action_home) {
                startActivity(new Intent(BottomNavigationActivity.this, MyplantListActivity.class));
                overridePendingTransition(0,0);
            } else if (itemId == R.id.action_ranking) {
                startActivity(new Intent(BottomNavigationActivity.this, RankingListActivity.class));
                overridePendingTransition(0,0);
            } else if (itemId == R.id.action_talk) {
                startActivity(new Intent(BottomNavigationActivity.this, NoticeBoardActivity.class));
                overridePendingTransition(0,0);
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();
}
