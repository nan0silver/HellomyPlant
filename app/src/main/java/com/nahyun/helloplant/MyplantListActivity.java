package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyplantListActivity extends BottomNavigationActivity {

    private ArrayList<MyplantListData> mp_arrayList;
    private MyplantListAdapter myplantListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplant_list);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setSelectedItemId(R.id.action_home);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent ML_intent_camera = new Intent(MyplantListActivity.this, searchPlant.class);
                        ML_intent_camera.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        break;
                    case R.id.action_ranking:
                        Intent ML_intent_ranking = new Intent(MyplantListActivity.this, RankingListActivity.class);
                        ML_intent_ranking.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent ML_intent_talk = new Intent(MyplantListActivity.this, NoticeBoardActivity.class);
                        ML_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.myplant_list_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mp_arrayList = new ArrayList<>();

        myplantListAdapter = new MyplantListAdapter(mp_arrayList);
        recyclerView.setAdapter(myplantListAdapter);

        MyplantListData sample1 = null;

        Bitmap sample1_image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test_image);
        sample1 = new MyplantListData(sample1_image, "test", null, null);

        mp_arrayList.add(sample1);
        mp_arrayList.add(sample1);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_myplant_list;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_home;
    }
}