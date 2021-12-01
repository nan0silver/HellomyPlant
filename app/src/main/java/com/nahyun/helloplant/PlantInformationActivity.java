package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlantInformationActivity extends AppCompatActivity {

    private ArrayList<PlantInformationData> arrayList;
    private PlantInformationAdapter plantInformationAdapter;
    private RecyclerView plant_information_RecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        TextView plant_name_TextView = (TextView)findViewById(R.id.searching_plant_name);
        Intent intent_comefrom_searchplant_page = getIntent();

        String plant_name = intent_comefrom_searchplant_page.getExtras().getString("test");
        plant_name_TextView.setText(plant_name);


        plant_information_RecyclerView = (RecyclerView)findViewById(R.id.plant_information_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        plant_information_RecyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        plantInformationAdapter = new PlantInformationAdapter(arrayList);
        plant_information_RecyclerView.setAdapter(plantInformationAdapter);


        PlantInformationData familyname = new PlantInformationData("식물 과명", "과명");
        PlantInformationData height = new PlantInformationData("성장 높이", "높이");
        PlantInformationData place = new PlantInformationData("배치 장소", "장소");
        PlantInformationData smell = new PlantInformationData("식물 냄새", "냄새");
        PlantInformationData speed = new PlantInformationData("생장 속도", "속도");
        PlantInformationData temperature = new PlantInformationData("최저 온도", "온도");
        PlantInformationData pest = new PlantInformationData(" 병해충 ", "병해충");
        arrayList.add(familyname);
        arrayList.add(height);
        arrayList.add(place);
        arrayList.add(smell);
        arrayList.add(speed);
        arrayList.add(temperature);
        arrayList.add(pest);

        //plantInformationAdapter.notifyDataSetChanged();


        findViewById(R.id.addmyplantButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_addmyplant_page = new Intent(PlantInformationActivity.this, AddMyplantActivity.class);
                startActivity(intent_goto_addmyplant_page);
            }
        });

    }
}