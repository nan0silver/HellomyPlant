package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class SaveInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_information);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        //navigation_add.setOnItemSelectedListener(this);
        navigation_add.setSelectedItemId(R.id.action_talk);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent SI_intent_camera = new Intent(SaveInformationActivity.this, searchPlant.class);
                        SI_intent_camera.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SI_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        Intent SI_intent_home = new Intent(SaveInformationActivity.this, MyplantListActivity.class);
                        SI_intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SI_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_ranking:
                        Intent SI_intent_ranking = new Intent(SaveInformationActivity.this, RankingListActivity.class);
                        SI_intent_ranking.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SI_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent SI_intent_talk = new Intent(SaveInformationActivity.this, NoticeBoardActivity.class);
                        SI_intent_talk.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SI_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }
}