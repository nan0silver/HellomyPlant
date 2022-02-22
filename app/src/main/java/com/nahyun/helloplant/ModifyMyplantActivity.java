package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyMyplantActivity extends BottomNavigationActivity {

    EditText PlantNickName_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_myplant);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setOnItemSelectedListener(this);
        navigation_add.setSelectedItemId(R.id.action_camera);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Intent AM_intent_home = new Intent(ModifyMyplantActivity.this, MyplantListActivity.class);
                        AM_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(AM_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent AM_intent_talk = new Intent(ModifyMyplantActivity.this, NoticeBoardActivity.class);
                        AM_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(AM_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });


        Intent intent_comefrom_viewmyplant_page = getIntent();
        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_viewmyplant_page.getExtras().getString("plantDetailData");
        if (jsonString != null) {
            try {
                plantDetailData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ImageView plant_ImageView = (ImageView)findViewById(R.id.searching_plant_ImageView);
        byte[] byteArray_imageBitmap_addmyplant = getIntent().getByteArrayExtra("image_bitmap_to_addmyplant");
        Bitmap get_image_addmyplant;
        get_image_addmyplant = BitmapFactory.decodeByteArray(byteArray_imageBitmap_addmyplant, 0, byteArray_imageBitmap_addmyplant.length);
        plant_ImageView.setImageBitmap(get_image_addmyplant);

        String PlantNickName_string = intent_comefrom_viewmyplant_page.getExtras().getString("PlantNickName");
        String WaterDrop_string = intent_comefrom_viewmyplant_page.getExtras().getString("WaterDrop");
        String WaterPeriod_String = intent_comefrom_viewmyplant_page.getExtras().getString("WateringPeriod");
        String FertilizingPeriod_String = intent_comefrom_viewmyplant_page.getExtras().getString("FertilizingPeriod");
        String light_string = intent_comefrom_viewmyplant_page.getExtras().getString("light");

        //==== watering spinner code =====//
        //String wateringInfomation = "";

        /*try {
            wateringInfomation = (String)plantDetailData.get("watercycleWinter");
            light = (String)plantDetailData.get("light");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        /*int wateringCycle = 0;
        String waterdrop = "";

        System.out.println(WaterPeriod_String);
        System.out.println(light);

        try {
            if (WaterPeriod_String.charAt(0) == '항') {
                wateringCycle = 0; //1
                waterdrop = "4";
            } else if (WaterPeriod_String.charAt(0) == '흙') {
                wateringCycle = 6; //7 일주
                waterdrop = "3";
            } else if (WaterPeriod_String.charAt(0) == '토') {
                wateringCycle = 13; //14 2주
                waterdrop = "2";
            } else {
                wateringCycle = 29; //30 한달
                waterdrop = "2";
            }
        }catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
            waterdrop = "2";
            wateringCycle = 0;
        }
*/
        Spinner spinner_watering = findViewById(R.id.set_wateringperiod_Spinner);

        ArrayAdapter arrayAdapter_watering = ArrayAdapter.createFromResource(this, R.array.watering_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_watering.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_watering.setAdapter(arrayAdapter_watering);

        spinner_watering.setSelection(Integer.parseInt(WaterPeriod_String)-1);

        TextView searched_wateringperiod_TextView = (TextView)findViewById(R.id.searched_wateringperiod_TextView);
        searched_wateringperiod_TextView.setText(WaterPeriod_String);

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

        int notfixed_fer = Integer.parseInt(FertilizingPeriod_String);
        notfixed_fer = (notfixed_fer - 30)/10;
        spinner_watering.setSelection(notfixed_fer);

        spinner_fertilizing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PlantNickName_EditText = (EditText)findViewById(R.id.set_plantnickname_EditText);
        PlantNickName_EditText.setText(PlantNickName_string);
        try {
            plantDetailData.put("plantNickname", PlantNickName_EditText.getText().toString());
            plantDetailData.put("waterDrop", WaterDrop_string);

            String wateringPeriod_string = spinner_watering.getSelectedItem().toString();
            plantDetailData.put("wateringPeriod", wateringPeriod_string);

            String fertilizingPeriod_string = spinner_fertilizing.getSelectedItem().toString();
            plantDetailData.put("fertilizingPeriod", fertilizingPeriod_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject finalPlantDetailData = plantDetailData;
        String finalLight = light_string;

        findViewById(R.id.modify_myplant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_viewmyplant_page = new Intent(ModifyMyplantActivity.this, ViewMyplantActivity.class);

                intent_goto_viewmyplant_page.putExtra("PlantNickName", PlantNickName_EditText.getText().toString());
                intent_goto_viewmyplant_page.putExtra("WaterDrop", WaterDrop_string);

                String wateringPeriod_string = spinner_watering.getSelectedItem().toString();
                intent_goto_viewmyplant_page.putExtra("WateringPeriod", wateringPeriod_string);
                String fertilizingPeriod_string = spinner_fertilizing.getSelectedItem().toString();
                intent_goto_viewmyplant_page.putExtra("FertilizingPeriod", fertilizingPeriod_string);

                intent_goto_viewmyplant_page.putExtra("plantDetailData", finalPlantDetailData.toString());
                intent_goto_viewmyplant_page.putExtra("image_bitmap_to_viewmyplant", byteArray_imageBitmap_addmyplant);
                intent_goto_viewmyplant_page.putExtra("light", finalLight);

                String name = "";
                try {
                    name = finalPlantDetailData.get("name").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String image_string = Base64.encodeToString(byteArray_imageBitmap_addmyplant, Base64.DEFAULT);
                //AddMyplant_post(name, wateringPeriod_string, fertilizingPeriod_string, PlantNickName_EditText.getText().toString(), image_string);

                startActivity(intent_goto_viewmyplant_page);
            }
        });


        findViewById(R.id.set_wateringperiod_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_watering.setSelection(Integer.parseInt(WaterPeriod_String)-1);
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