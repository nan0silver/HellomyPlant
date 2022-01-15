package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeBoardActivity extends AppCompatActivity {

    private ArrayList<NoticeBoardData> nb_arrayList;
    private NoticeBoardAdapter noticeBoardAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        //navigation_add.setOnItemSelectedListener(this);
        navigation_add.setSelectedItemId(R.id.action_talk);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent NB_intent_camera = new Intent(NoticeBoardActivity.this, searchPlant.class);
                        NB_intent_camera.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        Intent NB_intent_home = new Intent(NoticeBoardActivity.this, MyplantListActivity.class);
                        NB_intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_ranking:
                        Intent NB_intent_ranking = new Intent(NoticeBoardActivity.this, RankingListActivity.class);
                        NB_intent_ranking.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        break;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.noticeboard_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        nb_arrayList = new ArrayList<>();
        noticeBoardAdapter = new NoticeBoardAdapter(nb_arrayList);
        noticeBoardAdapter.setOnItemClickListener(new NoticeBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String p = Integer.toString(position);
                Toast.makeText(NoticeBoardActivity.this, p, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(noticeBoardAdapter);

        NoticeBoardData sample1 = null;

        Bitmap sample1_image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test_image);
        sample1 = new NoticeBoardData(sample1_image, "test");

        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);



    }
}