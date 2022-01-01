package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class SearhedNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searhed_name);

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.action_camera);
        navigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent SN_intent_camera = new Intent(SearhedNameActivity.this, searchPlant.class);
                        startActivity(SN_intent_camera);
                        break;
                    case R.id.action_home:
                        Toast.makeText(SearhedNameActivity.this, "내 식물 리스트로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_ranking:
                        Toast.makeText(SearhedNameActivity.this, "랭킹 페이지로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_talk:
                        Toast.makeText(SearhedNameActivity.this, "게시판으로 이동", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
}