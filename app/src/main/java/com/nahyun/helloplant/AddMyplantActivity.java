package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.nahyun.helloplant.SignupActivity.email;

public class AddMyplantActivity extends BottomNavigationActivity {

    EditText PlantNickName;

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
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Intent AM_intent_home = new Intent(AddMyplantActivity.this, MyplantListActivity.class);
                        AM_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(AM_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_ranking:
                        Intent AM_intent_ranking = new Intent(AddMyplantActivity.this, RankingListActivity.class);
                        AM_intent_ranking.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(AM_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent AM_intent_talk = new Intent(AddMyplantActivity.this, NoticeBoardActivity.class);
                        AM_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(AM_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });


        Intent intent_comefrom_plantinfomation_page = getIntent();
        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_plantinfomation_page.getExtras().getString("plantDetailData");
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

        try {
            if (wateringInfomation.charAt(0) == '항') {
                wateringCycle = 0;
                waterdrop = "4";
            } else if (wateringInfomation.charAt(0) == '흙') {
                wateringCycle = 6;
                waterdrop = "3";
            } else if (wateringInfomation.charAt(0) == '토') {
                wateringCycle = 13;
                waterdrop = "2";
            } else {
                wateringCycle = 29;
                waterdrop = "2";
            }
        }catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
            waterdrop = "2";
            wateringCycle = 0;
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

        PlantNickName = (EditText)findViewById(R.id.set_plantnickname_EditText);

        try {
            plantDetailData.put("plantNickname", PlantNickName.getText().toString());
            plantDetailData.put("waterDrop", waterdrop);

            String wateringPeriod_string = spinner_watering.getSelectedItem().toString();
            plantDetailData.put("wateringPeriod", wateringPeriod_string);

            String fertilizingPeriod_string = spinner_fertilizing.getSelectedItem().toString();
            plantDetailData.put("fertilizingPeriod", fertilizingPeriod_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String finalWaterdrop = waterdrop;
        JSONObject finalPlantDetailData = plantDetailData;

        findViewById(R.id.set_myplant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_viewmyplant_page = new Intent(AddMyplantActivity.this, ViewMyplantActivity.class);

                intent_goto_viewmyplant_page.putExtra("PlantNickName", PlantNickName.getText().toString());
                intent_goto_viewmyplant_page.putExtra("WaterDrop", finalWaterdrop);

                String wateringPeriod_string = spinner_watering.getSelectedItem().toString();
                intent_goto_viewmyplant_page.putExtra("WateringPeriod", wateringPeriod_string);
                String fertilizingPeriod_string = spinner_fertilizing.getSelectedItem().toString();
                intent_goto_viewmyplant_page.putExtra("FertilizingPeriod", fertilizingPeriod_string);

                intent_goto_viewmyplant_page.putExtra("plantDetailData", finalPlantDetailData.toString());
                intent_goto_viewmyplant_page.putExtra("image_bitmap_to_viewmyplant", byteArray_imageBitmap_addmyplant);

                String name = "";
                try {
                    name = finalPlantDetailData.get("name").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AddMyplant_post(name, wateringPeriod_string, fertilizingPeriod_string, PlantNickName.getText().toString(), new String(byteArray_imageBitmap_addmyplant));

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

    public void AddMyplant_post(String scientific_name, String water_cycle, String fertilizer_cycle, String nickname, String image) {

        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println(token);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.@NotNull Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token).build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl("http://18.116.203.236:1234/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);

        Map <String, String> map = new HashMap<>();
        map.put("scientific_name", scientific_name);
        map.put("water_cycle", water_cycle);
        map.put("fertilizer_cycle", fertilizer_cycle);
        map.put("nickname", nickname);
        map.put("image", image);

        System.out.println("scientific_name = " + scientific_name + " water_cycle = " + water_cycle + " fertilizer_cycle = " + fertilizer_cycle
        + " nickname = " + nickname + " image = " + image );


        Call<RetrofitPostData> call_post = service.postFunc(email, map);
        call_post.enqueue(new Callback<RetrofitPostData>() {
            @Override
            public void onResponse(Call<RetrofitPostData> call, Response<RetrofitPostData> response) {
                if(response.isSuccessful()) {

                    response.body();
                    String after_scientific_name = response.body().getNewPlant().getScientificName();
                    String after_water_cycle = response.body().getNewPlant().getWaterCycle();
                    String after_fertilizer_cycle = response.body().getNewPlant().getFertilizerCycle();
                    String after_nickname = response.body().getNewPlant().getNickname();
                    String after_image = response.body().getNewPlant().getImage();
                    String after_id = response.body().getNewPlant().getId();
                    String after_createdAt = response.body().getNewPlant().getCreatedAt();
                    String after_updatedAt = response.body().getNewPlant().getUpdatedAt();

                    Log.v("AddMyplantActivity", "scientific_name = " + after_scientific_name
                            + "\nwater_cycle = " + after_water_cycle
                            +"\nfertilizer_cycle = " + after_fertilizer_cycle
                            +"\nnickname = " + after_nickname
                            +"\nimage = " + after_image
                            +"\nid = " + after_id
                            +"\ncreatedAt = " + after_createdAt
                            +"\nupdatedAt = " + after_updatedAt
                            + "\ncode = " + String.valueOf(response.code()));
                    Toast.makeText(AddMyplantActivity.this, "내 식물이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.v("AddMyplantActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(AddMyplantActivity.this, "error : " + String.valueOf(response.code()) + "\n 내 식물 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitPostData> call, Throwable t) {
                Log.v("AddMyplantActivity", "Fail");
                Toast.makeText(AddMyplantActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

