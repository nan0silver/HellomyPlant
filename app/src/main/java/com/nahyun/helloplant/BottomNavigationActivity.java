package com.nahyun.helloplant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getContentViewId());

            navigationView = findViewById(R.id.navigation);
            navigationView.setOnItemSelectedListener(this);
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
            } else if (itemId == R.id.action_home) {
                //startActivity(new Intent(this, NotificationsActivity.class));
                Toast.makeText(BottomNavigationActivity.this, "내 식물 리스트로 이동", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.action_ranking) {
                //startActivity(new Intent(this, MoreActivity.class));
                Toast.makeText(BottomNavigationActivity.this, "랭킹 페이지로 이동", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.action_talk) {
                //startActivity(new Intent(this, MoreActivity.class));
                Toast.makeText(BottomNavigationActivity.this, "게시판으로 이동", Toast.LENGTH_SHORT).show();
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
