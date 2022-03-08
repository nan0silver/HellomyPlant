package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
                        Intent PI_intent_home = new Intent(PlantInformationActivity.this, MyplantListActivity.class);
                        //PI_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(PI_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent PI_intent_talk = new Intent(PlantInformationActivity.this, NoticeBoardActivity.class);
                        //PI_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(PI_intent_talk);
                        overridePendingTransition(0,0);
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
            plant_name_TextView.setText((String)plantDetailData.get("koreanName"));
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

        try {
            if (wateringInfomation.charAt(0) == '항') {
                wateringCycle = 1;
                wateringdrop = "흙이 흠뻑 젖도록";
            } else if (wateringInfomation.charAt(0) == '흙') {
                wateringCycle = 7;
                wateringdrop = "흙이 촉촉하게";
            } else if (wateringInfomation.charAt(0) == '토') {
                wateringCycle = 14;
                wateringdrop = "흙이 적당히 젖도록";
            } else {
                wateringCycle = 30;
                wateringdrop = "흙이 적당히 젖도록";
            }
        }
        catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
            wateringdrop = "정보가 없습니다.";
            System.out.println("There is no watering information for this plant.");
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

        TextView plant_information_probability_TextView = (TextView)findViewById(R.id.plant_information_probability_TextView);
        String probability = "";
        try {
            probability = (String)plantDetailData.get("probability");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String show_probability = probability.substring(2, 4) + "%";
        plant_information_probability_TextView.setText(show_probability);

        findViewById(R.id.addmyplantButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_addmyplant_page = new Intent(PlantInformationActivity.this, AddMyplantActivity.class);
                intent_goto_addmyplant_page.putExtra("plantDetailData", jsonString);
                intent_goto_addmyplant_page.putExtra("image_bitmap_to_addmyplant", byteArray_imageBitmap);
                startActivity(intent_goto_addmyplant_page);
            }
        });

        //plant name copy code
        ImageButton copy_Button = (ImageButton)findViewById(R.id.searching_plant_name_copy_Button);
        copy_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("plant_name",plant_name_TextView.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(PlantInformationActivity.this, "식물 이름이 복사되었습니다.", Toast.LENGTH_SHORT).show();
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
