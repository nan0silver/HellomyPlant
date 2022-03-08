package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

public class ViewMyplantActivity extends BottomNavigationActivity {

    TextView PlantNickName;
    private int resize_width = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_myplant);

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

        System.out.println("View myplant plantDetailData : " + plantDetailData.toString());

        String PlantNickName_string = intent_comefrom_addmyplant_page.getExtras().getString("PlantNickName");
        String ScientificName_string = intent_comefrom_addmyplant_page.getExtras().getString("ScientificName");
        String WaterDrop_string = intent_comefrom_addmyplant_page.getExtras().getString("WaterDrop");
        String WaterPeriod_String = intent_comefrom_addmyplant_page.getExtras().getString("WateringPeriod");
        String FertilizingPeriod_String = intent_comefrom_addmyplant_page.getExtras().getString("FertilizingPeriod");
        String light_string = intent_comefrom_addmyplant_page.getExtras().getString("light");
        String PlantId_string = intent_comefrom_addmyplant_page.getExtras().getString("PlantId");

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

        ImageView light_ImageView = (ImageView)findViewById(R.id.light_ImageView);
        if (light_string.charAt(0) == '낮') {
            light_ImageView.setImageResource(R.drawable.light_two);
        }
        else if (light_string.charAt(0) == '중') {
            light_ImageView.setImageResource(R.drawable.light_three);
        }
        else if (light_string.charAt(0) == '높') {
            light_ImageView.setImageResource(R.drawable.light_two);
        }
        else {
            light_ImageView.setImageResource(R.drawable.light_three);
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

        findViewById(R.id.modify_viewmyplant_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_modify_page = new Intent(ViewMyplantActivity.this, ModifyMyplantActivity.class);
                intent_modify_page.putExtra("plantDetailData", jsonString);
                intent_modify_page.putExtra("image_bitmap_to_addmyplant", byteArray_imageBitmap_viewmyplant);

                intent_modify_page.putExtra("PlantNickName", PlantNickName_string);
                intent_modify_page.putExtra("WaterDrop", WaterDrop_string);
                intent_modify_page.putExtra("WateringPeriod", WaterPeriod_String);
                intent_modify_page.putExtra("FertilizingPeriod", FertilizingPeriod_String);
                intent_modify_page.putExtra("light", light_string);
                intent_modify_page.putExtra("PlantId", PlantId_string);
                startActivity(intent_modify_page);
            }
        });

        String finalScientificName_string = ScientificName_string;
        findViewById(R.id.get_viewmyplant_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Viewmyplant_delete(PlantId_string, email);
                //image, nickname, manage_level, 정보들

                ViewMyplant_get(finalScientificName_string);
                System.out.println("Go to Myplant information page \nPlant nickname : " + PlantNickName_string + "\nPlant scientific name : "
                + finalScientificName_string);

            }
        });

        findViewById(R.id.goto_viewmyplant_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_myplantlist_from_viewmyplant = new Intent(ViewMyplantActivity.this, MyplantListActivity.class);
                startActivity(intent_goto_myplantlist_from_viewmyplant);
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

    public byte[] ImageViewToByteArray() {

        ImageView selected_Image_View = (ImageView)findViewById(R.id.searching_plant_ImageView);
        BitmapDrawable selected_image_drawable = (BitmapDrawable)selected_Image_View.getDrawable();
        int height = selected_image_drawable.getBitmap().getHeight();
        int width = selected_image_drawable.getBitmap().getWidth();
        System.out.println("height = " + height + " width = " + width);
        resize_width = 400*width/height;
        System.out.println("resize_width = " + resize_width);
        if (resize_width <= 0) resize_width = 300;
        Bitmap selected_image_bitmap = Bitmap.createScaledBitmap(selected_image_drawable.getBitmap(), resize_width , 400, true);
        ByteArrayOutputStream stream_change = new ByteArrayOutputStream();
        selected_image_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream_change);
        byte[] byteArray_result = stream_change.toByteArray();

        return byteArray_result;
    }

    public void ViewMyplant_get(String scientific_name) {
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println("searchPlant token = " + token);

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

        Call<Retrofit_plant_GetData> call_plant_get = service.get_plant_Func(scientific_name);
        System.out.println("searchPlant scientific name = " + scientific_name);

        call_plant_get.enqueue(new Callback<Retrofit_plant_GetData>() {
            @Override
            public void onResponse(Call<Retrofit_plant_GetData> call, Response<Retrofit_plant_GetData> response) {
                if (response.isSuccessful()) {
                    response.body();
                    String after_id = response.body().getPlant().getId();
                    String after_scientific_name = response.body().getPlant().getScientificName();
                    String after_family_name = response.body().getPlant().getFamilyName();
                    String after_korean_name = response.body().getPlant().getKoreanName();
                    String after_water_cycle = response.body().getPlant().getWaterCycle();
                    String after_height = response.body().getPlant().getHeight();
                    String after_place = response.body().getPlant().getPlace();
                    String after_smell = response.body().getPlant().getSmell();
                    String after_growth_speed = response.body().getPlant().getGrowthSpeed();
                    String after_proper_temperature = response.body().getPlant().getProperTemperature();
                    String after_pest = response.body().getPlant().getPest();
                    String after_manage_level = response.body().getPlant().getManageLevel();
                    String after_light = response.body().getPlant().getLight();
                    String after_createdAt = response.body().getPlant().getCreatedAt();
                    String after_updatedAt = response.body().getPlant().getUpdatedAt();

                    JSONObject plantDetailData = new JSONObject();

                    try {
                        plantDetailData.put("koreanName", after_korean_name);
                        plantDetailData.put("familyName", after_family_name);
                        plantDetailData.put("scientificName", after_scientific_name);
                        plantDetailData.put("height", after_height);
                        plantDetailData.put("place", after_place);
                        plantDetailData.put("smell", after_smell);
                        plantDetailData.put("growthSpeed", after_growth_speed);
                        plantDetailData.put("properTemperature", after_proper_temperature);
                        plantDetailData.put("pest", after_pest);
                        plantDetailData.put("watercycleSpring", after_water_cycle);
                        plantDetailData.put("watercycleSummer", after_water_cycle);
                        plantDetailData.put("watercycleFall", after_water_cycle);
                        plantDetailData.put("watercycleWinter", after_water_cycle);
                        plantDetailData.put("manageLevel", after_manage_level);
                        plantDetailData.put("light", after_light);
                        plantDetailData.put("after_response_id", after_id);
                        plantDetailData.put("after_response_createdAt", after_createdAt);
                        plantDetailData.put("after_response_updatedAt", after_updatedAt);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent_goto_myplant_information_page = new Intent(ViewMyplantActivity.this, MyplantInformationActivity.class);

                    intent_goto_myplant_information_page.putExtra("plantDetailData", plantDetailData.toString());
                    System.out.println("View myplant get plantDetailData : " + plantDetailData);

                    byte[] byteArray_result = ImageViewToByteArray();
                    intent_goto_myplant_information_page.putExtra("image_bitmap", byteArray_result);

                    //Toast.makeText(ViewMyplantActivity.this, "식물 정보 요청에 성공했습니다.", Toast.LENGTH_SHORT).show();

                    startActivity(intent_goto_myplant_information_page);

                }
                else { //if response is 400+a
                    Toast.makeText(getApplicationContext(), "식물 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_plant_GetData> call, Throwable t) {
                Log.v("searchPlantActivity", "Fail");
                Toast.makeText(getApplicationContext(), "네트워크에 에러가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}