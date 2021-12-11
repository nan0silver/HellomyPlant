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
        String jsonString =
                intent_comefrom_searchplant_page.getExtras().getString("plantDetailData");
        try {
            plantDetailData = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            plant_name_TextView.setText((String)plantDetailData.get("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        plant_information_RecyclerView = (RecyclerView)findViewById(R.id.plant_information_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        plant_information_RecyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        plantInformationAdapter = new PlantInformationAdapter(arrayList);
        plant_information_RecyclerView.setAdapter(plantInformationAdapter);

        PlantInformationData familyname = null;
        PlantInformationData height = null;
        PlantInformationData place = null;
        PlantInformationData smell = null;
        PlantInformationData speed = null;
        PlantInformationData temperature = null;
        PlantInformationData pest = null;
        try {
            familyname = new PlantInformationData("식물 과명", (String)plantDetailData.get("familyName"));
         height = new PlantInformationData("성장 높이", (String)plantDetailData.get("height"));
         place = new PlantInformationData("배치 장소", ((String)plantDetailData.get("place")).replace(",","\n"));
         smell = new PlantInformationData("식물 냄새", (String)plantDetailData.get("smell"));
         speed = new PlantInformationData("생장 속도", (String)plantDetailData.get("growthSpeed"));
         temperature = new PlantInformationData("적정 온도", (String)plantDetailData.get("familyName"));
         pest = new PlantInformationData(" 병해충 ", (String)plantDetailData.get("pest"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
