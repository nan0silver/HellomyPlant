package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

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

import static com.nahyun.helloplant.MainActivity.email;

public class MyplantListActivity extends BottomNavigationActivity {

    public static ArrayList<MyplantListData> mp_arrayList;
    private MyplantListAdapter myplantListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplant_list);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setSelectedItemId(R.id.action_home);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent ML_intent_camera = new Intent(MyplantListActivity.this, searchPlant.class);
                        ML_intent_camera.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        break;
                    case R.id.action_ranking:
                        Intent ML_intent_ranking = new Intent(MyplantListActivity.this, RankingListActivity.class);
                        ML_intent_ranking.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent ML_intent_talk = new Intent(MyplantListActivity.this, NoticeBoardActivity.class);
                        ML_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        //====recycler View code====//
        recyclerView = (RecyclerView)findViewById(R.id.myplant_list_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mp_arrayList = new ArrayList<>();
        System.out.println("mp_arrayList first : " + mp_arrayList.size());

        myplantListAdapter = new MyplantListAdapter(mp_arrayList);
        recyclerView.setAdapter(myplantListAdapter);


        //====myplant server connection code ======//
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println("Myplant list token = " + token);

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

        Call<RetrofitGetData> call_get = service.getFunc(email);
        System.out.println("myplantlist email = " + email);

        /*call_get.enqueue(new Callback<RetrofitGetData>() {
            @Override
            public void onResponse(Call<RetrofitGetData> call, Response<RetrofitGetData> response) {
                if (response.isSuccessful()) {
                    response.body();
                    List<Plant> plantList = response.body().getPlants();
                    int list_count = plantList.size();
                    System.out.println("myplantlist count of plant = " +list_count);

                    for (Plant plant: plantList ) {
                        MyplantListData mld1 = null;
                        String after_id = plant.getMyPlant().getId();
                        String after_image = plant.getMyPlant().getImage();
                        String after_name = plant.getMyPlant().getNickname();
                        String after_water_cycle = plant.getMyPlant().getWaterCycle();
                        String after_fertilizer_cycle = plant.getMyPlant().getFertilizerCycle();

                        byte[] byte_array_image = after_image.getBytes();
                        Bitmap after_image_bitmap;
                        after_image_bitmap = BitmapFactory.decodeByteArray(byte_array_image, 0, byte_array_image.length);

                        mld1 = new MyplantListData(after_image_bitmap, after_name, after_water_cycle, after_fertilizer_cycle);

                        //mp_arrayList.add(mld1);
                        add_arraylist(mld1);

                        System.out.println("goooood!!! \nafter_id = " + after_id + " after_image = " + after_image + " after_name = " + after_name
                        + " after_water_cycle = " + after_water_cycle + " after_fertilizer_cycle = " + after_fertilizer_cycle);
                    }

                    Log.v("MyplantListActivity", "code = " + String.valueOf(response.code()));
                    Toast.makeText(MyplantListActivity.this, "code = " + String.valueOf(response.code()) + "\nmyplant list를 불러오는데 성공하였습니다.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.v("MyplantListActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(MyplantListActivity.this, "error : " + String.valueOf(response.code()) + "\n 내 식물 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitGetData> call, Throwable t) {
                Log.v("MyplantListActivity", "Fail");
                Toast.makeText(MyplantListActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Plant> plantList = call_get.execute().body().getPlants();

                    int list_count = plantList.size();
                    System.out.println("myplantlist count of plant = " +list_count);

                    for (Plant plant: plantList ) {
                        MyplantListData mld1 = null;
                        String after_id = plant.getMyPlant().getId();
                        String after_image = plant.getMyPlant().getImage();
                        String after_name = plant.getMyPlant().getNickname();
                        String after_water_cycle = plant.getMyPlant().getWaterCycle();
                        String after_fertilizer_cycle = plant.getMyPlant().getFertilizerCycle();


                        byte[] byte_array_image = after_image.getBytes();
                        Bitmap after_image_bitmap;
                        after_image_bitmap = BitmapFactory.decodeByteArray(byte_array_image, 0, byte_array_image.length);

                        mld1 = new MyplantListData(after_image_bitmap, after_name, after_water_cycle, after_fertilizer_cycle);

                        add_arraylist(mld1);

                        System.out.println("goooood!!! \nafter_id = " + after_id + " after_image = " + after_image + " after_name = " + after_name
                                + " after_water_cycle = " + after_water_cycle + " after_fertilizer_cycle = " + after_fertilizer_cycle);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("Thread is done");

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sleep is done");
        //==== add list to recycler view =====//
        MyplantListData sample1 = null;


        Bitmap sample1_image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test_image);
        /*sample1 = new MyplantListData(sample1_image, "test", null, null);


        mp_arrayList.add(sample1);
        mp_arrayList.add(sample1);*/
        System.out.println("mp_arrayList second : " + mp_arrayList.size());

        //test//
        /*MyplantListData sample2 = null;
        sample2 = new MyplantListData(sample1_image, "test2", null, null);
        mp_arrayList.add(sample2);*/
       // mp_arrayList.add(sample1);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_myplant_list;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_home;
    }


    public static void add_arraylist(MyplantListData myplantListData) {

        mp_arrayList.add(myplantListData);
        System.out.println("mp_arrayList third : " + mp_arrayList.size());
        System.out.println("myplantListData = " + myplantListData);
    }
}