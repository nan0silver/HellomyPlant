package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
        try {
            familyname = new PlantInformationData("식물 과명", (String)plantDetailData.get("familyName"));
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
}
