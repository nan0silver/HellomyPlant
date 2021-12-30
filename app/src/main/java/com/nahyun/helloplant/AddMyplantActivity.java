package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class AddMyplantActivity extends BottomNavigationActivity {

    EditText PlantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_myplant);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setOnItemSelectedListener(this);
        navigation_add.setSelectedItemId(R.id.action_camera);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_calendar:
                        Toast.makeText(AddMyplantActivity.this, "캘린더로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Toast.makeText(AddMyplantActivity.this, "내 식물 리스트로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_ranking:
                        Toast.makeText(AddMyplantActivity.this, "랭킹 페이지로 이동", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_talk:
                        Toast.makeText(AddMyplantActivity.this, "게시판으로 이동", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });


        Intent intent_comefrom_plantinfomation_page = getIntent();
        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_plantinfomation_page.getExtras().getString("plantDetailData");
        try {
            plantDetailData = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView plant_ImageView = (ImageView)findViewById(R.id.searching_plant_ImageView);
        byte[] byteArray_imageBitmap_addmyplant = getIntent().getByteArrayExtra("image_bitmap_to_addmyplant");
        Bitmap get_image_addmyplant;
        get_image_addmyplant = BitmapFactory.decodeByteArray(byteArray_imageBitmap_addmyplant, 0, byteArray_imageBitmap_addmyplant.length);
        plant_ImageView.setImageBitmap(get_image_addmyplant);

        //==== watering spinner code =====//
        String wateringInfomation = "";
        try {
            wateringInfomation = (String)plantDetailData.get("watercycleWinter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int wateringCycle = 0;
        String waterdrop = "";

        System.out.println(wateringInfomation);

        if(wateringInfomation.charAt(0)=='항'){
            wateringCycle = 0;
            waterdrop = "4";
        } else if(wateringInfomation.charAt(0)=='흙'){
            wateringCycle = 6;
            waterdrop = "3";
        } else if(wateringInfomation.charAt(0)=='토'){
            wateringCycle = 13;
            waterdrop = "2";
        }else{
            wateringCycle = 29;
            waterdrop = "2";
        }

        Spinner spinner_watering = findViewById(R.id.set_wateringperiod_Spinner);

        ArrayAdapter arrayAdapter_watering = ArrayAdapter.createFromResource(this, R.array.watering_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_watering.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_watering.setAdapter(arrayAdapter_watering);

        spinner_watering.setSelection(wateringCycle);

        TextView searched_wateringperiod_TextView = (TextView)findViewById(R.id.searched_wateringperiod_TextView);
        searched_wateringperiod_TextView.setText(String.valueOf(wateringCycle+1));

        spinner_watering.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //==== fertilizing spinner code ====//
        Spinner spinner_fertilizing = findViewById(R.id.set_fertilizingperiod_Spinner);

        ArrayAdapter arrayAdapter_fertilizing = ArrayAdapter.createFromResource(this, R.array.fertilizing_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_fertilizing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_fertilizing.setAdapter(arrayAdapter_fertilizing);

        spinner_fertilizing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PlantName = (EditText)findViewById(R.id.set_plantname_EditText);

        String finalWaterdrop = waterdrop;
        findViewById(R.id.set_myplant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_viewmyplant_page = new Intent(AddMyplantActivity.this, ViewMyplantActivity.class);

                intent_goto_viewmyplant_page.putExtra("PlantName", PlantName.getText().toString());
                intent_goto_viewmyplant_page.putExtra("WaterDrop", finalWaterdrop);

                String wateringPeriod_string = spinner_watering.getSelectedItem().toString();
                intent_goto_viewmyplant_page.putExtra("WateringPeriod", wateringPeriod_string);

                String fertilizingPeriod_string = spinner_fertilizing.getSelectedItem().toString();
                intent_goto_viewmyplant_page.putExtra("FertilizingPeriod", fertilizingPeriod_string);

                intent_goto_viewmyplant_page.putExtra("image_bitmap_to_viewmyplant", byteArray_imageBitmap_addmyplant);

                startActivity(intent_goto_viewmyplant_page);
            }
        });

        int finalWateringCycle = wateringCycle;
        findViewById(R.id.set_wateringperiod_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_watering.setSelection(finalWateringCycle);
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