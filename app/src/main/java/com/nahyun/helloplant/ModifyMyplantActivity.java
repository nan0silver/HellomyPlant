package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nahyun.helloplant.MainActivity.email;

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
        String PlantId_string = intent_comefrom_viewmyplant_page.getExtras().getString("PlantId");

        //==== watering spinner code =====//
        Spinner spinner_watering = findViewById(R.id.set_wateringperiod_Spinner);

        ArrayAdapter arrayAdapter_watering = ArrayAdapter.createFromResource(this, R.array.watering_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_watering.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int waterperiod_int = Integer.parseInt(WaterPeriod_String)-1;
        spinner_watering.setAdapter(arrayAdapter_watering);
        spinner_watering.setSelection(waterperiod_int);

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
                Modifymyplant_put(PlantId_string, wateringPeriod_string, fertilizingPeriod_string, PlantNickName_EditText.getText().toString());
                startActivity(intent_goto_viewmyplant_page);
            }
        });


        findViewById(R.id.set_wateringperiod_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_watering.setSelection(Integer.parseInt(WaterPeriod_String)-1);
            }
        });

        findViewById(R.id.modify_delete_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modifymyplant_delete(PlantId_string, email);
                System.out.println("delete Modify myplant email : " + email);
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

    public void Modifymyplant_put(String plantId, String water_cycle, String fertilizer_cycle, String nickname) {

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

        Map<String, String> map = new HashMap<>();
        map.put("plantId", plantId);
        map.put("water_cycle", water_cycle);
        map.put("fertilizer_cycle", fertilizer_cycle);
        map.put("nickname", nickname);

        System.out.println("plantId = " + plantId + " water_cycle = " + water_cycle + " fertilizer_cycle = " + fertilizer_cycle
                + " nickname = " + nickname );

        Call<RetrofitPutData> call_put = service.putFunc(map);
        call_put.enqueue(new Callback<RetrofitPutData>() {
            @Override
            public void onResponse(Call<RetrofitPutData> call, Response<RetrofitPutData> response) {
                if(response.isSuccessful()) {

                    response.body();
                    String after_message = response.body().getMessage();
                    String after_scientific_name = response.body().getMyPlant().getScientificName();
                    String after_water_cycle = response.body().getMyPlant().getWaterCycle();
                    String after_fertilizer_cycle = response.body().getMyPlant().getFertilizerCycle();
                    String after_nickname = response.body().getMyPlant().getNickname();
                    String after_image = response.body().getMyPlant().getImage();
                    String after_id = response.body().getMyPlant().getId();
                    String after_createdAt = response.body().getMyPlant().getCreatedAt();
                    String after_updatedAt = response.body().getMyPlant().getUpdatedAt();

                    Log.v("ModifyMyplantActivity", "scientific_name = " + after_scientific_name
                            + "\nwater_cycle = " + after_water_cycle
                            +"\nfertilizer_cycle = " + after_fertilizer_cycle
                            +"\nnickname = " + after_nickname
                            +"\nimage = " + after_image
                            +"\nid = " + after_id
                            +"\ncreatedAt = " + after_createdAt
                            +"\nupdatedAt = " + after_updatedAt
                            + "\ncode = " + String.valueOf(response.code())
                            + "\nmessage = " + after_message);

                    Toast.makeText(ModifyMyplantActivity.this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.v("ModifyMyplantActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(ModifyMyplantActivity.this, "error : " + String.valueOf(response.code()) + "\n 내 식물 수정을 실패했습니다.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RetrofitPutData> call, Throwable t) {
                Log.v("ModifyMyplantActivity", "Fail");
                Toast.makeText(ModifyMyplantActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Modifymyplant_delete(String plantId, String email) {
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

        Map<String, String> map = new HashMap<>();
        map.put("plantId", plantId);
        map.put("email", email);

        System.out.println("plantId = " + plantId + " email = " + email);

        Call<ResponseBody> call_delete = service.deleteFunc(map);
        call_delete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println(String.valueOf(response.code()));

                    Toast.makeText(ModifyMyplantActivity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent_after_success_delete = new Intent(ModifyMyplantActivity.this, MyplantListActivity.class);
                    startActivity(intent_after_success_delete);
                }
                else {
                    Log.v("ViewMyplantActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(ModifyMyplantActivity.this, "code : " + String.valueOf(response.code()) + "\n 내 식물 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("ViewMyplantActivity", "Fail");
                Toast.makeText(ModifyMyplantActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}