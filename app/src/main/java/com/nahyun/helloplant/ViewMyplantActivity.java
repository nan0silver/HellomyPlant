package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewMyplantActivity extends BottomNavigationActivity {

    TextView PlantNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_myplant);

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.action_camera);
        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Intent VM_intent_home = new Intent(ViewMyplantActivity.this, MyplantListActivity.class);
                        VM_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(VM_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_ranking:
                        Intent VM_intent_ranking = new Intent(ViewMyplantActivity.this, RankingListActivity.class);
                        VM_intent_ranking.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(VM_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent VM_intent_talk = new Intent(ViewMyplantActivity.this, NoticeBoardActivity.class);
                        VM_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(VM_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        Intent intent_comefrom_addmyplant_page = getIntent();

        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_addmyplant_page.getExtras().getString("plantDetailData");
        if (jsonString != null) {
            try {
                plantDetailData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String PlantNickName_string = intent_comefrom_addmyplant_page.getExtras().getString("PlantNickName");
        String WaterDrop_string = intent_comefrom_addmyplant_page.getExtras().getString("WaterDrop");
        String WaterPeriod_String = intent_comefrom_addmyplant_page.getExtras().getString("WateringPeriod");
        String FertilizingPeriod_String = intent_comefrom_addmyplant_page.getExtras().getString("FertilizingPeriod");

        /*String PlantNickName_string = null;
        String WaterDrop_string = null;
        String WaterPeriod_String = null;
        String FertilizingPeriod_String = null;

        try {
            PlantNickName_string = plantDetailData.getString("plantNickname");
            WaterDrop_string = plantDetailData.getString("waterDrop");
            WaterPeriod_String = plantDetailData.getString("wateringPeriod");
            FertilizingPeriod_String = plantDetailData.getString("fertilizingPeriod");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        PlantNickName = (TextView)findViewById(R.id.myplant_nickname_TextView);
        PlantNickName.setText(PlantNickName_string);

        ImageView waterdrop_ImageView = (ImageView)findViewById(R.id.waterdrop_ImageView);
        if(WaterDrop_string.equals("4")) {
            waterdrop_ImageView.setImageResource(R.drawable.water_drop_four);
        }
        else if (WaterDrop_string.equals("3")) {
            waterdrop_ImageView.setImageResource(R.drawable.water_drop_three);
        }
        else {
            waterdrop_ImageView.setImageResource(R.drawable.water_drop_two);
        }

        TextView wateringperiod_TextView = (TextView)findViewById(R.id.view_wateringperiod_TextView);
        wateringperiod_TextView.setText(WaterPeriod_String);

        TextView fertilizingperiod_TextView = (TextView)findViewById(R.id.view_fertilizingperiod_TextView);
        fertilizingperiod_TextView.setText(FertilizingPeriod_String);

        ImageView plant_ImageView = (ImageView)findViewById(R.id.searching_plant_ImageView);
        byte[] byteArray_imageBitmap_viewmyplant = getIntent().getByteArrayExtra("image_bitmap_to_viewmyplant");
        Bitmap get_image_viewmyplant;
        get_image_viewmyplant = BitmapFactory.decodeByteArray(byteArray_imageBitmap_viewmyplant, 0, byteArray_imageBitmap_viewmyplant.length);
        plant_ImageView.setImageBitmap(get_image_viewmyplant);


        findViewById(R.id.view_to_add_page_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_modify_page = new Intent(ViewMyplantActivity.this, AddMyplantActivity.class);
                intent_modify_page.putExtra("plantDetailData", jsonString);
                intent_modify_page.putExtra("image_bitmap_to_addmyplant", byteArray_imageBitmap_viewmyplant);
                startActivity(intent_modify_page);
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