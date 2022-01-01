package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class PlantInformationActivity extends BottomNavigationActivity {

    private ArrayList<PlantInformationData> arrayList;
    private PlantInformationAdapter plantInformationAdapter;
    private RecyclerView plant_information_RecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.action_camera);
        navigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Toast.makeText(PlantInformationActivity.this, "내 식물 리스트로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_ranking:
                        Toast.makeText(PlantInformationActivity.this, "랭킹 페이지로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_talk:
                        Toast.makeText(PlantInformationActivity.this, "게시판으로 이동", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        TextView plant_name_TextView = (TextView)findViewById(R.id.searching_plant_name);
        Intent intent_comefrom_searchplant_page = getIntent();

        byte[] byteArray_imageBitmap = getIntent().getByteArrayExtra("image_bitmap");
        Bitmap get_image;
        get_image = BitmapFactory.decodeByteArray(byteArray_imageBitmap, 0, byteArray_imageBitmap.length);
        ImageView plant_ImageView = (ImageView)findViewById(R.id.searching_plant_ImageView);
        plant_ImageView.setImageBitmap(get_image);

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
        PlantInformationData waterCycle = null;
        PlantInformationData waterdrop = null;
        PlantInformationData light = null;

        String wateringInfomation = "";
        try {
            wateringInfomation = (String)plantDetailData.get("watercycleWinter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int wateringCycle = 0;
        String wateringdrop = "";

        System.out.println(wateringInfomation);

        if(wateringInfomation.charAt(0)=='항'){
            wateringCycle = 1;
            wateringdrop = "흙이 흠뻑 젖도록";
        } else if(wateringInfomation.charAt(0)=='흙'){
            wateringCycle = 7;
            wateringdrop = "흙이 촉촉하게";
        } else if(wateringInfomation.charAt(0)=='토'){
            wateringCycle = 14;
            wateringdrop = "흙이 적당히 젖도록";
        }else{
            wateringCycle = 30;
            wateringdrop = "흙이 적당히 젖도록";
        }
        String water = Integer.toString(wateringCycle) + " 일";
        try {
            familyname = new PlantInformationData("식물 과명", (String)plantDetailData.get("familyName"));
            waterCycle = new PlantInformationData(" 물 주기 ", water);
            waterdrop = new PlantInformationData(" 물의 양 ", wateringdrop);
            light = new PlantInformationData("햇빛의 양", (String)plantDetailData.get("light"));
         height = new PlantInformationData("성장 높이", (String)plantDetailData.get("height"));
         place = new PlantInformationData("배치 장소", ((String)plantDetailData.get("place")).replace(",","\n"));
         smell = new PlantInformationData("식물 냄새", (String)plantDetailData.get("smell"));
         speed = new PlantInformationData("생장 속도", (String)plantDetailData.get("growthSpeed"));
         temperature = new PlantInformationData("적정 온도", (String)plantDetailData.get("properTemperature"));
         pest = new PlantInformationData(" 병해충 ", (String)plantDetailData.get("pest"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrayList.add(familyname);
        arrayList.add(waterCycle);
        arrayList.add(waterdrop);
        arrayList.add(light);
        arrayList.add(height);
        arrayList.add(place);
        arrayList.add(smell);
        arrayList.add(speed);
        arrayList.add(temperature);
        arrayList.add(pest);

        //plantInformationAdapter.notifyDataSetChanged();

        String manageLevel = "";

        ImageView star_ImageView = (ImageView)findViewById(R.id.difficulty_star_ImageView);
        try {
            manageLevel = (String)plantDetailData.get("manageLevel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(manageLevel.equals("경험자")){
            // 별 3
            star_ImageView.setImageResource(R.drawable.star_three);
        }
        else if(manageLevel.equals("초보자")){
            // 별 1
            star_ImageView.setImageResource(R.drawable.star_one);
        }
        else if(manageLevel.equals("전문가")){
            // 별 5
            star_ImageView.setImageResource(R.drawable.star_five);
        }
        else{
            // 별 2
            star_ImageView.setImageResource(R.drawable.star_two);
        }

        findViewById(R.id.addmyplantButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_addmyplant_page = new Intent(PlantInformationActivity.this, AddMyplantActivity.class);
                intent_goto_addmyplant_page.putExtra("plantDetailData", jsonString);
                intent_goto_addmyplant_page.putExtra("image_bitmap_to_addmyplant", byteArray_imageBitmap);
                startActivity(intent_goto_addmyplant_page);
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_search_plant;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_camera;
    }
}
