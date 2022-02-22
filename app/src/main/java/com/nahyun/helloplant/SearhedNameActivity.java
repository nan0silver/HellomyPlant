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
                        SN_intent_camera.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SN_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        Intent SN_intent_home = new Intent(SearhedNameActivity.this, MyplantListActivity.class);
                        SN_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SN_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent SN_intent_talk = new Intent(SearhedNameActivity.this, NoticeBoardActivity.class);
                        SN_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SN_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }
}