package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyplantInformationActivity extends AppCompatActivity {

    private ArrayList<MyplantInformationData> mpi_arrayList;
    private MyplantInformationAdapter myplantInformationAdapter;
    private RecyclerView myplant_information_RecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplant_information);

        Intent intent_comefrom_view_myplant_page = getIntent();

        byte[] byteArray_imageBitmap = getIntent().getByteArrayExtra("image_bitmap");
        Bitmap get_image;
        get_image = BitmapFactory.decodeByteArray(byteArray_imageBitmap, 0, byteArray_imageBitmap.length);
        ImageView plant_ImageView = (ImageView)findViewById(R.id.myplant_information_ImageView);
        plant_ImageView.setImageBitmap(get_image);

        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_view_myplant_page.getExtras().getString("plantDetailData");
        if (jsonString != null) {
            try {
                plantDetailData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        myplant_information_RecyclerView = (RecyclerView)findViewById(R.id.myplant_information_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        myplant_information_RecyclerView.setLayoutManager(linearLayoutManager);

        mpi_arrayList = new ArrayList<>();

        myplantInformationAdapter = new MyplantInformationAdapter(mpi_arrayList);
        myplant_information_RecyclerView.setAdapter(myplantInformationAdapter);

        MyplantInformationData familyname = null;
        MyplantInformationData height = null;
        MyplantInformationData place = null;
        MyplantInformationData smell = null;
        MyplantInformationData speed = null;
        MyplantInformationData temperature = null;
        MyplantInformationData pest = null;
        MyplantInformationData waterCycle = null;
        MyplantInformationData waterdrop = null;
        MyplantInformationData light = null;

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
        String KoreanName = "";
        String ManageLevel = "";
        try {
            familyname = new MyplantInformationData("식물 과명", (String)plantDetailData.get("familyName"));
            waterCycle = new MyplantInformationData(" 물 주기 ", water);
            waterdrop = new MyplantInformationData(" 물의 양 ", wateringdrop);
            light = new MyplantInformationData("햇빛의 양", (String)plantDetailData.get("light"));
            height = new MyplantInformationData("성장 높이", (String)plantDetailData.get("height"));
            place = new MyplantInformationData("배치 장소", ((String)plantDetailData.get("place")).replace(",","\n"));
            smell = new MyplantInformationData("식물 냄새", (String)plantDetailData.get("smell"));
            speed = new MyplantInformationData("생장 속도", (String)plantDetailData.get("growthSpeed"));
            temperature = new MyplantInformationData("적정 온도", (String)plantDetailData.get("properTemperature"));
            pest = new MyplantInformationData(" 병해충 ", (String)plantDetailData.get("pest"));
            KoreanName = (String)plantDetailData.get("koreanName");
            ManageLevel = (String)plantDetailData.get("manageLevel");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mpi_arrayList.add(familyname);
        mpi_arrayList.add(waterCycle);
        mpi_arrayList.add(waterdrop);
        mpi_arrayList.add(light);
        mpi_arrayList.add(height);
        mpi_arrayList.add(place);
        mpi_arrayList.add(smell);
        mpi_arrayList.add(speed);
        mpi_arrayList.add(temperature);
        mpi_arrayList.add(pest);

        TextView myplant_information_name_TextView = (TextView)findViewById(R.id.myplant_information_name_TextView);
        myplant_information_name_TextView.setText(KoreanName);

        ImageView star_ImageView = (ImageView)findViewById(R.id.difficulty_star_ImageView);
        if(ManageLevel.equals("경험자")){
            // 별 3
            star_ImageView.setImageResource(R.drawable.star_three);
        }
        else if(ManageLevel.equals("초보자")){
            // 별 1
            star_ImageView.setImageResource(R.drawable.star_one);
        }
        else if(ManageLevel.equals("전문가")){
            // 별 5
            star_ImageView.setImageResource(R.drawable.star_five);
        }
        else{
            // 별 2
            star_ImageView.setImageResource(R.drawable.star_two);
        }

    }

    /*public void Myplant_information_get(String scientific_name) {
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println("Myplant_information token is " + token);

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

        Call<Retrofit_plant_GetData> call_plant_get = service.get_plant_myplantinfo_Func(scientific_name);
        System.out.println("Myplant information scientific name = " + scientific_name);

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

                    System.out.println("Myplant information response :"
                    + "\n after_id : " + after_id
                    + "\nafter_scientific_name : " + after_scientific_name
                    + "\nafter_water_cycle : " + after_water_cycle
                    + "\nafter_light :" + after_light);

                    int wateringCycle = 0;
                    String wateringdrop = "";
                    if (after_water_cycle.charAt(0) == '항') {
                        wateringCycle = 1;
                        wateringdrop = "흙이 흠뻑 젖도록";
                    } else if (after_water_cycle.charAt(0) == '흙') {
                        wateringCycle = 7;
                        wateringdrop = "흙이 촉촉하게";
                    } else if (after_water_cycle.charAt(0) == '토') {
                        wateringCycle = 14;
                        wateringdrop = "흙이 적당히 젖도록";
                    } else {
                        wateringCycle = 30;
                        wateringdrop = "흙이 적당히 젖도록";
                    }
                    String water = Integer.toString(wateringCycle) + " 일";

                    mpi_arrayList.add(new MyplantInformationData("식물 과명", after_family_name));
                    mpi_arrayList.add(new MyplantInformationData(" 물 주기 ", water));
                    mpi_arrayList.add(new MyplantInformationData(" 물의 양 ", wateringdrop));
                    mpi_arrayList.add(new MyplantInformationData("햇빛의 양", after_light));
                    mpi_arrayList.add(new MyplantInformationData("성장 높이", after_height));
                    mpi_arrayList.add(new MyplantInformationData("배치 장소", after_place));
                    mpi_arrayList.add(new MyplantInformationData("식물 냄새", after_smell));
                    mpi_arrayList.add(new MyplantInformationData("생장 속도", after_growth_speed));
                    mpi_arrayList.add(new MyplantInformationData("적정 온도", after_proper_temperature));
                    mpi_arrayList.add(new MyplantInformationData(" 병해충 ", after_pest));


                    ImageView star_ImageView = (ImageView)findViewById(R.id.difficulty_star_ImageView);
                    if(after_manage_level.equals("경험자")){
                        // 별 3
                        star_ImageView.setImageResource(R.drawable.star_three);
                    }
                    else if(after_manage_level.equals("초보자")){
                        // 별 1
                        star_ImageView.setImageResource(R.drawable.star_one);
                    }
                    else if(after_manage_level.equals("전문가")){
                        // 별 5
                        star_ImageView.setImageResource(R.drawable.star_five);
                    }
                    else{
                        // 별 2
                        star_ImageView.setImageResource(R.drawable.star_two);
                    }

                    myplantInformationAdapter.updateMyplantInformationItems(mpi_arrayList);
                    Toast.makeText(getApplicationContext(), "success get plant information", Toast.LENGTH_SHORT).show();

                }
                else { //if response is 400+a
                    System.out.println("response is over 400");
                    Log.v("MyplantInformation", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(), "정보가 저장되지 않은 식물입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_plant_GetData> call, Throwable t) {
                Log.v("MyplantInformation", "Fail");
                Log.v("MyplantInformation", t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크에 에러가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


}