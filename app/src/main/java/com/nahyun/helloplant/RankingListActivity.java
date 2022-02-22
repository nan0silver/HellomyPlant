package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class RankingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        //navigation_add.setSelectedItemId(R.id.action_ranking);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent RL_intent_camera = new Intent(RankingListActivity.this, searchPlant.class);
                        RL_intent_camera.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(RL_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        Intent RL_intent_home = new Intent(RankingListActivity.this, MyplantListActivity.class);
                        RL_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(RL_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent RL_intent_talk = new Intent(RankingListActivity.this, NoticeBoardActivity.class);
                        RL_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(RL_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }
}