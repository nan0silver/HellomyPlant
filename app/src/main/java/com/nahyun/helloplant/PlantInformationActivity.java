package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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

        String plant_name = "";
        JSONObject plantDetailData = new JSONObject();
//        String jsonString =

//                intent_comefrom_searchplant_page.getExtras().getString("plantDetailData");
//        try {
//            plantDetailData = new JSONObject(jsonString);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            plant_name_TextView.setText((String)plantDetailData.get("name"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        plant_information_RecyclerView = (RecyclerView)findViewById(R.id.plant_information_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        plant_information_RecyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        plantInformationAdapter = new PlantInformationAdapter(arrayList);
        plant_information_RecyclerView.setAdapter(plantInformationAdapter);

        PlantInformationData familyname = null;
        familyname = new PlantInformationData("식물 과명", "뽕나무과");
        //        PlantInformationData height = new PlantInformationData("성장 높이", "높이");
        PlantInformationData place = new PlantInformationData("배치 장소", "거실 창측 (실내깊이 150~300cm),발코니 내측 (실내깊이 50~150cm),발코니 창측 (실내깊이 0~50cm)");
        PlantInformationData smell = new PlantInformationData("식물 냄새", "거의 없음");
        PlantInformationData speed = new PlantInformationData("생장 속도", "빠름");
        PlantInformationData temperature = new PlantInformationData("최저 온도", "21~25℃");
        PlantInformationData pest = new PlantInformationData(" 병해충 ", "응애,깍지벌레");
        arrayList.add(familyname);
//        arrayList.add(height);
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
